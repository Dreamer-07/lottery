package pers.prover07.lottery.domain.award.repository;

import pers.prover07.lottery.common.Result;
import pers.prover07.lottery.domain.award.model.vo.DrawOrderVo;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 *
 *
 * @author Prover07
 * @date 2023/9/1 15:05
 */
public interface IAwardRepository {

    /**
     * 更改用户的发货奖品状态
     * @param uId
     * @param orderId
     * @param awardId
     * @param grantState
     */
    void updateUserStrategyGrantState(String uId, Long orderId, String awardId, Integer grantState);

}
