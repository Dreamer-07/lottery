package pers.prover07.lottery.domain.activity.repository;

import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.vo.ActivityBillVO;
import pers.prover07.lottery.domain.activity.model.vo.InvoiceVO;
import pers.prover07.lottery.domain.activity.model.vo.UserTakeActivityVO;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;

import java.util.List;

/**
 * 用户参与活动仓储接口
 *
 * @author Prover07
 * @date 2023/9/5 15:00
 */
public interface IUserTakeActivityRepository {
    /**
     * 查找未执行但已经领取的活动单
     * @param activityId
     * @param uId
     * @return
     */
    UserTakeActivityVO queryNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 用户领取(参与)活动记录
     * @param partake
     * @param activityBillVo
     * @param takeId
     */
    void takeActivity(PartakeReq partake, ActivityBillVO activityBillVo, long takeId);

    /**
     * 保存中奖信息
     * @param drawOrderVo
     */
    void saveUserStrategyExport(DrawOrderVo drawOrderVo);

    /**
     * 修改 mq 发奖状态
     * @param uId
     * @param orderId
     * @param mqState
     */
    void updateInvoiceMqState(String uId, Long orderId, Integer mqState);

    /**
     * 用户使用活动单参与抽奖
     * @param uId
     * @param activityId
     * @param takeId
     * @return
     */
    int useTakeActivity(String uId, Long activityId, Long takeId);

    List<InvoiceVO> scanInvoiceMqState(long startId, Integer count);
}
