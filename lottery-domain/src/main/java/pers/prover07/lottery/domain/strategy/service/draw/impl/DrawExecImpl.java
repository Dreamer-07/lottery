package pers.prover07.lottery.domain.strategy.service.draw.impl;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import pers.prover07.lottery.domain.strategy.service.draw.AbstractDrawBase;

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


        return drawAlgorithm.randomDraw(strategyId, excludeAwardIds);
    }
}
