package com.healer.core;

import java.util.concurrent.atomic.AtomicLong;

public class BurgerCacheManager {

    private AtomicLong capacity;

    private ExpirationStrategy expirationStrategy;

    private BurgerCache cache;

    public BurgerCacheManager(AtomicLong capacity, ExpirationStrategy expirationStrategy) {
        this.capacity = capacity == null ? new AtomicLong(Long.MAX_VALUE) : capacity;
        this.expirationStrategy = expirationStrategy == null ? ExpirationStrategy.LRU : expirationStrategy;
    }

    public BurgerCache buildCache() {
        this.cache = new BurgerCache(this.capacity.intValue());
        return this.cache;
    }

    public AtomicLong capacity() {
        return capacity;
    }

    public BurgerCacheManager capacity(AtomicLong capacity) {
        this.capacity = capacity;
        return this;
    }

    public ExpirationStrategy expirationStrategy() {
        return expirationStrategy;
    }

    public BurgerCacheManager expirationStrategy(ExpirationStrategy expirationStrategy) {
        this.expirationStrategy = expirationStrategy;
        return this;
    }


    public enum ExpirationStrategy {
        LRU,
        LFU,
        TTL;
    }
}
