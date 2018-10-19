package com.itcwt.ss.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.*;

/**
 * @author BF
 * @date 2018/9/14
 * Guava Cache abstract
 * 定义统一方法
 * 这里使用抽象方法要比接口更方便
 * 利用guava实现的内存缓存。缓存加载之后永不过期，后台线程定时刷新缓存值。刷新失败时将继续返回旧缓存。
 * 在调用getValue之前，需要设置 refreshDuration， refreshTimeunit， maxSize 三个参数
 * 后台刷新线程池为该系统中所有子类共享，大小为20.
 * 参考CSDN博客   https://blog.csdn.net/u012859681/article/details/75220605
 */
public abstract class GuavaCacheService<K, V> {
    /**
     * 缓存过期时间（可选择）  -1 为缓存时间不过期
     */
    private int expireDuration = -1;
    /**
     * 缓存过期时间格式
     */
    private TimeUnit expireTimeUnit = TimeUnit.HOURS;
    /**
     * 缓存最大容量
     * 这个数值建议设置大一点，因为当缓存超过容量后便会对cache进行删除重新更新操作，而且并不是当容量到达设置的值才开始，
     * 而是容量接近设置的数值后变开始了
     */
    private int maxSize = 10;
    /**
     * 缓存数据块
     */
    private LoadingCache<K, V> cache = null;

    private LoadingCache<K, V> getCache(){
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                            .maximumSize(maxSize);
                    // 默认永不过期
                    if (expireDuration > 0) {
                        cacheBuilder = cacheBuilder.expireAfterWrite(expireDuration, expireTimeUnit);
                    }
                    cache = cacheBuilder.build(new CacheLoader<K, V>() {
                        @Override
                        public V load(K k){
                            return get(k);
                        }
                    });
                }
            }
        }
        return cache;
    }

    /**
     * 不存在则返回null
     *
     * @param key
     * @return
     */
    public V get(K key) {
        try {
            return getCache().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从cache中拿出数据操作
     *
     * @param key 泛型 若不存在改值则返回默认值
     */
    public V get(K key, V defaultValue) {
        try {
            return getCache().get(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 往缓存中插入数据
     *
     * @param key   泛型
     * @param value 泛型
     *              注意，若缓存中存在对应的key，则会覆盖
     */
    public void put(K key, V value) {
        this.getCache().put(key, value);
    }

    /**
     * 设置缓存过期时间
     *
     * @param expireDuration [int] 缓存过期时间
     * @param expireTimeUnit 时间单位
     * @return 返回重新设置的对象
     */
    public GuavaCacheService setExpire(int expireDuration, TimeUnit expireTimeUnit) {
        this.expireDuration = expireDuration;
        this.expireTimeUnit = expireTimeUnit;
        return this;
    }


    /**
     * 设置缓存最大容量
     *
     * @param maxSize [int] 缓存最大容量
     * @return 返回重新设置的对象
     */
    public GuavaCacheService setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 清除所有缓存数据，若执行了缓存清除后，下一次请求还是会加载缓存
     */
    public void clearAll() {
        getCache().invalidateAll();
    }

    /**
     * 清除指定缓存
     */
    public void clearCacheByKey(K key) {
        getCache().invalidate(key);
    }

    @Override
    public String toString() {
        return "GuavaCache";
    }
}
