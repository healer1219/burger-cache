package com.healer.core.store.node;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class DoubleLinkedStoreNode<K, V> extends StoreNode<K, V> {

    private DoubleLinkedStoreNode<?, ?> preNode;

    private DoubleLinkedStoreNode<?, ?> postNode;


    public DoubleLinkedStoreNode(DoubleLinkedStoreNode<?, ?> preNode, DoubleLinkedStoreNode<?, ?> postNode) {
        super();
        this.preNode = preNode;
        this.postNode = postNode;
    }

    public DoubleLinkedStoreNode(K key, V value, DoubleLinkedStoreNode<?, ?> preNode, DoubleLinkedStoreNode<?, ?> postNode) {
        super(key, value);
        this.preNode = preNode;
        this.postNode = postNode;
    }

    public DoubleLinkedStoreNode() {
    }

    public DoubleLinkedStoreNode(K key, V value) {
        super(key, value);
    }
}
