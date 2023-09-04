package pers.prover07.lottery.domain.award.repository;

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
