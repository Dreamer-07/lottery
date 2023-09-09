package pers.prover07.lottery.domain.activity.model.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * 活动参与结果
 *
 * @author Prover07
 * @date 2023/9/4 17:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PartakeRes extends Result {
    private static final long serialVersionUID = -341174288442055238L;
    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 活动领取ID
     */
    private Long takeId;

    public PartakeRes(String code, String info) {
        super(code, info);
    }
}
