package pers.prover07.lottery.domain.activity.service.partake;

import org.springframework.transaction.annotation.Transactional;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.res.PartakeRes;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;

/**
 * 活动参与接口
 *
 * @author Prover07
 * @date 2023/9/4 17:33
 */
public interface IActivityPartake {


    /**
     * 参与活动
     * @param req
     * @return
     */
    PartakeRes doPartake(PartakeReq req);

    /**
     * 记录中奖订单信息
     * @param drawOrderVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Result recordDrawOrder(DrawOrderVo drawOrderVo);

    /**
     * 修改发奖的状态
     * @param uId
     * @param orderId
     * @param mqState
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);
}
