package pers.prover07.lottery.domain.award.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 中奖商品订单信息
 *
 * @author Prover07
 * @date 2023/9/18 15:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawOrderVo {

    /**
     * 用户ID
     */
    private String uId;

    /**
     * 活动领取ID
     */
    private Long takeId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 中奖状态(0未中奖、1已中奖、2兜底奖)
     */
    private Integer drawState;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 策略ID
     */
    private Long strategyId;
    /**
     * 策略方式（1:单项概率、2:总体概率）
     */
    private Integer strategyMode;
    /**
     * 发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）
     */
    private Integer grantType;
    /**
     * 发奖时间
     */
    private Date grantDate;
    /**
     * 发奖状态
     */
    private Integer grantState;
    /**
     * 发奖ID
     */
    private String awardId;
    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer awardType;
    /**
     * 奖品名称
     */
    private String awardName;
    /**
     * 奖品内容「文字描述、Key、码」
     */
    private String awardContent;

}
