package pers.prover07.lottery.interfaces.facde;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.prover07.lottery.application.process.IActivityProcess;
import pers.prover07.lottery.application.process.req.DrawProcessReq;
import pers.prover07.lottery.application.process.res.DrawProcessRes;

/**
 * 抽奖活动展台
 *
 * @author Prover07
 * @date 2023/9/9 16:35
 */
@RestController
@Slf4j
@RequestMapping("/activity")
public class LotteryActivityBooth {

    @Autowired
    private IActivityProcess activityProcess;

    @GetMapping("/draw")
    public DrawProcessRes doDraw(DrawProcessReq drawProcessReq) {
        return activityProcess.doDrawProcess(drawProcessReq);
    }

}
