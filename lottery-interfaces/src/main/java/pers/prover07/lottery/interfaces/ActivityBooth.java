package pers.prover07.lottery.interfaces;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.infrastructure.dao.IActivityDao;
import pers.prover07.lottery.infrastructure.po.Activity;
import pers.prover07.lottery.rpc.IActivityBooth;
import pers.prover07.lottery.rpc.dto.ActivityDto;
import pers.prover07.lottery.rpc.req.ActivityReq;
import pers.prover07.lottery.rpc.res.ActivityRes;

import javax.annotation.Resource;

/**
 * 活动展台
 *
 * @author Prover07
 * @date 2023/8/25 16:04
 */
@Service
public class ActivityBooth implements IActivityBooth {

    @Resource
    private IActivityDao activityDao;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {
        Activity activity = activityDao.queryByActivityId(req.getActivityId());

        ActivityDto activityDto = new ActivityDto();
        BeanUtils.copyProperties(activity, activityDto);

        return new ActivityRes(Result.buildSuccessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
    }
}
