package pers.prover07.lottery.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.prover07.lottery.domain.strategy.model.req.DrawReq;
import pers.prover07.lottery.domain.strategy.service.draw.IDrawExec;

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

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("测试1号", 10001L));
        drawExec.doDrawExec(new DrawReq("测试2号", 10001L));
    }


}
