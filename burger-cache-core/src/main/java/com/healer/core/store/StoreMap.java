package com.healer.core.store;

import com.healer.core.store.node.StoreNode;
import lombok.NonNull;

import java.util.Map;

public interface StoreMap<K, V> {

    StoreNode<K, V> get(@NonNull K key);

    V put(K key, V value);

    V put(K key, V value, long expirationTime);

    void putAll(Map<? extends K, ? extends V> map);

    int size();

    void expireCache(StoreNode<K , V> expiredNode);
}
