package pers.prover07.lottery.rpc;

import pers.prover07.lottery.rpc.req.ActivityReq;
import pers.prover07.lottery.rpc.req.DrawReq;
import pers.prover07.lottery.rpc.res.ActivityRes;
import pers.prover07.lottery.rpc.res.DrawRes;

/**
 * 活动展台
 *  1. 创建活动
 *  2. 更新活动
 *  3. 查询活动
 *  4. 参与活动抽奖
 *
 * @author Prover07
 * @date 2023/8/25 16:00
 */
public interface ILotteryActivityBooth {


    /**
     * 参与活动抽奖
     * @param drawReq
     * @return
     */
    DrawRes doDraw(DrawReq drawReq);



}
