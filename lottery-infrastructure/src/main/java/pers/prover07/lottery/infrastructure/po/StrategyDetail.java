package pers.prover07.lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 抽奖明细
 *
 * @author Prover07
 * @date 2023/8/25 17:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyDetail {

    // 自增ID
    private String id;

    // 策略ID
    private Long strategyId;

    // 奖品ID
    private String awardId;

    // 奖品数量
    private Integer awardCount;

    // 中奖概率
    private BigDecimal awardRate;

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date updateTime;

}
