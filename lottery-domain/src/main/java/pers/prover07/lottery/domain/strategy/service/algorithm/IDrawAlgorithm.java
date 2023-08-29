package pers.prover07.lottery.domain.strategy.service.algorithm;

import pers.prover07.lottery.domain.strategy.model.vo.AwardRateInfo;

import java.util.List;

/**
 * 抽奖算法接口
 *
 * @author Prover07
 * @date: 2023/8/28 14:22
 */
public interface IDrawAlgorithm {

    /**
     * 初始化概率矩阵
     *
     * @param strategyId            策略ID
     * @param strategyMode          抽奖策略模式
     * @param awardRateInfoList     奖品概率配置集合(奖品和概率值的对应关系)
     */
    void initRateTuple(Long strategyId, Integer strategyMode, List<AwardRateInfo> awardRateInfoList);

    /**
     * 判断对应的抽奖策略是否已经初始化完成
     * @param strategyId
     * @return
     */
    boolean isExists(Long strategyId);

    /**
     * 生成随机数并返回抽到的奖品信息
     * @param strategyId
     * @param excludeAwardIds
     * @return
     */
    String randomDraw(Long strategyId, List<String> excludeAwardIds);

}
