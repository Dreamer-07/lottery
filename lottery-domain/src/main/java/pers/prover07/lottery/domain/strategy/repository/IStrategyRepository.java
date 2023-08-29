package pers.prover07.lottery.domain.strategy.repository;

import pers.prover07.lottery.domain.strategy.model.aggregates.StrategyRich;
import pers.prover07.lottery.domain.strategy.model.vo.AwardBriefVO;

import java.util.List;

/**
 * 策略表仓储服务
 *
 * @author Prover07
 * @date 2023/8/28 16:50
 */
public interface IStrategyRepository {

    /**
     * 查找策略信息
     * @param strategyId
     * @return
     */
    StrategyRich queryStrategyRich(Long strategyId);

    /**
     * 查询已经排除的奖品信息
     * @param strategyId
     * @return
     */
    List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * 查找抽奖奖品信息
     * @param awardId
     * @return
     */
    AwardBriefVO queryAwardInfoByAwardId(String awardId);

    /**
     * 减少抽奖奖品库存
     * @param strategyId
     * @param awardId
     * @return
     */
    boolean deductStock(Long strategyId, String awardId);
}
