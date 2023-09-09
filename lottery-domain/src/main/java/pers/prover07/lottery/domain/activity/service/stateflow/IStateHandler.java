package pers.prover07.lottery.domain.activity.service.stateflow;

import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;

/**
 * 状态处理器
 *
 * @author Prover07
 * @date 2023/9/7 15:50
 */
public interface IStateHandler {

    /**
     * 提审
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result arraignment(Long activityId, Constants.ActivityState currentStatus);

    /**
     * 审核通过
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result checkPass(Long activityId, Constants.ActivityState currentStatus);

    /**
     * 审核拒绝
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result checkRefuse(Long activityId, Constants.ActivityState currentStatus);

    /**
     * 撤销审核
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result checkRevoke(Long activityId, Constants.ActivityState currentStatus);

    /**
     * 关闭
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result close(Long activityId, Constants.ActivityState currentStatus);

    /**
     * 开启
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result open(Long activityId, Constants.ActivityState currentStatus);

    /**
     * 运行活动中
     *
     * @param activityId    活动ID
     * @param currentStatus 当前状态
     * @return 审核结果
     */
    Result doing(Long activityId, Constants.ActivityState currentStatus);

}
