package pers.prover07.lottery.application.mq;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 *
 * @author Prover07
 * @date 2023/7/12 9:06
 */
@Builder
@Getter
@ToString
public class KafkaMessageVo {

    private String topic;

    private String messageId;

    private Object message;

    public KafkaMessageVo(String topic, String messageId, Object message) {
        this.topic = topic;
        this.messageId = StringUtils.isBlank(messageId) ? UUID.randomUUID().toString() : messageId;
        this.message = message;
    }
}
