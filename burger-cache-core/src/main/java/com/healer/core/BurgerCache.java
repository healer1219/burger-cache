package com.healer.core;

import com.healer.core.cache.CoreCache;

import java.util.concurrent.atomic.AtomicLong;

public class BurgerCache {

    private AtomicLong capacity;

    private ExpirationStrategy expirationStrategy;

    private CoreCache cache;

    public BurgerCache(AtomicLong capacity, ExpirationStrategy expirationStrategy) {
        this.capacity = capacity == null ? new AtomicLong(Long.MAX_VALUE) : capacity;
        this.expirationStrategy = expirationStrategy == null ? ExpirationStrategy.LRU : expirationStrategy;
    }

    public CoreCache buildCache() {
        this.cache = new CoreCache(this.capacity.intValue());
        return this.cache;
    }

    public AtomicLong capacity() {
        return capacity;
    }

    public BurgerCache capacity(AtomicLong capacity) {
        this.capacity = capacity;
        return this;
    }

    public ExpirationStrategy expirationStrategy() {
        return expirationStrategy;
    }

    public BurgerCache expirationStrategy(ExpirationStrategy expirationStrategy) {
        this.expirationStrategy = expirationStrategy;
        return this;
    }


    public enum ExpirationStrategy {
        LRU,
        LFU,
        TTL;
    }
}
