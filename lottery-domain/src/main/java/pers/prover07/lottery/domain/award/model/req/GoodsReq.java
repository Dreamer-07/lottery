package pers.prover07.lottery.domain.award.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.domain.award.model.vo.ShippingAddress;

/**
 * 奖品发货信息
 *
 * @author Prover07
 * @date 2023/9/1 14:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsReq {

    private String uId;

    private Long orderId;

    private String awardId;

    private String awardName;

    private String awardContent;

    private ShippingAddress shippingAddress;

    private String extInfo;

    public GoodsReq(String uId, Long orderId, String awardId, String awardName, String awardContent) {
        this.uId = uId;
        this.orderId = orderId;
        this.awardId = awardId;
        this.awardName = awardName;
        this.awardContent = awardContent;
    }

}
