package pers.prover07.lottery.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.prover07.lottery.domain.support.cache.IRedisRepository;

/**
 * 测试 redis
 *
 * @author Prover07
 * @date 2023/9/9 13:53
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private IRedisRepository redisRepository;

    @Test
    public void test_incr() {
        redisRepository.set("test:key", 100);
    }
}
