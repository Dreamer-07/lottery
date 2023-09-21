package pers.prover07.lottery.application.worker;

import cn.hutool.json.JSONUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import pers.prover07.lottery.application.mq.KafkaMessageVo;
import pers.prover07.lottery.application.mq.KafkaProducer;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.model.vo.ActivityVO;
import pers.prover07.lottery.domain.activity.model.vo.InvoiceVO;
import pers.prover07.lottery.domain.activity.service.deploy.IActivityDeploy;
import pers.prover07.lottery.domain.activity.service.partake.IActivityPartake;
import pers.prover07.lottery.domain.activity.service.stateflow.impl.StateHandler;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时任务执行器配置
 *
 * @author Prover07
 * @date 2023/9/20 17:05
 */
@Component
@Slf4j
public class LotteryXxlJob {

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private IActivityDeploy activityDeploy;

    @Resource
    private StateHandler stateHandler;

    @Resource
    private KafkaProducer kafkaProducer;

    private static final Integer DEFAULT_DATA_COUNT = 10;

    /**
     * 更新活动状态
     */
    @XxlJob("lottery_updateActivityStateJobHandler")
    public void updateActivityStateJobHandler() {
        log.info("[xxl-job] --- 开始更新活动状态");

        // 获取正在进行的活动 & 已经通过审核通过的活动
        List<ActivityVO> activityVOList = activityDeploy.scanToDoActivityList(0L, DEFAULT_DATA_COUNT);
        if (activityVOList.isEmpty()) {
            log.info("[xxl-job] --- 扫描活动状态 End 暂无符合需要扫描的活动列表");
            return;
        }

        while (!activityVOList.isEmpty()) {
            activityVOList.forEach((ActivityVO activityVO) -> {
                Integer state = activityVO.getState();
                switch (state) {
                    // 活动状态为审核通过，在临近活动开启时间前，审核活动为活动中。在使用活动的时候，需要依照活动状态核时间两个字段进行判断和使用。
                    case 4:
                        Result state4Result = stateHandler.doing(activityVO.getActivityId(), Constants.ActivityState.PASS);
                        log.info("[xxl-job] --- 扫描活动状态为活动中 结果：{} activityId：{} activityName：{} creator：{}", JSONUtil.toJsonStr(state4Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        break;
                    // 如果日期过了需要关闭活动
                    case 5:
                        if (activityVO.getEndDateTime().before(new Date())) {
                            Result state5Result = stateHandler.close(activityVO.getActivityId(), Constants.ActivityState.DOING);
                            log.info("[xxl-job] --- 扫描活动状态为关闭 结果：{} activityId：{} activityName：{} creator：{}", JSONUtil.toJsonStr(state5Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        }
                        break;
                    default:
                        break;
                }
            });

            // 更新 activityVOList
            activityVOList = activityDeploy.scanToDoActivityList(activityVOList.get(activityVOList.size() - 1).getId(), DEFAULT_DATA_COUNT);
        }

        log.info("[xxl-job] --- 扫描活动状态 End");
    }

    /**
     * 补偿发奖
     */
    @XxlJob("lottery_updateOrderMqStateJobHandler")
    public void updateOrderMqStateJobHandler() {
        log.info("[xxl-job] --- 开始补偿奖品发货");
        // 获取没有发货的奖品
        List<InvoiceVO> invoiceVOList = activityPartake.scanInvoiceMqState(0L, DEFAULT_DATA_COUNT);

        while (!invoiceVOList.isEmpty()) {
            invoiceVOList.forEach((InvoiceVO invoiceVO) -> {
                // mq 补偿
                ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendMessage(KafkaMessageVo.builder().topic(Constants.MessageTopic.INVOICE).message(invoiceVO).build());
                log.info("[xxl-job] --- 开始补偿 orderId 为 {} 的奖品", invoiceVO.getOrderId());
                future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

                    @Override
                    public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                        // MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                        log.info("[xxl-job] --- 补偿 orderId 为 {} 的奖品成功", invoiceVO.getOrderId());
                        activityPartake.updateInvoiceMqState(invoiceVO.getUId(), invoiceVO.getOrderId(), Constants.MQState.COMPLETE.getCode());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        // MQ 消息发送失败，更新数据库表 user_strategy_export.mq_state = 2 【等待定时任务扫码补偿MQ消息】
                        log.warn("[xxl-job] --- 补偿 orderId 为 {} 的奖品失败", invoiceVO.getOrderId());
                        activityPartake.updateInvoiceMqState(invoiceVO.getUId(), invoiceVO.getOrderId(), Constants.MQState.FAIL.getCode());
                    }

                });
            });

            invoiceVOList = activityPartake.scanInvoiceMqState(invoiceVOList.get(invoiceVOList.size() - 1).getId(), DEFAULT_DATA_COUNT);
        }

        log.info("[xxl-job] --- End补偿奖品发货");
    }

}