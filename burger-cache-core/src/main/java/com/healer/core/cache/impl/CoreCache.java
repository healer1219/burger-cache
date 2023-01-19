package com.healer.core.cache.impl;

import com.healer.core.cache.BaseCache;
import com.healer.core.enums.ExpirationStrategy;
import com.healer.core.store.StoreMap;
import com.healer.core.store.node.StoreNode;
import com.healer.core.store.storemap.LRUStoreMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CoreCache implements BaseCache {


    private ConcurrentMap<String, StoreMap<String, Object>> cacheMap = new ConcurrentHashMap<>();

    public CoreCache() {
        LRUStoreMap<String, Object> defaultStoreMap = new LRUStoreMap<>();
        cacheMap.put(StoreNames.DEFAULT_STORE.storeName(), defaultStoreMap);
    }

    public CoreCache(int capacity) {
        LRUStoreMap<String, Object> defaultStoreMap = new LRUStoreMap<>(capacity);
        cacheMap.put(StoreNames.DEFAULT_STORE.storeName(), defaultStoreMap);
    }


    /**
     * get a
     * @param key
     * @param clazz
     * @return
     * @param <T>
     */
    @Override
    public <T> T get(String key, Class<? extends T> clazz) {
        return this.get(
                key,
                clazz,
                StoreNames.DEFAULT_STORE.storeName()
        );
    }

    @Override
    public <T> T get(String key, Class<? extends T> clazz, String storeName) {
        checkStore(storeName);
        StoreMap<String, Object> storeMap = cacheMap.get(storeName);
        StoreNode<?, ?> storeNode = storeMap.get(key);
        if (storeNode != null) {
            storeNode.checkValueClazz(clazz);
            return clazz.cast(storeNode.value());
        }
        return null;
    }

    @Override
    public <T> T put(String key, T value) {
        return this.put(
                key,
                value,
                StoreNames.DEFAULT_STORE.storeName(),
                -1
                );
    }

    @Override
    public <T> T put(String key, T value, String storeName) {
        return this.put(
                key,
                value,
                storeName,
                -1
        );
    }

    @Override
    public <T> T put(String key, T value, String storeName, long expirationTime) {
        checkStore(storeName);
        StoreMap<String, Object> storeMap = cacheMap.get(storeName);
        storeMap.put(key, value, expirationTime);
        return value;
    }

    @Override
    public <K, V> StoreMap<K, V> createStoreMap(String storeMapName, Integer storeMapSize, ExpirationStrategy expirationStrategy) {
        return null;
    }

    @Override
    public void newStoreMap(String storeMapName , StoreMap<String, Object> storeMap) {
        if (StoreNames.DEFAULT_STORE.storeName().equals(storeMapName)) {
            throw new RuntimeException();
        }
        cacheMap.put(storeMapName, storeMap);
    }

    @Override
    public int getStoreMapSize(String storeMapName) {
        StoreMap<String, Object> storeMap = cacheMap.get(storeMapName);
        return storeMap.size();
    }


    private void checkStore(String storeName) {
        if (cacheMap == null || cacheMap.isEmpty() || !cacheMap.containsKey(storeName)) {
            throw new RuntimeException();
        }
    }

    enum StoreNames {
        DEFAULT_STORE("default_store");

        private String storeName;

        StoreNames(String storeName) {
            this.storeName = storeName;
        }


        public String storeName() {
            return storeName;
        }

        public StoreNames storeName(String storeName) {
            this.storeName = storeName;
            return this;
        }
    }
}
