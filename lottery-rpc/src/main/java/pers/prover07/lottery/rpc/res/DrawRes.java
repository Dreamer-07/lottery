package pers.prover07.lottery.rpc.res;

import lombok.Data;
import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.rpc.dto.AwardDTO;

/**
 * 抽奖结果
 *
 * @author Prover07
 * @date 2023/9/9 16:40
 */
@Data
public class DrawRes extends Result {
    private AwardDTO awardDTO;

    public DrawRes(String code, String info) {
        super(code, info);
    }

}
