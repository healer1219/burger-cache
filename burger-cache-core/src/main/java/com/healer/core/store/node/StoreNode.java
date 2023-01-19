package com.healer.core.store.node;

import com.healer.core.store.StoreMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public abstract class StoreNode<K, V> {
    private K key;

    private V value;

    private Class<?> keyClazz;

    private Class<?> valueClazz;

    /**
     * when the node Data expires, if this is -1, then forever
     */
    private volatile long expirationTime;

    /**
     *  the storeMap which use this node
     */
    private StoreMap<K, V> storeMap;

    private static long DEFAULT_EXPIRATION_TIME = -1;



    public void checkValueClazz(Class<?> clazz) {
        if (!valueClazz.equals(clazz)) {
            throw new RuntimeException();
        }
    }

    public StoreNode(K key, V value, StoreMap<K, V> storeMap, long expirationTime) {
        this.key = key;
        this.value = value;
        this.keyClazz = this.key.getClass();
        this.valueClazz = this.value.getClass();
        this.storeMap = storeMap;
        this.expirationTime = expirationTime;
        expirationStart();
    }

    public StoreNode(K key, V value, StoreMap<K, V> storeMap) {
        this.key = key;
        this.value = value;
        this.keyClazz = this.key.getClass();
        this.valueClazz = this.value.getClass();
        this.storeMap = storeMap;
        this.expirationTime = DEFAULT_EXPIRATION_TIME;
        expirationStart();
    }

    public StoreNode(StoreMap<K, V> storeMap) {
        this.storeMap = storeMap;
        this.expirationTime = DEFAULT_EXPIRATION_TIME;
        expirationStart();
    }


    public void expirationStart() {
        if (expirationTime > 0) {
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.schedule(
                    () -> storeMap.expireCache(this),
                    expirationTime,
                    TimeUnit.MILLISECONDS
            );
        }

    }


}
