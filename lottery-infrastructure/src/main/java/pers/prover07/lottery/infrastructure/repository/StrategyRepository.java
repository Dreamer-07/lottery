package pers.prover07.lottery.infrastructure.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.strategy.model.aggregates.StrategyRich;
import pers.prover07.lottery.domain.strategy.model.vo.AwardBriefVO;
import pers.prover07.lottery.domain.strategy.model.vo.StrategyBriefVO;
import pers.prover07.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import pers.prover07.lottery.domain.strategy.repository.IStrategyRepository;
import pers.prover07.lottery.infrastructure.dao.IAwardDao;
import pers.prover07.lottery.infrastructure.dao.IStrategyDao;
import pers.prover07.lottery.infrastructure.dao.IStrategyDetailDao;
import pers.prover07.lottery.infrastructure.po.Award;
import pers.prover07.lottery.infrastructure.po.Strategy;
import pers.prover07.lottery.infrastructure.po.StrategyDetail;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 策略表仓储服务实现
 *
 * @author Prover07
 * @date 2023/8/29 15:06
 */
@Component
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        // 获取策略
        Strategy strategy = strategyDao.selectOne(
                Wrappers.<Strategy>lambdaQuery()
                        .eq(Strategy::getStrategyId, strategyId)
        );
        // 获取策略明细
        List<StrategyDetail> strategyDetails = strategyDetailDao.selectList(
                Wrappers.<StrategyDetail>lambdaQuery()
                        .eq(StrategyDetail::getStrategyId, strategyId)
        );

        // 包装数据
        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        BeanUtils.copyProperties(strategy, strategyBriefVO);
        List<StrategyDetailBriefVO> strategyDetailBriefVOS = strategyDetails.stream().map((StrategyDetail strategyDetail) -> {
            StrategyDetailBriefVO strategyDetailBriefVO = new StrategyDetailBriefVO();
            BeanUtils.copyProperties(strategyDetail, strategyDetailBriefVO);
            return strategyDetailBriefVO;
        }).collect(Collectors.toList());

        return new StrategyRich(strategyId, strategyBriefVO, strategyDetailBriefVOS);
    }

    @Override
    public List<String> queryExcludeAwardIds(Long strategyId) {
        List<StrategyDetail> strategyDetails = strategyDetailDao.selectList(
                Wrappers.<StrategyDetail>lambdaQuery()
                        .select(StrategyDetail::getAwardId)
                        .eq(StrategyDetail::getStrategyId, strategyId)
                        .eq(StrategyDetail::getAwardCount, 0)
        );

        return strategyDetails.stream().map(StrategyDetail::getAwardId).collect(Collectors.toList());
    }

    @Override
    public AwardBriefVO queryAwardInfoByAwardId(String awardId) {
        Award award = awardDao.selectOne(
                Wrappers.<Award>lambdaQuery()
                        .eq(Award::getAwardId, awardId)
        );

        AwardBriefVO awardBriefVO = new AwardBriefVO();
        BeanUtils.copyProperties(award, awardBriefVO);

        return awardBriefVO;
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        // 获取相关的策略配置
        StrategyDetail strategyDetail = strategyDetailDao.selectOne(
                Wrappers.<StrategyDetail>lambdaQuery()
                        .select(StrategyDetail::getAwardCount)
                        .eq(StrategyDetail::getStrategyId, strategyId)
                        .eq(StrategyDetail::getAwardId, awardId)
        );

        return 1 == strategyDetailDao.update(
                strategyDetail,
                Wrappers.<StrategyDetail>lambdaQuery()
                        .eq(StrategyDetail::getStrategyId, strategyId)
                        .eq(StrategyDetail::getAwardId, awardId)
                        .gt(StrategyDetail::getAwardCount, 0)
        );
    }
}
