package pers.prover07.lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 奖品概率信息
 *
 * @author Prover07
 * @date 2023/8/28 14:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwardRateInfo {

    // 奖品id
    private String awardId;

    // 概率值
    private BigDecimal awardRate;

}
