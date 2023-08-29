package pers.prover07.lottery.domain.strategy.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖请求
 *
 * @author Prover07
 * @date 2023/8/28 16:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawReq {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 防重ID
     */
    private String uuid;

    public DrawReq(String uId, Long strategyId) {
        this.uId = uId;
        this.strategyId = strategyId;
    }
}
