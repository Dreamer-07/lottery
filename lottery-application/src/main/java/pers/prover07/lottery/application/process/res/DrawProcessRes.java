package pers.prover07.lottery.application.process.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * 活动抽奖结果
 *
 * @author Prover07
 * @date 2023/9/9 16:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawProcessRes extends Result {

    private static final long serialVersionUID = -3787631652628114755L;

    private DrawAwardVO drawAwardVO;

    public DrawProcessRes(String code, String info) {
        super(code, info);
    }

    public DrawProcessRes(String code, String info, DrawAwardVO drawAwardVO) {
        super(code, info);
        this.drawAwardVO = drawAwardVO;
    }
}
