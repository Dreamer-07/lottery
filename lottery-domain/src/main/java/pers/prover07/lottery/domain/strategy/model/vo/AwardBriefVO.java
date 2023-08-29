package pers.prover07.lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 奖品简要信息
 *
 * @author Prover07
 * @date 2023/8/29 13:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwardBriefVO {

    // 奖品id
    private String awardId;

    // 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
    private Integer awardType;

    // 奖品名称
    private String awardName;

    //  奖品内容「描述、奖品码、sku」
    private String awardContent;

}
