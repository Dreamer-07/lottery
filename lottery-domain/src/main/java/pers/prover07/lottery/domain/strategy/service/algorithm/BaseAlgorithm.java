package pers.prover07.lottery.domain.strategy.service.algorithm;

import pers.prover07.lottery.domain.strategy.model.vo.AwardRateInfo;
import pers.prover07.lottery.common.Constants;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置抽奖算法运行的的上下文环境
 *
 * @author Prover07
 * @date 2023/8/28 14:55
 */
public abstract class BaseAlgorithm implements IDrawAlgorithm {

    /**
     * 斐波那契散列增量，逻辑：黄金分割点：(√5 - 1) / 2 = 0.6180339887，Math.pow(2, 32) * 0.6180339887 = 0x61c88647
     */
    private final int HASH_INCREMENT = 0x61c88647;

    /**
     * 数组初始化长度 128，保证数据填充时不发生碰撞的最小初始化值
     */
    private final int RATE_TUPLE_LENGTH = 128;

    /**
     * 存放概率与奖品对应的散列结果，strategyId -> rateTuple
     */
    protected Map<Long, String[]> rateTupleMap = new ConcurrentHashMap<>();

    /**
     * 奖品区间概率值，strategyId -> [awardId->begin、awardId->end]
     */
    protected Map<Long, List<AwardRateInfo>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public void initRateTuple(Long strategyId, Integer strategyMode, List<AwardRateInfo> awardRateInfoList) {
        // 判断改概率元组是否已经存在
        if (isExists(strategyId)) {
            return;
        }

        // 保存概率信息
        awardRateInfoMap.put(strategyId, awardRateInfoList);

        // 如果是总体概率算法，不需要缓存，这部分算法需要实时处理中奖概率
        if (Constants.DrawStrategyMode.ENTIRETY.getCode().equals(strategyMode)) {
            return;
        }

        String[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]);

        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : awardRateInfoList) {
            int rateVal = awardRateInfo.getAwardRate().multiply(new BigDecimal(100)).intValue();

            for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i++) {
                rateTuple[hashIdx(i)] = awardRateInfo.getAwardId();
            }

            cursorVal += rateVal;
        }
    }

    @Override
    public boolean isExists(Long strategyId) {
        return awardRateInfoMap.containsKey(strategyId);
    }

    protected int hashIdx(int val) {
        int hashCode = val * HASH_INCREMENT + HASH_INCREMENT;
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }

    /**
     * 生成 [0, bound) 之间的抽奖码
     *
     * @param bound
     * @return
     */
    protected int generaSecureRandomIntCode(int bound) {
        return new SecureRandom().nextInt(bound) + 1;
    }
}
