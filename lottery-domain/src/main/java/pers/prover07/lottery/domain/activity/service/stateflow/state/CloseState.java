package pers.prover07.lottery.domain.activity.service.stateflow.state;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.service.stateflow.AbstractState;

/**
 * 关闭状态
 *
 * @author Prover07
 * @date 2023/9/7 15:08
 */
@Component
public class CloseState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动关闭不可提审");
    }

    @Override
    public Result checkPass(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动关闭不可审核通过");
    }

    @Override
    public Result checkRefuse(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动关闭不可审核拒绝");
    }

    @Override
    public Result checkRevoke(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动关闭不可撤销审核");
    }

    @Override
    public Result close(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动关闭不可重复关闭");
    }

    @Override
    public Result open(Long activityId, Constants.ActivityState currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.OPEN);
        return isSuccess ? Result.buildSuccessResult("活动开启完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result doing(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动关闭不可变更活动中");
    }
}
