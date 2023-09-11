package pers.prover07.lottery.application.process.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动抽奖请求
 *
 * @author Prover07
 * @date 2023/9/9 16:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawProcessReq {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 活动ID
     */
    private Long activityId;

}
