package pers.prover07.lottery.application.mq;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.vo.InvoiceVO;
import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.service.factory.DistributionGoodsFactory;
import pers.prover07.lottery.domain.award.service.goods.IDistributionGoods;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * @author Prover07
 * @date 2023/7/12 10:06
 */
@Slf4j
@Component
public class KafkaConsumer {

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @KafkaListener(topics = Constants.MessageTopic.INVOICE)
    public void addLikeCountForArticle(ConsumerRecord<String, Object> message, Acknowledgment ack) {
        this.messageConsumer(message, ack, InvoiceVO.class, (InvoiceVO invoiceVO) -> {
            IDistributionGoods distributionGoods = distributionGoodsFactory.getDistributionGoods(invoiceVO.getAwardType());
            GoodsReq goodsReq = new GoodsReq();
            BeanUtils.copyProperties(invoiceVO, goodsReq);

            distributionGoods.distribution(goodsReq);
        });
    }

    /**
     * 消息具体的消费逻辑
     *
     * @param message
     * @param ack
     * @param paramClass
     * @param consumer
     * @param <T>
     */
    private <T> void messageConsumer(ConsumerRecord<String, Object> message, Acknowledgment ack,
                                     Class<T> paramClass,
                                     Consumer<T> consumer) {
        if (message.value() == null) {
            log.warn("kafka consumer message is null - topic: {}, message-id: {}", message.topic(), message.key());
            return;
        }

        log.info("kafka consumer - topic: {}, message-id: {}, message: {}", message.topic(), message.key(), message.value());

        T bean = JSONUtil.toBean((String) message.value(), paramClass);
        try {
            consumer.accept(bean);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("kafka consumer throw exception: {}, data: {}", e.getMessage(), message.value(), e);
            throw e;
        }
    }

}
