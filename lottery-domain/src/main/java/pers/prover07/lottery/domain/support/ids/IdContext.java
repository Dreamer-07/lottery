package pers.prover07.lottery.domain.support.ids;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.support.ids.policy.RandomNumeric;
import pers.prover07.lottery.domain.support.ids.policy.ShortCode;
import pers.prover07.lottery.domain.support.ids.policy.SnowFlakeCode;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Id 生成器的上下文环境配置
 *
 * @author Prover07
 * @date 2023/9/5 14:34
 */
@Configuration
@RequiredArgsConstructor
public class IdContext {


    private final RandomNumeric randomNumeric;

    private final ShortCode shortCode;

    private final SnowFlakeCode snowFlakeCode;

    @Bean
    public Map<Constants.Ids, IIdGenerator> idGeneratorMap() {
        Map<Constants.Ids, IIdGenerator> idGeneratorMap = new HashMap<>(3);
        idGeneratorMap.put(Constants.Ids.RANDOM_NUMERIC, randomNumeric);
        idGeneratorMap.put(Constants.Ids.SHORT_CODE, shortCode);
        idGeneratorMap.put(Constants.Ids.SNOW_FLAKE, snowFlakeCode);

        return idGeneratorMap;
    }


}
