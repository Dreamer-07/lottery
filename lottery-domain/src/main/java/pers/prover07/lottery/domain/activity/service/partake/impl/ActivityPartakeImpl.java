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
import pers.prover07.lottery.domain.activity.service.partake.BaseActivityPartake;

import javax.annotation.PostConstruct;

/**
 * 参与活动业务类
 *
 * @author Prover07
 * @date 2023/9/5 17:02
 */
@Service
@Slf4j
public class ActivityPartakeImpl extends BaseActivityPartake {

    private final DefaultRedisScript<Long> activityStockCountIncrScript = new DefaultRedisScript<>();

    @PostConstruct
    public void init() {
        activityStockCountIncrScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/Incrby.lua")));
        activityStockCountIncrScript.setResultType(Long.class);
    }

    @Override
    protected Result grabActivity(PartakeReq partake, ActivityBillVO activityBillVo, long takeId) {
        try {
            // 增加 redis 中的已领取次数
            double takeCount = redisRepository.hIncr(Constants.Cache.ACTIVITY_PARTAKE_USER_TAKE_COUNT_KEY + partake.getActivityId(), partake.getUId(), 1);
            if (takeCount > activityBillVo.getTakeCount()) {
                redisRepository.hIncr(Constants.Cache.ACTIVITY_PARTAKE_USER_TAKE_COUNT_KEY + partake.getActivityId(), partake.getUId(), -1);
                return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), "已参与过该活动");
            }

            // mysql 中同步记录
            userTakeActivityRepository.takeActivity(partake, activityBillVo, takeId);
        } catch (DuplicateKeyException exception) {
            log.error("领取活动，唯一索引冲突 activityId：{} uId：{}", partake.getActivityId(), partake.getUId(), exception);
            return Result.buildResult(Constants.ResponseCode.REPLACE_TAKE);
        } catch (Exception exception) {
            log.error("领取活动，出现未知错误", exception);
            return Result.buildErrorResult();
        }

        return Result.buildSuccessResult();
    }

    @Override
    protected boolean subtractionActivityStock(PartakeReq req) {
        // 扣减 redis 库存
        long stock = redisRepository.execute(activityStockCountIncrScript, ListUtil.of(Constants.Cache.ACTIVITY_STOCK_COUNT_KEY + req.getActivityId()));
        log.info("扣减 redis 库存: {}, {}", stock, Constants.Cache.ACTIVITY_STOCK_COUNT_KEY + req.getActivityId());
        // 扣减数据库库存
        if (stock > 0) {
            return activityRepository.subtractionActivityStock(req.getActivityId());
        }

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
}
