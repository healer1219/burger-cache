package com.healer.core.store.node;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public abstract class StoreNode<K, V> {
    private K key;

    private V value;

    private Class<?> keyClazz;

    private Class<?> valueClazz;


    public void checkValueClazz(Class<?> clazz) {
        if (!valueClazz.equals(clazz)) {
            throw new RuntimeException();
        }
    }

    public StoreNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.keyClazz = this.key.getClass();
        this.valueClazz = this.value.getClass();
    }

    public StoreNode() {}


}
