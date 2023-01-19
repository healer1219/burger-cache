package com.healer.core.cache;

import com.healer.core.enums.ExpirationStrategy;
import com.healer.core.store.StoreMap;

public interface BaseCache {

    <T> T get(String key, Class<? extends T> clazz);


    <T> T get(String key, Class<? extends T> clazz, String storeName);

    <T> T put(String key, T value);

    <T> T put(String key, T value, String storeName);

    <T> T put(String key, T value, String storeName, long expirationTime);

    <K, V> StoreMap<K, V> createStoreMap(String storeMapName, Integer storeMapSize, ExpirationStrategy expirationStrategy);

    void newStoreMap(String storeMapName , StoreMap<String, Object> storeMap);

    int getStoreMapSize(String storeMapName);

}
