package pers.prover07.lottery.infrastructure.po;

import lombok.Data;

import java.util.Date;

/**
 * 活动基础信息表
 */
@Data
public class Activity {
    // 自增ID
    private Long id;

    // 活动ID
    private Long activityId;

    // 策略ID
    private Long strategyId;

    // 活动名称
    private String activityName;

    // 活动描述
    private String activityDesc;

    // 开始时间
    private Date beginDateTime;

    // 结束时间
    private Date endDateTime;

    // 库存
    private Integer stockCount;

    // 剩余库存
    private Integer stockSurplusCount;

    // 每人可参与次数
    private Integer takeCount;

    // 活动状态：编辑、提审、撤审、通过、运行、拒绝、关闭、开启
    private Integer state;

    // 创建人
    private String creator;

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date updateTime;

}
