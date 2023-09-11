package pers.prover07.lottery.application.process;

import pers.prover07.lottery.application.process.req.DrawProcessReq;
import pers.prover07.lottery.application.process.res.DrawProcessRes;

/**
 * 活动抽奖编排接口
 *
 * @author Prover07
 * @date 2023/9/9 16:02
 */
public interface IActivityProcess {

    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessRes doDrawProcess(DrawProcessReq req);

}
