package com.healer.core.store.node;

import com.healer.core.store.StoreMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class DoubleLinkedStoreNode<K, V> extends StoreNode<K, V> {

    private DoubleLinkedStoreNode<?, ?> preNode;

    private DoubleLinkedStoreNode<?, ?> postNode;


    public DoubleLinkedStoreNode(DoubleLinkedStoreNode<?, ?> preNode, DoubleLinkedStoreNode<?, ?> postNode, long expirationTime, StoreMap<K, V> storeMap) {
        super(storeMap);
        this.preNode = preNode;
        this.postNode = postNode;
    }

    public DoubleLinkedStoreNode(K key, V value, DoubleLinkedStoreNode<?, ?> preNode, DoubleLinkedStoreNode<?, ?> postNode, long expirationTime, StoreMap<K, V> storeMap) {
        super(key, value, storeMap);
        this.preNode = preNode;
        this.postNode = postNode;
    }

    public DoubleLinkedStoreNode(StoreMap<K, V> storeMap) {
        super(storeMap);
    }

    public DoubleLinkedStoreNode(K key, V value, StoreMap<K, V> storeMap, long expirationTime) {
        super(key, value, storeMap, expirationTime);
    }
}
