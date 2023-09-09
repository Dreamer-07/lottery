package pers.prover07.lottery.domain.activity.repository;

import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.vo.ActivityBillVO;
import pers.prover07.lottery.domain.activity.model.vo.UserTakeActivityVO;

/**
 * 用户参与活动仓储接口
 *
 * @author Prover07
 * @date 2023/9/5 15:00
 */
public interface IUserTakeActivityRepository {
    /**
     * 查找未执行但已经领取的活动单
     * @param activityId
     * @param uId
     * @return
     */
    UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 用户领取(参与)活动记录
     * @param partake
     * @param activityBillVo
     * @param takeId
     */
    void takeActivity(PartakeReq partake, ActivityBillVO activityBillVo, long takeId);
}
