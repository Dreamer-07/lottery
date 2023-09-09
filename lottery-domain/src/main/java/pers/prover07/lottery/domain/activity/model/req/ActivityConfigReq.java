package pers.prover07.lottery.domain.activity.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.domain.activity.model.aggregates.ActivityConfigRich;

/**
 * 活动配置请求
 *
 * @author Prover07
 * @date 2023/9/4 15:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityConfigReq {

    // 活动id
    private Long activityId;

    // 活动聚合配置信息
    private ActivityConfigRich activityConfigRich;

}
