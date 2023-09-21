package pers.prover07.lottery.domain.activity.service.partake.impl;

import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.vo.ActivityBillVO;
import pers.prover07.lottery.domain.activity.model.vo.InvoiceVO;
import pers.prover07.lottery.domain.activity.service.partake.BaseActivityPartake;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;
import pers.prover07.lottery.domain.strategy.repository.IStrategyRepository;
import pers.prover07.lottery.domain.support.cache.IRedisRepository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 参与活动业务类
 *
 * @author Prover07
 * @date 2023/9/5 17:02
 */
@Service
@Slf4j
public class ActivityPartakeImpl extends BaseActivityPartake {

    @Resource
    private IStrategyRepository strategyRepository;

    @Resource
    private IRedisRepository redisRepository;

    private final DefaultRedisScript<Long> activityStockCountIncrScript = new DefaultRedisScript<>();

    @PostConstruct
    public void init() {
        activityStockCountIncrScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/Incrby.lua")));
        activityStockCountIncrScript.setResultType(Long.class);
    }

    @Override
    public void grabActivity(PartakeReq partake, ActivityBillVO activityBillVo, long takeId) {
        // mysql 中同步记录用户领取记录
        userTakeActivityRepository.takeActivity(partake, activityBillVo, takeId);
        // 同步活动名额
        boolean state = activityRepository.subtractionActivityStock(partake.getActivityId());
        if (!state) {
            throw new RuntimeException("活动名额不足");
        }
        UUID uuid1 = UUID.randomUUID();
        // 增加 redis 中的已领取次数
        // TODO 脚本优化
        double takeCount = redisRepository.hIncr(Constants.Cache.ACTIVITY_PARTAKE_USER_TAKE_COUNT_KEY + partake.getActivityId(), partake.getUId(), 1);
        if (takeCount > activityBillVo.getTakeCount()) {
            redisRepository.hIncr(Constants.Cache.ACTIVITY_PARTAKE_USER_TAKE_COUNT_KEY + partake.getActivityId(), partake.getUId(), -1);
            throw new RuntimeException("用户个人领取次数到达上线");
        }

        // 减少 redis 活动库存
        long stock = redisRepository.execute(activityStockCountIncrScript, ListUtil.of(Constants.Cache.ACTIVITY_STOCK_COUNT_KEY + partake.getActivityId()));
        log.info("扣减 redis 库存: {}, {}", stock, Constants.Cache.ACTIVITY_STOCK_COUNT_KEY + partake.getActivityId());
        if (stock < 0) {
            throw new RuntimeException("活动名额不足");
        }
    }

    @Override
    protected boolean subtractionActivityStock(PartakeReq req) {
        return false;
    }

    @Override
    protected Result checkActivityBill(PartakeReq req, ActivityBillVO activityBillVo) {
        // 校验活动状态
        if (!Constants.ActivityState.DOING.getCode().equals(activityBillVo.getState())) {
            log.warn("活动 - {} 不可用，当前状态 - {}", req.getActivityId(), activityBillVo.getState());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), "当前无法参加该活动");
        }

        // 校验日期
        if (activityBillVo.getEndDateTime().before(req.getPartakeDate()) || activityBillVo.getBeginDateTime().after(req.getPartakeDate())) {
            log.warn("活动 - {} 还未开启(已结束), 请求时间 - {}", req.getActivityId(), req.getPartakeDate());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), "活动暂未开始(或已结束)");
        }

        // 校验库存
        if (activityBillVo.getStockSurplusCount() <= 0) {
            log.warn("活动 - {} 库存已空", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), "活动名额不足");
        }

        // 校验个人剩余领取次数
        if (activityBillVo.getUserTakeLeftCount() != null && activityBillVo.getTakeCount() <= activityBillVo.getUserTakeLeftCount()) {
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), "已参与过该活动");
        }

        return Result.buildSuccessResult();
    }

    @Override
    public Result recordDrawOrder(DrawOrderVo drawOrderVo) {
        try {
            // 变更用户活动单领取信息
            int updateCount = userTakeActivityRepository.useTakeActivity(drawOrderVo.getUId(), drawOrderVo.getActivityId(), drawOrderVo.getTakeId());
            if (updateCount == 0) {
                log.error("记录中奖单，当前 activityId：{} uId：{}", drawOrderVo.getActivityId(), drawOrderVo.getUId());
                return Result.buildResult(Constants.ResponseCode.REPLACE_TAKE);
            }

            // 扣减中奖品的库存
            if (!Constants.DrawState.FAIL.getCode().equals(drawOrderVo.getDrawState())) {
                Boolean state = redisRepository.lock(Constants.Cache.STRATEGY_DRAW_STOCK_LOCK_KEY + drawOrderVo.getAwardId(), () -> strategyRepository.deductStock(drawOrderVo.getStrategyId(), drawOrderVo.getAwardId()));
                if (Boolean.FALSE.equals(state)) {
                    // 商品已空就显示未中奖
                    drawOrderVo.setDrawState(Constants.DrawState.FAIL.getCode());
                    drawOrderVo.setOrderId(-1L);
                }
            }

            userTakeActivityRepository.saveUserStrategyExport(drawOrderVo);
        } catch (DuplicateKeyException e) {
            log.error("记录中奖单，唯一索引冲突 activityId：{} uId：{}", drawOrderVo.getActivityId(), drawOrderVo.getUId(), e);
            return Result.buildResult(Constants.ResponseCode.REPLACE_TAKE);
        }
        return Result.buildSuccessResult();
    }

    @Override
    public void updateInvoiceMqState(String uId, Long orderId, Integer mqState) {
        userTakeActivityRepository.updateInvoiceMqState(uId, orderId, mqState);
    }

    @Override
    public List<InvoiceVO> scanInvoiceMqState(long startId, Integer count) {
        return userTakeActivityRepository.scanInvoiceMqState(startId, count);
    }
}
