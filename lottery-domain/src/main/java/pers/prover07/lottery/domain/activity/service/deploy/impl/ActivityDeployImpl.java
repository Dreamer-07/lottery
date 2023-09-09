package pers.prover07.lottery.domain.activity.service.deploy.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import pers.prover07.lottery.domain.activity.model.req.ActivityConfigReq;
import pers.prover07.lottery.domain.activity.repository.IActivityRepository;
import pers.prover07.lottery.domain.activity.service.deploy.IActivityDeploy;
import pers.prover07.lottery.domain.support.cache.IRedisRepository;

/**
 * 活动配置接口实现
 *
 * @author Prover07
 * @date 2023/9/4 15:12
 */
@Service
@Slf4j
public class ActivityDeployImpl implements IActivityDeploy {

    @Autowired
    private IActivityRepository activityRepository;

    @Autowired
    private IRedisRepository redisRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createActivity(ActivityConfigReq activityConfigReq) {
        Long activityId = activityConfigReq.getActivityId();
        log.info("创建活动: id - {}", activityId);
        ActivityConfigRich activityConfigRich = activityConfigReq.getActivityConfigRich();

        try {
            activityRepository.addActivity(activityConfigRich.getActivity());

            activityRepository.addStrategy(activityConfigRich.getStrategy());

            activityRepository.addStrategyDetailList(activityConfigRich.getStrategy().getStrategyDetailList());

            activityRepository.addAwardList(activityConfigRich.getAwardList());

            // 在 redis 中预留库存
            redisRepository.set(Constants.Cache.ACTIVITY_STOCK_COUNT_KEY + activityId, activityConfigRich.getActivity().getStockCount());
            log.info("创建活动：status - success, activityId - {}", activityId);
        } catch (DuplicateKeyException exception) {
            log.error("创建活动：status - error, message - activityId: {} 重复, data - {}", activityId, activityConfigReq, exception);
            throw exception;
        }
    }

}
