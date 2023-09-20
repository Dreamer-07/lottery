package pers.prover07.lottery.test;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import pers.prover07.lottery.domain.activity.model.req.ActivityConfigReq;
import pers.prover07.lottery.domain.activity.model.req.PartakeReq;
import pers.prover07.lottery.domain.activity.model.res.PartakeRes;
import pers.prover07.lottery.domain.activity.model.vo.ActivityVO;
import pers.prover07.lottery.domain.activity.model.vo.AwardVO;
import pers.prover07.lottery.domain.activity.model.vo.StrategyDetailVO;
import pers.prover07.lottery.domain.activity.model.vo.StrategyVO;
import pers.prover07.lottery.domain.activity.service.deploy.IActivityDeploy;
import pers.prover07.lottery.domain.activity.service.partake.IActivityPartake;
import pers.prover07.lottery.domain.activity.service.stateflow.IStateHandler;
import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.model.res.DistributionRes;
import pers.prover07.lottery.domain.award.service.factory.DistributionGoodsFactory;
import pers.prover07.lottery.domain.award.service.goods.IDistributionGoods;
import pers.prover07.lottery.domain.strategy.model.req.DrawReq;
import pers.prover07.lottery.domain.strategy.model.res.DrawRes;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;
import pers.prover07.lottery.domain.strategy.service.draw.IDrawExec;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;
import pers.prover07.lottery.infrastructure.dao.IUserStrategyExportDao;
import pers.prover07.lottery.infrastructure.po.UserStrategyExport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 功能测试
 *
 * @author Prover07
 * @date 2023/8/29 17:35
 */
@SpringBootTest
public class ApplicationTest {

    private Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private IDrawExec drawExec;

    @Autowired
    private DistributionGoodsFactory distributionGoodsFactory;

    @Resource
    private IActivityDeploy activityDeploy;

    @Resource
    private IStateHandler stateHandler;

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;


    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    ActivityConfigRich activityConfigRich;

    Long activityId;

    @BeforeEach
    public void init() {
        activityId = idGeneratorMap.get(Constants.Ids.RANDOM_NUMERIC).nextId();

        ActivityVO activity = new ActivityVO();
        activity.setActivityId(activityId);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("测试活动描述");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(DateUtil.offsetDay(new Date(), 7));
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(Constants.ActivityState.EDIT.getCode());
        activity.setCreator("xiaofuge");

        StrategyVO strategy = new StrategyVO();
        strategy.setStrategyId(10002L);
        strategy.setStrategyDesc("抽奖策略");
        strategy.setStrategyMode(Constants.DrawStrategyMode.SINGLE.getCode());
        strategy.setGrantType(1);
        strategy.setGrantDate(new Date());
        strategy.setExtInfo("");

        StrategyDetailVO strategyDetail_01 = new StrategyDetailVO();
        strategyDetail_01.setStrategyId(strategy.getStrategyId());
        strategyDetail_01.setAwardId("101");
        strategyDetail_01.setAwardName("一等奖");
        strategyDetail_01.setAwardCount(10);
        strategyDetail_01.setAwardSurplusCount(10);
        strategyDetail_01.setAwardRate(new BigDecimal("0.05"));

        StrategyDetailVO strategyDetail_02 = new StrategyDetailVO();
        strategyDetail_02.setStrategyId(strategy.getStrategyId());
        strategyDetail_02.setAwardId("102");
        strategyDetail_02.setAwardName("二等奖");
        strategyDetail_02.setAwardCount(20);
        strategyDetail_02.setAwardSurplusCount(20);
        strategyDetail_02.setAwardRate(new BigDecimal("0.15"));

        StrategyDetailVO strategyDetail_03 = new StrategyDetailVO();
        strategyDetail_03.setStrategyId(strategy.getStrategyId());
        strategyDetail_03.setAwardId("103");
        strategyDetail_03.setAwardName("三等奖");
        strategyDetail_03.setAwardCount(50);
        strategyDetail_03.setAwardSurplusCount(50);
        strategyDetail_03.setAwardRate(new BigDecimal("0.20"));

        StrategyDetailVO strategyDetail_04 = new StrategyDetailVO();
        strategyDetail_04.setStrategyId(strategy.getStrategyId());
        strategyDetail_04.setAwardId("104");
        strategyDetail_04.setAwardName("四等奖");
        strategyDetail_04.setAwardCount(100);
        strategyDetail_04.setAwardSurplusCount(100);
        strategyDetail_04.setAwardRate(new BigDecimal("0.25"));

        StrategyDetailVO strategyDetail_05 = new StrategyDetailVO();
        strategyDetail_05.setStrategyId(strategy.getStrategyId());
        strategyDetail_05.setAwardId("104");
        strategyDetail_05.setAwardName("五等奖");
        strategyDetail_05.setAwardCount(500);
        strategyDetail_05.setAwardSurplusCount(500);
        strategyDetail_05.setAwardRate(new BigDecimal("0.35"));

        List<StrategyDetailVO> strategyDetailList = new ArrayList<>();
        strategyDetailList.add(strategyDetail_01);
        strategyDetailList.add(strategyDetail_02);
        strategyDetailList.add(strategyDetail_03);
        strategyDetailList.add(strategyDetail_04);
        strategyDetailList.add(strategyDetail_05);

        strategy.setStrategyDetailList(strategyDetailList);

        AwardVO award_01 = new AwardVO();
        award_01.setAwardId("101");
        award_01.setAwardType(Constants.AwardType.DESC_GOODS.getCode());
        award_01.setAwardName("电脑");
        award_01.setAwardContent("请联系活动组织者 测试人员");

        AwardVO award_02 = new AwardVO();
        award_02.setAwardId("102");
        award_02.setAwardType(Constants.AwardType.DESC_GOODS.getCode());
        award_02.setAwardName("手机");
        award_02.setAwardContent("请联系活动组织者 测试人员");

        AwardVO award_03 = new AwardVO();
        award_03.setAwardId("103");
        award_03.setAwardType(Constants.AwardType.DESC_GOODS.getCode());
        award_03.setAwardName("平板");
        award_03.setAwardContent("请联系活动组织者 测试人员");

        AwardVO award_04 = new AwardVO();
        award_04.setAwardId("104");
        award_04.setAwardType(Constants.AwardType.DESC_GOODS.getCode());
        award_04.setAwardName("耳机");
        award_04.setAwardContent("请联系活动组织者 测试人员");

        AwardVO award_05 = new AwardVO();
        award_05.setAwardId("105");
        award_05.setAwardType(Constants.AwardType.DESC_GOODS.getCode());
        award_05.setAwardName("数据线");
        award_05.setAwardContent("请联系活动组织者 测试人员");

        List<AwardVO> awardList = new ArrayList<>();
        awardList.add(award_01);
        awardList.add(award_02);
        awardList.add(award_03);
        awardList.add(award_04);
        awardList.add(award_05);

        activityConfigRich = new ActivityConfigRich(activity, strategy, awardList);
    }

    @Test
    public void test_drawExec() {
        DrawRes drawRes = drawExec.doDrawExec(new DrawReq("测试1号", 10001L));
        System.out.println(drawRes);

        if (Objects.equals(drawRes.getDrawState(), Constants.DrawState.SUCCESS.getCode())) {
            DrawAwardVO drawAwardInfo = drawRes.getDrawAwardInfo();
            IDistributionGoods distributionGoods = distributionGoodsFactory.getDistributionGoods(drawAwardInfo.getAwardType());
            DistributionRes distributionRes = distributionGoods.distribution(new GoodsReq("测试1号", 2109313442431L, drawAwardInfo.getAwardId(), drawAwardInfo.getAwardName(), drawAwardInfo.getAwardContent()));
            System.out.println(distributionRes);
        }
    }

    @Test
    public void test_createActivity() {
        System.out.println(activityConfigRich);
        activityDeploy.createActivity(new ActivityConfigReq(activityId, activityConfigRich));
    }

    @Test
    public void test_alterState() {
        logger.info("提交审核，测试：{}", JSON.toJSONString(stateHandler.arraignment(33924793979L, Constants.ActivityState.EDIT)));
        logger.info("审核通过，测试：{}", JSON.toJSONString(stateHandler.checkPass(33924793979L, Constants.ActivityState.ARRAIGNMENT)));
        logger.info("运行活动，测试：{}", JSON.toJSONString(stateHandler.doing(33924793979L, Constants.ActivityState.PASS)));
        logger.info("二次提审，测试：{}", JSON.toJSONString(stateHandler.checkPass(33924793979L, Constants.ActivityState.EDIT)));
    }

    @Test
    public void test_activityPartake() {
        PartakeReq req = new PartakeReq("Uhdgkw766120d", 33924793979L);
        PartakeRes res = activityPartake.doPartake(req);
        logger.info("请求参数：{}", JSON.toJSONString(req));
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }

    @Test
    public void test_selectUserStrategyExportAll() {
        System.out.println(userStrategyExportDao.selectCount(Wrappers.<UserStrategyExport>lambdaQuery()
                .ne(UserStrategyExport::getOrderId, -1L)));
    }


}
