package pers.prover07.lottery.domain.activity.service.deploy;

import pers.prover07.lottery.domain.activity.model.req.ActivityConfigReq;
import pers.prover07.lottery.domain.activity.model.vo.ActivityVO;

import java.util.List;

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

    /**
     * 扫描活动列表(只扫描大于 startId 的数据，且返回 count 个)
     * @return
     */
    List<ActivityVO> scanToDoActivityList(Long startId, Integer count);
}
