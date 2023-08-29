package pers.prover07.lottery.domain.strategy.service.algorithm.impl;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.strategy.service.algorithm.BaseAlgorithm;

import java.util.List;

/**
 * 单项概率抽奖算法
 *
 * @author Prover07
 * @date 2023/8/28 15:41
 */
@Component
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        // 获取策略对应的元组
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        if (rateTuple == null) {
            throw new RuntimeException("rate tuple is null! strategyId:" + strategyId);
        }

        // 获取随机索引
        int randomVal = this.generaSecureRandomIntCode(100);
        // 计算索引的 hash 值并得到对应的奖品下标
        int idx = this.hashIdx(randomVal);

        String awardId = rateTuple[idx];
        if (excludeAwardIds.contains(awardId)) {
            return null;
        }
        return awardId;
    }
}
