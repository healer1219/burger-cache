package com.healer.core;

import com.healer.core.cache.impl.CoreCache;
import com.healer.core.enums.ExpirationStrategy;

import java.util.concurrent.atomic.AtomicLong;

public class BurgerCache {

    private AtomicLong capacity;

    private CoreCache cache;

    public BurgerCache() {}

    public CoreCache buildCache() {
        this.capacity = capacity == null ? new AtomicLong(Long.MAX_VALUE) : capacity;
        if (this.cache == null) {
            this.cache = new CoreCache(this.capacity.intValue());
        }
        return this.cache;
    }

    public AtomicLong capacity() {
        return capacity;
    }

    public BurgerCache capacity(Integer capacity) {
        this.capacity = new AtomicLong(capacity);
        return this;
    }

    public static class Builder {
        private AtomicLong capacity;

        private ExpirationStrategy expirationStrategy;

        private CoreCache cache;

    }
}
