package pers.prover07.lottery.domain.activity.service.deploy;

import pers.prover07.lottery.domain.activity.model.req.ActivityConfigReq;

/**
 * 活动部署配置接口
 *
 * @author Prover07
 * @date 2023/9/4 15:12
 */
public interface IActivityDeploy {

    /**
     * 创建活动
     * @param activityConfigReq
     */
    void createActivity(ActivityConfigReq activityConfigReq);

}
