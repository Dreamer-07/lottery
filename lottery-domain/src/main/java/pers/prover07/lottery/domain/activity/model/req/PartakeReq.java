package pers.prover07.lottery.domain.activity.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 参与活动请求
 *
 * @author Prover07
 * @date 2023/9/4 17:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartakeReq {

    // 用户标识
    private String uId;

    // 活动id
    private Long activityId;

    // 领取活动的时间
    private Date partakeDate;

    public PartakeReq(String uId, Long activityId) {
        this.uId = uId;
        this.activityId = activityId;
        this.partakeDate = new Date();
    }
}
