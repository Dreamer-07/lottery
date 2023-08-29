package pers.prover07.lottery.domain.strategy.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.domain.strategy.model.vo.StrategyBriefVO;
import pers.prover07.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;

import java.util.List;

/**
 * 策略信息
 *
 * @author Prover07
 * @date 2023/8/28 16:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyRich {

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 策略配置
     */
    private StrategyBriefVO strategy;

    /**
     * 策略明细
     */
    private List<StrategyDetailBriefVO> strategyDetailList;

}
