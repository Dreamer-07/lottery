package pers.prover07.lottery.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户参与抽奖策略计算结果的关联表
 *
 * @author Prover07
 * @date 2023/9/1 15:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStrategyExport {

    // 自增ID
    @TableId(type = IdType.AUTO)
    private Long id;


    // 用户ID
    private String uId;


    // 活动ID
    private Long activityId;


    // 订单ID
    private Long orderId;


    // 策略ID
    private Long strategyId;


    // 策略方式
    private Integer strategyMode;


    // 发放奖品
    private Integer grantType;


    // 发奖时间
    private Date grantDate;


    // 发奖状态
    private Integer grantState;


    // 发奖ID
    private String awardId;


    // 奖品类型
    private Integer awardType;


    // 奖品名称
    private String awardName;


    // 奖品内容
    private String awardContent;


    // 防重ID
    private String uuid;


    // 消息发送
    private Integer mqState;


    // 创建时间
    private Date createTime;


    // 更新时间
    private Date updateTime;

}
