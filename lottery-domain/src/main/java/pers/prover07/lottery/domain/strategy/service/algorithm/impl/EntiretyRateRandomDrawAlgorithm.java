package pers.prover07.lottery.domain.strategy.service.algorithm.impl;

import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.strategy.model.vo.AwardRateInfo;
import pers.prover07.lottery.domain.strategy.service.algorithm.BaseAlgorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 总体概率抽奖算法
 *
 * @author Prover07
 * @date 2023/8/28 15:52
 */
@Component
public class EntiretyRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        // 用于计算剩余奖品的总概率
        BigDecimal differenceDenominator = BigDecimal.ZERO;

        // 过滤掉 excludeAwardIds 奖品
        List<AwardRateInfo> differenceAwardRateList = new ArrayList<>();
        List<AwardRateInfo> awardRateInfos = this.awardRateInfoMap.get(strategyId);
        for (AwardRateInfo awardRateInfo : awardRateInfos) {
            if (!excludeAwardIds.contains(awardRateInfo.getAwardId())) {
                differenceAwardRateList.add(awardRateInfo);
                differenceDenominator = differenceDenominator.add(awardRateInfo.getAwardRate());
            }
        }

        if (differenceAwardRateList.isEmpty()) {
            return null;
        }

        if (differenceAwardRateList.size() == 1) {
            return differenceAwardRateList.get(0).getAwardId();
        }

        // 获取随机数
        int randomVal = this.generaSecureRandomIntCode(100);

        // 通过概率叠加的方式找到奖品
        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : differenceAwardRateList) {
            int rateVal = awardRateInfo.getAwardRate().divide(differenceDenominator, 2, RoundingMode.UP).multiply(new BigDecimal(100)).intValue();
            if (randomVal <= rateVal + cursorVal) {
                return awardRateInfo.getAwardId();
            }
            cursorVal += rateVal;
        }

        return null;
    }
}
