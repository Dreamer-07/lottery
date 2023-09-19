package pers.prover07.lottery.interfaces.facde;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.prover07.lottery.application.process.IActivityProcess;
import pers.prover07.lottery.application.process.req.DrawProcessReq;
import pers.prover07.lottery.application.process.res.DrawProcessRes;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;
import pers.prover07.lottery.interfaces.assembler.IMapping;
import pers.prover07.lottery.rpc.ILotteryActivityBooth;
import pers.prover07.lottery.rpc.dto.AwardDTO;
import pers.prover07.lottery.rpc.req.DrawReq;
import pers.prover07.lottery.rpc.res.DrawRes;

import javax.annotation.Resource;

/**
 * 抽奖活动展台
 *
 * @author Prover07
 * @date 2023/9/9 16:35
 */
@RestController
@Slf4j
@RequestMapping("/activity")
public class LotteryActivityBooth implements ILotteryActivityBooth {

    @Autowired
    private IActivityProcess activityProcess;

    @Autowired
    private IMapping<DrawAwardVO, AwardDTO> awardMapping;

    @GetMapping("/draw")
    @Override
    public DrawRes doDraw(DrawReq drawReq) {
        log.info("抽奖，开始 uId：{} activityId：{}", drawReq.getUId(), drawReq.getActivityId());

        // 执行抽奖
        DrawProcessRes drawProcessRes = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getUId(), drawReq.getActivityId()));
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessRes.getCode())) {
            log.error("抽奖，失败(抽奖过程异常) uId：{} activityId：{}", drawReq.getUId(), drawReq.getActivityId());
            return new DrawRes(drawProcessRes.getCode(), drawProcessRes.getInfo());
        }

        // 2. 数据转换
        DrawAwardVO drawAwardVO = drawProcessRes.getDrawAwardVO();
        AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
        awardDTO.setActivityId(drawReq.getActivityId());

        // 3. 封装数据
        DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        drawRes.setAwardDTO(awardDTO);
        log.info("抽奖，完成 uId：{} activityId：{} drawRes：{}", drawReq.getUId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));

        return drawRes;
    }
}
