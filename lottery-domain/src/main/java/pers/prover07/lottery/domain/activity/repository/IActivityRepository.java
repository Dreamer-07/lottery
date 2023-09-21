package pers.prover07.lottery.domain.activity.repository;

import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.vo.*;

import java.util.List;

/**
 * 活动仓储服务
 *
 * @author Prover07
 * @date 2023/9/4 15:46
 */
public interface IActivityRepository {


    /**
     * 添加活动
     *
     * @param activityVO
     */
    void addActivity(ActivityVO activityVO);

    /**
     * 添加策略配置
     *
     * @param strategyVO
     */
    void addStrategy(StrategyVO strategyVO);

    /**
     * 添加策略详情配置
     *
     * @param strategyDetailVOList
     */
    void addStrategyDetailList(List<StrategyDetailVO> strategyDetailVOList);

    /**
     * 添加奖品
     *
     * @param awardVOList
     */
    void addAwardList(List<AwardVO> awardVOList);


    /**
     * 查找活动信息
     *
     * @param req
     * @return
     */
    ActivityBillVO queryActivityBill(PartakeReq req);

    /**
     * 减少活动库存
     *
     * @param activityId
     * @return
     */
    boolean subtractionActivityStock(Long activityId);

    /**
     * 修改活动状态
     *
     * @param activityId
     * @param currentState
     * @param pass
     * @return
     */
    boolean alterStatus(Long activityId, Constants.ActivityState currentState, Constants.ActivityState pass);

    /**
     * 扫描下标大于 startId 且状态为(准备 & 活动中)的活动(只查询 count 个)
     * @param startId
     * @param count
     * @return
     */
    List<ActivityVO> scanToDoActivityList(Long startId, Integer count);
}
