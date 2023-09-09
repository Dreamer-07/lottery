package pers.prover07.lottery.domain.support.cache;

import org.redisson.api.RLock;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;
import java.util.function.Supplier;

/**
 * redis 仓库接口定义
 */
public interface IRedisRepository {

    /**
     * 分布式锁
     *
     * @param key
     * @return
     */
    RLock lock(String key);

    /**
     * 带超时的锁
     *
     * @param key
     * @param second 超时时间(秒)
     * @return
     */
    RLock lock(String key, Long second);

    /**
     * 基础的分布式锁业务模板
     *
     * @param key
     * @param supplier 业务逻辑方法
     * @param <T>      返回的参数类型
     * @return
     */
    <T> T lock(String key, Supplier<T> supplier);

    /**
     * 释放锁
     *
     * @param lockKey
     */
    void unlock(String lockKey);


    /**
     * 释放锁
     *
     * @param lock
     */
    void unlock(RLock lock);

    /**
     * 将值放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    void set(String key, Object value);

    /**
     * 将值放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) -1为无期限
     * @return
     */
    void set(String key, Object value, long time);

    /**
     * 根据key获取值
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 判断 key 对应的缓存是否存在
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 获取 hash 中的 item
     *
     * @param key
     * @param item
     * @return
     */
    Object hGet(String key, String item);

    /**
     * 存储到 hash 中
     *
     * @param key
     * @param item
     * @param val
     * @return
     */
    void hSet(String key, String item, Object val);

    /**
     * 自增 hash 中指定的 item 对应的 val 值
     *
     * @param key
     * @param item
     * @param delta
     */
    double hIncr(String key, String item, double delta);

    /**
     * 执行 lua 脚本
     *
     * @param script
     * @param keys
     * @param args
     * @return
     */
    <T> T execute(DefaultRedisScript<T> script, List<String> keys, Object... args);

    /**
     * 自增
     * @param key
     * @param delta
     * @return
     */
    long incr(String key, int delta);
}
