package pers.prover07.lottery.test;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.prover07.lottery.infrastructure.dao.IActivityDao;
import pers.prover07.lottery.infrastructure.po.Activity;

import java.util.ArrayList;
import java.util.Date;

/**
 * 测试
 *
 * @author Prover07
 * @date 2023/8/25 16:20
 */
@SpringBootTest
@Slf4j
public class ApiTest {

    @Autowired
    private IActivityDao activityDao;

    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100002L);
        activity.setActivityName("测试");
        activity.setActivityDesc("测试数据");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("prover");

        activityDao.insert(activity);
    }

    @Test
    public void test_select() {
        Activity activity = activityDao.queryByActivityId(100002L);

        log.info("测试结果：{}", JSON.toJSONString(activity));
    }


}
