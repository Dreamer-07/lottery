package pers.prover07.lottery.domain.support.ids.policy;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.PostConstruct;

/**
 * 基于 hutool 工具包的雪花id生成器
 *
 * @author Prover07
 * @date 2023/9/5 14:30
 */
@Component
public class SnowFlakeCode implements IIdGenerator {

    private Snowflake snowflake;


    @PostConstruct
    public void init() {
        // 0 ~ 31 位 采用配置的方法使用
        long workerId;
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        }catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
        workerId = (workerId >> 16) & 31;

        long dataCenterId = 1L;
        snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
    }

    @Override
    public long nextId() {
        return snowflake.nextId();
    }
}
