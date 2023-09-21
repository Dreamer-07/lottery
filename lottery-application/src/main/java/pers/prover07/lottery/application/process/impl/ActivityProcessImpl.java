package pers.prover07.lottery.application.process.impl;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import pers.prover07.lottery.application.mq.KafkaMessageVo;
import pers.prover07.lottery.application.mq.KafkaProducer;
import pers.prover07.lottery.application.process.IActivityProcess;
import pers.prover07.lottery.application.process.req.DrawProcessReq;
import pers.prover07.lottery.application.process.res.DrawProcessRes;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.res.PartakeRes;
import pers.prover07.lottery.domain.activity.model.vo.InvoiceVO;
import pers.prover07.lottery.domain.activity.service.partake.IActivityPartake;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;
import pers.prover07.lottery.domain.strategy.model.req.DrawReq;
import pers.prover07.lottery.domain.strategy.model.res.DrawRes;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;
import pers.prover07.lottery.domain.strategy.service.draw.IDrawExec;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

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


    private Queue<Integer> minStack = new ArrayDeque<>();
    @Resource
    private KafkaProducer kafkaProducer;

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public DrawProcessRes doDrawProcess(DrawProcessReq req) {
        PartakeReq partakeReq = new PartakeReq(req.getUId(), req.getActivityId());
        PartakeRes partakeRes = activityPartake.doPartake(partakeReq);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(partakeRes.getCode())) {
            return new DrawProcessRes(partakeRes.getCode(), partakeRes.getInfo());
        }

        // 执行抽奖
        DrawReq drawReq = new DrawReq(req.getUId(), partakeRes.getStrategyId(), idGeneratorMap.get(Constants.Ids.RANDOM_NUMERIC).toString());
        DrawRes drawRes = drawExec.doDrawExec(drawReq);

        // 中奖结果保存
        DrawAwardVO drawAwardVO = drawRes.getDrawAwardInfo();
        DrawOrderVo drawOrderVo = buildDrawOrderVo(req, partakeRes.getStrategyId(), partakeRes.getTakeId(), drawAwardVO, drawRes.getDrawState());
        Result result = activityPartake.recordDrawOrder(drawOrderVo);
        if (Constants.DrawState.FAIL.getCode().equals(drawRes.getDrawState())) {
            return new DrawProcessRes(Constants.ResponseCode.DRAW_FAIL.getCode(), Constants.ResponseCode.DRAW_FAIL.getInfo());
        }
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(result.getCode())) {
            return new DrawProcessRes(result.getCode(), result.getInfo());
        }

        // 发送 mq，触发发奖流程
        InvoiceVO invoiceVO = buildInvoiceVo(drawOrderVo);
        ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendMessage(KafkaMessageVo.builder()
                .topic(Constants.MessageTopic.INVOICE)
                .message(invoiceVO)
                .build());

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            /**
             * 发送失败
             * @param ex
             */
            @Override
            public void onFailure(Throwable ex) {
                activityPartake.updateInvoiceMqState(invoiceVO.getUId(), invoiceVO.getOrderId(), Constants.MQState.FAIL.getCode());
            }

            /**
             * 发送成功
             * @param result
             */
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                activityPartake.updateInvoiceMqState(invoiceVO.getUId(), invoiceVO.getOrderId(), Constants.MQState.COMPLETE.getCode());
            }
        });

        // 5. 返回结果
        return new DrawProcessRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardVO);
    }

    private InvoiceVO buildInvoiceVo(DrawOrderVo drawOrderVo) {
        InvoiceVO invoiceVO = new InvoiceVO();
        BeanUtils.copyProperties(drawOrderVo, invoiceVO);
        return invoiceVO;
    }

    /**
     * 将中奖信息封装成用户领取订单信息
     *
     * @param req
     * @param strategyId
     * @param takeId
     * @param drawAwardVO
     * @return
     */
    private DrawOrderVo buildDrawOrderVo(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardVO, Integer drawState) {
        DrawOrderVo drawOrderVo = new DrawOrderVo();

        if (drawAwardVO != null) {
            BeanUtils.copyProperties(drawAwardVO, drawOrderVo);
            long orderId = idGeneratorMap.get(Constants.Ids.SNOW_FLAKE).nextId();
            drawOrderVo.setOrderId(orderId);
            drawOrderVo.setGrantState(Constants.GrantState.INIT.getCode());
        } else {
            // 未中奖的记录保存到一张表里
            drawOrderVo.setOrderId(-1L);
        }

        drawOrderVo.setDrawState(drawState);
        drawOrderVo.setTakeId(takeId);
        drawOrderVo.setStrategyId(strategyId);
        drawOrderVo.setUId(req.getUId());
        drawOrderVo.setActivityId(req.getActivityId());
        return drawOrderVo;
    }
}
