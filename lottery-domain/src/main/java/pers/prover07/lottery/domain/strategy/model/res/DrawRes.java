package pers.prover07.lottery.domain.strategy.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;
import pers.prover07.lottery.common.Constants;

/**
 * 抽奖结果
 *
 * @author Prover07
 * @date 2023/8/28 16:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawRes {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖 Constants.DrawState
     */
    private Integer drawState = Constants.DrawState.FAIL.getCode();

    /**
     * 中奖奖品信息
     */
    private DrawAwardVO drawAwardInfo;

}
