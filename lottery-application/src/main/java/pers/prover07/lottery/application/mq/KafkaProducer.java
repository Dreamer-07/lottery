package pers.prover07.lottery.application.mq;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Prover07
 * @date 2023/7/12 9:16
 */
@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public ListenableFuture<SendResult<String, Object>> sendMessage(KafkaMessageVo kafkaMessageVo) {

        log.info("投递 kafka 队列消息 - status: prepare, data: {}", kafkaMessageVo);

        // 发送到 kafka
        return kafkaTemplate.send(kafkaMessageVo.getTopic(), kafkaMessageVo.getMessageId(), JSONUtil.toJsonStr(kafkaMessageVo.getMessage()));
    }

}
