package pers.prover07.lottery.application.process.impl;

import org.springframework.stereotype.Service;
import pers.prover07.lottery.application.process.IActivityProcess;
import pers.prover07.lottery.application.process.req.DrawProcessReq;
import pers.prover07.lottery.application.process.res.DrawProcessRes;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.res.PartakeRes;
import pers.prover07.lottery.domain.activity.service.partake.IActivityPartake;
import pers.prover07.lottery.domain.strategy.service.draw.IDrawExec;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 抽奖活动业务编排实现
 *
 * @author Prover07
 * @date 2023/9/9 16:07
 */
@Service
public class ActivityProcessImpl implements IActivityProcess {

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private IDrawExec drawExec;

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public DrawProcessRes doDrawProcess(DrawProcessReq req) {
        // 领取活动
        PartakeReq partakeReq = new PartakeReq(req.getUId(), req.getActivityId());
        PartakeRes partakeRes = activityPartake.doPartake(partakeReq);
        // if (!Constants.ResponseCode.SUCCESS.getCode().equals(partakeRes.getCode())) {
        //
        // }

        return new DrawProcessRes(partakeRes.getCode(), partakeRes.getInfo());

        // 执行抽奖
        // DrawReq drawReq = new DrawReq(req.getUId(), partakeRes.getStrategyId(), idGeneratorMap.get(Constants.Ids.RANDOM_NUMERIC).toString());
        // DrawRes drawRes = drawExec.doDrawExec(drawReq);
        // if (Constants.DrawState.FAIL.getCode().equals(drawRes.getDrawState())) {
        //     return new DrawProcessRes(Constants.ResponseCode.DRAW_FAIL.getCode(), Constants.ResponseCode.DRAW_FAIL.getInfo());
        // }
        //
        // DrawAwardVO drawAwardVO = drawRes.getDrawAwardInfo();
        //
        // return new DrawProcessRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardVO);
    }
}
