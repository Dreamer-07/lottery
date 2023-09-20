package pers.prover07.lottery.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.vo.*;
import pers.prover07.lottery.domain.activity.repository.IActivityRepository;
import pers.prover07.lottery.infrastructure.dao.IActivityDao;
import pers.prover07.lottery.infrastructure.dao.IAwardDao;
import pers.prover07.lottery.infrastructure.dao.IStrategyDao;
import pers.prover07.lottery.infrastructure.dao.IStrategyDetailDao;
import pers.prover07.lottery.infrastructure.po.Activity;
import pers.prover07.lottery.infrastructure.po.Award;
import pers.prover07.lottery.infrastructure.po.Strategy;
import pers.prover07.lottery.infrastructure.po.StrategyDetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 活动仓储表服务
 *
 * @author Prover07
 * @date 2023/9/4 15:47
 */
@Component
@RequiredArgsConstructor
public class ActivityRepository implements IActivityRepository {

    private final IActivityDao activityDao;

    private final IStrategyDao strategyDao;

    private final IStrategyDetailDao strategyDetailDao;

    private final IAwardDao awardDao;

    private final RedisRepository redisRepository;

    @Override
    public void addActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        activity.setStockSurplusCount(activityVO.getStockCount());
        activityDao.insert(activity);
    }

    @Override
    public void addStrategy(StrategyVO strategyVO) {
        Strategy strategy = new Strategy();
        BeanUtils.copyProperties(strategyVO, strategy);
        strategyDao.insert(strategy);
    }

    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailVOList) {
        List<StrategyDetail> strategyDetailList = strategyDetailVOList.stream().map((StrategyDetailVO strategyDetailVO) -> {
            StrategyDetail strategyDetail = new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            return strategyDetail;
        }).collect(Collectors.toList());

        strategyDetailDao.insertList(strategyDetailList);
    }

    @Override
    public void addAwardList(List<AwardVO> awardVOList) {
        List<Award> awardList = awardVOList.stream().map((AwardVO awardVO) -> {
            Award award = new Award();
            BeanUtils.copyProperties(awardVO, award);
            return award;
        }).collect(Collectors.toList());

        awardDao.insertList(awardList);
    }

    @Override
    public ActivityBillVO queryActivityBill(PartakeReq req) {
        // 查找活动基本信息
        Activity activity = activityDao.selectOne(
                Wrappers.<Activity>lambdaQuery()
                        .eq(Activity::getActivityId, req.getActivityId())
        );

        if (!Optional.ofNullable(activity).isPresent()) {
            return null;
        }

        String key = Constants.Cache.ACTIVITY_PARTAKE_USER_TAKE_COUNT_KEY + req.getActivityId();
        Integer takeCount = null;
        if (redisRepository.hasKey(key)) {
            // 查找用户已经领取的次数
            takeCount = (Integer) redisRepository.hGet(key, req.getUId());
        }

        ActivityBillVO activityBillVO = new ActivityBillVO();
        BeanUtils.copyProperties(activity, activityBillVO);
        activityBillVO.setUId(req.getUId());
        activityBillVO.setUserTakeLeftCount(takeCount == null ? 0 : takeCount);
        activityBillVO.setStockSurplusCount(Optional.ofNullable(activityBillVO.getStockSurplusCount()).orElse(activityBillVO.getStockCount()));

        return activityBillVO;
    }

    @Override
    public boolean subtractionActivityStock(Long activityId) {
        return 1 == activityDao.deductStock(activityId);
    }

    @Override
    public boolean alterStatus(Long activityId, Constants.ActivityState currentState, Constants.ActivityState pass) {
        return 1 == activityDao.alterStatus(activityId, currentState.getCode(), pass.getCode());
    }
}
