package pers.prover07.lottery.domain.activity.service.partake;

import org.springframework.transaction.annotation.Transactional;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.res.PartakeRes;
import pers.prover07.lottery.domain.activity.model.vo.ActivityBillVO;
import pers.prover07.lottery.domain.activity.model.vo.UserTakeActivityVO;
import pers.prover07.lottery.domain.activity.repository.IActivityRepository;
import pers.prover07.lottery.domain.activity.repository.IUserTakeActivityRepository;
import pers.prover07.lottery.domain.support.cache.IRedisRepository;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 参与活动业务类
 *
 * @author Prover07
 * @date 2023/9/4 17:56
 */
public abstract class BaseActivityPartake implements IActivityPartake {

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Resource
    protected IUserTakeActivityRepository userTakeActivityRepository;

    @Resource
    protected IActivityRepository activityRepository;

    @Resource
    protected IRedisRepository redisRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PartakeRes doPartake(PartakeReq req) {
        // 判断用户是否已经领取过该活动单且没有完成
        UserTakeActivityVO userTakeActivityVO = userTakeActivityRepository.queryNoConsumedTakeActivityOrder(req.getActivityId(), req.getUId());
        if (userTakeActivityVO != null) {
            return buildPartakeResult(userTakeActivityVO.getStrategyId(), userTakeActivityVO.getTakeId());
        }

        // 查询活动信息
        ActivityBillVO activityBillVo = activityRepository.queryActivityBill(req);

        if (activityBillVo == null) {
            return new PartakeRes(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), "无法查找到改活动");
        }

        // 活动信息校验
        Result result = checkActivityBill(req, activityBillVo);
        if (!result.getCode().equals(Constants.ResponseCode.SUCCESS.getCode())) {
            return new PartakeRes(result.getCode(), result.getInfo());
        }

        // 扣减活动库存
        boolean status = subtractionActivityStock(req);
        if (!status) {
            return new PartakeRes(Constants.ResponseCode.ACTIVITY_STOCK_IS_EMPTY.getCode(), Constants.ResponseCode.ACTIVITY_STOCK_IS_EMPTY.getInfo());
        }

        // 插入活动领取信息
        long takeId = idGeneratorMap.get(Constants.Ids.SNOW_FLAKE).nextId();
        Result grabResult = this.grabActivity(req, activityBillVo, takeId);
        if (!grabResult.getCode().equals(Constants.ResponseCode.SUCCESS.getCode())) {
            // 回收库存
            redisRepository.incr(Constants.Cache.STRATEGY_DRAW_STOCK_LOCK_KEY + req.getActivityId(), 1);
            return new PartakeRes(grabResult.getCode(), grabResult.getInfo());
        }

        return buildPartakeResult(activityBillVo.getStrategyId(), takeId);
    }

    /**
     * 领取活动
     * @param req
     * @param activityBillVo
     * @param takeId
     * @return
     */
    protected abstract Result grabActivity(PartakeReq req, ActivityBillVO activityBillVo, long takeId);

    /**
     * 扣减活动库存
     * @param req
     * @return
     */
    protected abstract boolean subtractionActivityStock(PartakeReq req);

    /**
     * 活动信息校验
     *
     * @param req
     * @param activityBillVo
     * @return
     */
    protected abstract Result checkActivityBill(PartakeReq req, ActivityBillVO activityBillVo);

    /**
     * 封装结果【返回的策略ID，用于继续完成抽奖步骤】
     *
     * @param strategyId 策略ID
     * @param takeId     领取ID
     * @return 封装结果
     */
    private PartakeRes buildPartakeResult(Long strategyId, Long takeId) {
        PartakeRes partakeResult = new PartakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        partakeResult.setStrategyId(strategyId);
        partakeResult.setTakeId(takeId);
        return partakeResult;
    }
}
