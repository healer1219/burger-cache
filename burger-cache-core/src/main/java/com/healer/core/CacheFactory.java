package com.healer.core;

import com.healer.core.cache.BaseCache;
import com.healer.core.enums.ExpirationStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheFactory {


    private static Map<ExpirationStrategy, ? extends BaseCache> map = new ConcurrentHashMap<>();

    private CacheFactory() {}


    public static BaseCache getCache(ExpirationStrategy expirationStrategy) {
        return map.get(expirationStrategy);
    }


}
