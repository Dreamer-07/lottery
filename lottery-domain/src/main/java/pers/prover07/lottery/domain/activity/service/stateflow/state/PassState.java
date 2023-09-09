package pers.prover07.lottery.domain.activity.service.stateflow.state;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.service.stateflow.AbstractState;

/**
 * 审核通过状态
 *
 * @author Prover07
 * @date 2023/9/7 15:40
 */
@Component
public class PassState extends AbstractState {
    @Override
    public Result arraignment(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult( "已审核状态不可重复提审");
    }

    @Override
    public Result checkPass(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult( "已审核状态不可重复审核");
    }

    @Override
    public Result checkRefuse(Long activityId, Constants.ActivityState currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.REFUSE);
        return isSuccess ? Result.buildSuccessResult( "活动审核拒绝完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result checkRevoke(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult( "审核通过不可撤销(可先拒绝审核)");
    }

    @Override
    public Result close(Long activityId, Constants.ActivityState currentState) {
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.CLOSE);
        return isSuccess ? Result.buildSuccessResult( "活动审核关闭完成") : Result.buildErrorResult("活动状态变更失败");
    }

    @Override
    public Result open(Long activityId, Constants.ActivityState currentState) {
        return Result.buildErrorResult( "非关闭活动不可开启");
    }

    @Override
    public Result doing(Long activityId, Constants.ActivityState currentState) {
        System.out.println("activityId:"+activityId+" currentState:"+currentState);
        boolean isSuccess = activityRepository.alterStatus(activityId, currentState, Constants.ActivityState.DOING);
        return isSuccess ? Result.buildSuccessResult( "活动状态变更为活动中完成") : Result.buildErrorResult("活动状态变更失败");
    }
}
