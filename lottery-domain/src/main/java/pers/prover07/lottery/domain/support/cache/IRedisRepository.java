package pers.prover07.lottery.domain.support.cache;

import org.redisson.api.RLock;

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
    void set(String key, String value);

    /**
     * 将值放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) -1为无期限
     * @return
     */
    void set(String key, String value, long time);

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

}
