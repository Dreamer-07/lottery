package pers.prover07.lottery.domain.activity.service.stateflow.state;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.service.stateflow.AbstractState;

/**
 * 审核拒绝状态
 *
 * @author Prover07
 * @date 2023/9/7 15:41
 */
@Component
public class RefuseState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("已审核状态不可重复提审");
    }

    @Override
    public Result checkPass(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("已审核状态不可重复审核");
    }

    @Override
    public Result checkRefuse(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("活动审核拒绝不可重复审核");
    }

    @Override
    public Result checkRevoke(Long activityId, Constants.ActivityState currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.EDIT);
        return isSuccess ? Result.buildSuccessResult("撤销审核完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result close(Long activityId, Constants.ActivityState currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.CLOSE);
        return isSuccess ? Result.buildSuccessResult("活动审核关闭完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("非关闭活动不可开启");
    }

    @Override
    public Result doing(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult("审核拒绝不可执行活动为进行中");
    }
}
