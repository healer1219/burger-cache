package com.healer.core.store.node;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class StoreNode<K, V> {

    private K key;

    private V value;

    private StoreNode<K, V> preNode;

    private StoreNode<K, V> postNode;

    public StoreNode(StoreNode<K, V> preNode, StoreNode<K, V> postNode) {
        this.preNode = preNode;
        this.postNode = postNode;
    }

    public StoreNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public StoreNode() {}


}
