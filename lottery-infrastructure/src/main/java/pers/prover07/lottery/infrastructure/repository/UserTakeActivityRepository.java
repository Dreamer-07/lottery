package pers.prover07.lottery.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.vo.ActivityBillVO;
import pers.prover07.lottery.domain.activity.model.vo.UserTakeActivityVO;
import pers.prover07.lottery.domain.activity.repository.IUserTakeActivityRepository;
import pers.prover07.lottery.infrastructure.dao.IUserTakeActivityDao;
import pers.prover07.lottery.infrastructure.po.UserTakeActivity;

import java.util.Optional;

/**
 * 用户参与活动仓储接口
 *
 * @author Prover07
 * @date 2023/9/5 15:01
 */
@Component
@RequiredArgsConstructor
public class UserTakeActivityRepository implements IUserTakeActivityRepository {

    private final IUserTakeActivityDao userTakeActivityDao;

    @Override
    public UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId) {
        LambdaQueryWrapper<UserTakeActivity> queryWrapper = Wrappers.<UserTakeActivity>lambdaQuery()
                .eq(UserTakeActivity::getActivityId, activityId)
                .eq(UserTakeActivity::getUId, uId)
                .eq(UserTakeActivity::getState, Constants.TaskState.NO_USED.getCode())
                .last("limit 1");

        UserTakeActivity userTakeActivity = userTakeActivityDao.selectOne(queryWrapper);

        if (userTakeActivity == null) {
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        BeanUtils.copyProperties(userTakeActivity, userTakeActivityVO);

        return userTakeActivityVO;
    }

    @Override
    public void takeActivity(PartakeReq partake, ActivityBillVO activityBillVo, long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();

        BeanUtils.copyProperties(partake, userTakeActivity);
        BeanUtils.copyProperties(activityBillVo, userTakeActivity);

        userTakeActivity.setTakeDate(partake.getPartakeDate());
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setTakeCount(activityBillVo.getUserTakeLeftCount() + 1);
        userTakeActivity.setUuid(partake.getUId() + "_" + activityBillVo.getActivityId() + userTakeActivity.getTakeCount());
        userTakeActivity.setState(Constants.TaskState.NO_USED.getCode());

        userTakeActivityDao.insert(userTakeActivity);
    }

}
