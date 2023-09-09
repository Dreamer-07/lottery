package pers.prover07.lottery.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 奖品配置
 *
 * @author Prover07
 * @date 2023/9/4 15:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwardVO {

    // 奖品ID
    private String awardId;

    // 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
    private Integer awardType;

    // 奖品名称
    private String awardName;

    // 奖品内容「描述、奖品码、sku」
    private String awardContent;

}
