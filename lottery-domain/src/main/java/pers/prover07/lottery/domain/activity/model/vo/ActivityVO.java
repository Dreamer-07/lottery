package pers.prover07.lottery.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 活动信息
 *
 * @author Prover07
 * @date 2023/9/4 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVO {

    // 自增id
    private Long id;

    // 活动id
    private Long activityId;

    // 活动名
    private String activityName;

    // 活动描述
    private String activityDesc;

    // 开始时间
    private Date beginDateTime;

    // 结束时间
    private Date endDateTime;

    // 活动库存
    private Integer stockCount;

    // 每个人可以参与的次数
    private Integer takeCount;

    // 活动状态(编辑、提审、撤审、通过、运行、拒绝、关闭、开启)
    private Integer state;

    // 创建人
    private String creator;

}
