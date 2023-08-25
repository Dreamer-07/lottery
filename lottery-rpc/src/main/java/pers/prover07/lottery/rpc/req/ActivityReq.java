package pers.prover07.lottery.rpc.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 活动请求 dto
 *
 * @author Prover07
 * @date 2023/8/25 15:50
 */
@Data
public class ActivityReq implements Serializable {

    private static final long serialVersionUID = 5765385918366382481L;

    private Long activityId;

}
