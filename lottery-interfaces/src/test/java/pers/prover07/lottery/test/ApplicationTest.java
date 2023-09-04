package pers.prover07.lottery.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.prover07.lottery.common.Constants;
import pers.prover07.lottery.domain.award.model.req.GoodsReq;
import pers.prover07.lottery.domain.award.model.res.DistributionRes;
import pers.prover07.lottery.domain.award.service.factory.DistributionGoodsFactory;
import pers.prover07.lottery.domain.award.service.goods.IDistributionGoods;
import pers.prover07.lottery.domain.strategy.model.req.DrawReq;
import pers.prover07.lottery.domain.strategy.model.res.DrawRes;
import pers.prover07.lottery.domain.strategy.model.vo.DrawAwardVO;
import pers.prover07.lottery.domain.strategy.service.draw.IDrawExec;

import java.util.Objects;

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


}
