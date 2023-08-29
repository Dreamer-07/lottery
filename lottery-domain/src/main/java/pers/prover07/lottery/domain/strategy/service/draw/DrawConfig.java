package pers.prover07.lottery.domain.strategy.service.draw;

import pers.prover07.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import pers.prover07.lottery.domain.strategy.service.algorithm.impl.EntiretyRateRandomDrawAlgorithm;
import pers.prover07.lottery.domain.strategy.service.algorithm.impl.SingleRateRandomDrawAlgorithm;
import pers.prover07.lottery.common.Constants;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽奖算法配置
 *
 * @author Prover07
 * @date 2023/8/28 14:15
 */
public class DrawConfig {
    @Resource
    private SingleRateRandomDrawAlgorithm singleRateRandomDrawAlgorithm;

    @Resource
    private EntiretyRateRandomDrawAlgorithm entiretyRateRandomDrawAlgorithm;

    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmMap = new ConcurrentHashMap<>(2);


    @PostConstruct
    public void init() {
        drawAlgorithmMap.put(Constants.DrawStrategyMode.SINGLE.getCode(), singleRateRandomDrawAlgorithm);
        drawAlgorithmMap.put(Constants.DrawStrategyMode.ENTIRETY.getCode(), entiretyRateRandomDrawAlgorithm);
    }


}
