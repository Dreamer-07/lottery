package pers.prover07.lottery;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pers.prover07.lottery.domain.strategy.service.draw.IDrawExec;

import javax.annotation.Resource;

/**
 * TODO(这里用一句话描述这个类的作用)
 *
 * @author Prover07
 * @date 2023/8/25 15:18
 */
@SpringBootApplication
@EnableDubbo
public class LotteryApplication {

    @Resource
    private IDrawExec drawExec;

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }
}
