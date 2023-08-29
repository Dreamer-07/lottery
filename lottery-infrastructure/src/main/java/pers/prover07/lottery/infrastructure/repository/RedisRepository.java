package pers.prover07.lottery.infrastructure.repository;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.prover07.lottery.domain.support.cache.IRedisRepository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * redis 存储服务
 *
 * @author Prover07
 * @date 2023/8/29 16:46
 */
@Component
public class RedisRepository implements IRedisRepository {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RLock lock(String key) {
        return redissonClient.getLock(key);
    }

    @Override
    public RLock lock(String key, Long second) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(second, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public <T> T lock(String key, Supplier<T> supplier) {
        RLock lock = lock(key);
        try {
            lock.lock();
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    @Override
    public Object get(String key) {
        return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(StringUtils.isBlank(key) ? null : redisTemplate.hasKey(key));
    }
}
