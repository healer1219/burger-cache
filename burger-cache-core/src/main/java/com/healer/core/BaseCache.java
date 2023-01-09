package com.healer.core;

import com.healer.core.store.StoreMap;

interface BaseCache {

    <T> T get(String key, Class<? extends T> clazz);


    <T> T get(String key, Class<? extends T> clazz, String storeName);


    <T> T put(String key, T value);

    <T> T put(String key, T value, String storeName);

    void createStoreMap(String storeMapName , StoreMap<String, Object> storeMap);

    int getStoreMapSize(String storeMapName);

}
