package pers.prover07.lottery.domain.activity.service.stateflow.impl;

import org.springframework.stereotype.Service;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.service.stateflow.IStateHandler;
import pers.prover07.lottery.domain.activity.service.stateflow.StateConfig;

/**
 * 状态处理器
 *
 * @author Prover07
 * @date 2023/9/7 15:53
 */
@Service
public class StateHandler extends StateConfig implements IStateHandler {

    @Override
    public Result arraignment(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).arraignment(activityId, currentStatus);
    }

    @Override
    public Result checkPass(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).checkPass(activityId, currentStatus);
    }

    @Override
    public Result checkRefuse(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).checkRefuse(activityId, currentStatus);
    }

    @Override
    public Result checkRevoke(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).checkRevoke(activityId, currentStatus);
    }

    @Override
    public Result close(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).close(activityId, currentStatus);
    }

    @Override
    public Result open(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).open(activityId, currentStatus);
    }

    @Override
    public Result doing(Long activityId, Constants.ActivityState currentStatus) {
        return stateGroup.get(currentStatus).doing(activityId, currentStatus);
    }

}
