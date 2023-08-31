package pers.prover07.lottery.domain.strategy.service.draw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import pers.prover07.lottery.domain.strategy.model.aggregates.StrategyRich;
import pers.prover07.lottery.domain.strategy.model.req.DrawReq;
import pers.prover07.lottery.domain.strategy.model.res.DrawRes;
import pers.prover07.lottery.domain.strategy.model.vo.*;
import pers.prover07.lottery.domain.strategy.repository.IStrategyRepository;
import pers.prover07.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import pers.prover07.lottery.domain.support.cache.IRedisRepository;
import pers.prover07.lottery.common.Constants;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (模板方法)定义抽奖的基本流程
 *
 * @author Prover07
 * @date 2023/8/28 17:18
 */
public abstract class AbstractDrawBase extends DrawConfig implements IDrawExec {

    private Logger log = LoggerFactory.getLogger(AbstractDrawBase.class);


    @Resource
    protected IStrategyRepository strategyRepository;

    @Resource
    protected IRedisRepository redisRepository;

    @Override
    public DrawRes doDrawExec(DrawReq drawReq) {
        Long strategyId = drawReq.getStrategyId();

        // 获取抽奖策略
        StrategyRich strategyRich = strategyRepository.queryStrategyRich(strategyId);
        StrategyBriefVO strategy = strategyRich.getStrategy();

        // 初始化抽奖策略对应的奖品概率信息
        this.initRateData(strategy.getStrategyId(), strategy.getStrategyMode(), strategyRich.getStrategyDetailList());

        // 查找不在抽奖范围内的奖品信息
        List<String> excludeAwardIds = strategyRepository.queryExcludeAwardIds(strategyId);

        // 抽奖
        String awardId = this.drawAlgorithm(strategyId, drawAlgorithmMap.get(strategy.getStrategyMode()), excludeAwardIds);

        return buildDrawRes(drawReq.getUId(), awardId, strategy);
    }

    /**
     * 具体的抽奖算法过程
     *
     * @param strategyId
     * @param drawAlgorithm
     * @param excludeAwardIds
     * @return
     */
    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds);

    /**
     * 包装抽奖结果
     *
     * @param uId      用户id
     * @param awardId  奖品id
     * @param strategy 策略信息
     * @return
     */
    private DrawRes buildDrawRes(String uId, String awardId, StrategyBriefVO strategy) {
        Long strategyId = strategy.getStrategyId();
        if (awardId == null) {
            log.info("抽奖活动完成: 状态 - 未中奖, userId - {}, strategyId - {}", uId, strategyId);
            return new DrawRes(uId, strategyId, Constants.DrawState.FAIL.getCode(), null);
        }

        // 查找奖品信息
        AwardBriefVO awardBriefVO = strategyRepository.queryAwardInfoByAwardId(awardId);
        // 构建奖品响应信息
        DrawAwardVO drawAwardVO = new DrawAwardVO();
        drawAwardVO.setUId(uId);
        BeanUtils.copyProperties(awardBriefVO, drawAwardVO);
        BeanUtils.copyProperties(strategy, drawAwardVO);

        return new DrawRes(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardVO);
    }


    /**
     * 初始化活动的奖品概率信息
     *
     * @param strategyId         策略id
     * @param strategyMode       策略类型
     * @param strategyDetailList 策略配置详情
     */
    protected void initRateData(Long strategyId, Integer strategyMode, List<StrategyDetailBriefVO> strategyDetailList) {
        // 获取抽奖的算法类型
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);

        // 判断该抽奖算法是否已经为指定的策略缓存了相关的数据
        if (drawAlgorithm.isExists(strategyId)) {
            return;
        }

        // 初始化奖品的概率信息
        List<AwardRateInfo> rateInfos = strategyDetailList.stream()
                .map(strategyDetailBriefVO -> new AwardRateInfo(strategyDetailBriefVO.getAwardId(), strategyDetailBriefVO.getAwardRate()))
                .collect(Collectors.toList());
        // 抽奖算法需要提前初始化奖品信息的概率信息
        drawAlgorithm.initRateTuple(strategyId, strategyMode, rateInfos);
    }
}
