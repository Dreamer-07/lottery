package pers.prover07.lottery.domain.support.ids.policy;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;

/**
 * 基于 {@link org.apache.commons.lang3.RandomStringUtils} 生成随机id
 *
 * @author Prover07
 * @date 2023/9/5 14:15
 */
@Component
public class RandomNumeric implements IIdGenerator {
    @Override
    public long nextId() {
        return Long.parseLong(RandomStringUtils.randomNumeric(11));
    }
}
