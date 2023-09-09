package pers.prover07.lottery.domain.activity.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.prover07.lottery.domain.activity.model.vo.ActivityVO;
import pers.prover07.lottery.domain.activity.model.vo.AwardVO;
import pers.prover07.lottery.domain.activity.model.vo.StrategyVO;

import java.util.List;


/**
 * 活动配置聚合信息
 *
 * @author Prover07
 * @date 2023/9/4 15:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityConfigRich {
    // 活动信息
    private ActivityVO activity;

    // 关联的策略配置
    private StrategyVO strategy;

    // 关联的奖品配置
    private List<AwardVO> awardList;

}
