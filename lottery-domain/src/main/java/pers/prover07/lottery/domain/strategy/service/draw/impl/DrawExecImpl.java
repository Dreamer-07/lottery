package pers.prover07.lottery.domain.strategy.service.draw.impl;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import pers.prover07.lottery.domain.strategy.service.draw.AbstractDrawBase;
import pers.prover07.lottery.common.Constants;

import java.util.List;

/**
 * 具体抽奖流程实现
 *
 * @author Prover07
 * @date 2023/8/29 14:59
 */
@Component("drawExec")
public class DrawExecImpl extends AbstractDrawBase {

    @Override
    protected String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds) {
        String awardId = drawAlgorithm.randomDraw(strategyId, excludeAwardIds);

        if (awardId == null) {
            return null;
        }

        Boolean isSuccess = redisRepository.lock(Constants.Cache.STRATEGY_DRAW_STOCK_LOCK_KEY, () -> strategyRepository.deductStock(strategyId, awardId));

        return Boolean.TRUE.equals(isSuccess) ? awardId : null;
    }
}
