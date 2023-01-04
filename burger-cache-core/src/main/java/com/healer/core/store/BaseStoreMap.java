package com.healer.core.store;

import lombok.NonNull;

import java.util.Map;

public interface BaseStoreMap<K, V> {
    V get(@NonNull K key);

    V put(K key, V value);

    void putAll(Map<? extends K, ? extends V> map);
}
