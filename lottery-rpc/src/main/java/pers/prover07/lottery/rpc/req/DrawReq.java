package pers.prover07.lottery.rpc.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖请求
 *
 * @author Prover07
 * @date 2023/9/9 16:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawReq {

    private String uId;

    private Long activityId;

}
