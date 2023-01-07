package com.healer.core.store.storemap;

import com.healer.core.store.BaseStoreMap;
import com.healer.core.store.node.StoreNode;
import com.healer.core.utils.UnsafeUtil;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class LRUStoreMap<K, V> implements BaseStoreMap<K, V> {

    private Map<K, StoreNode<K, V>> storeMap = new ConcurrentHashMap<>();

    private AtomicLong count;

    private AtomicLong capacity;

    private StoreNode<K, V> head, tail;

    public LRUStoreMap(int capacity) {
        this.capacity = new AtomicLong(capacity);
        this.count = new AtomicLong(0L);

        this.head = new StoreNode<>();
        this.tail = new StoreNode<>();

        head.postNode(tail);
        tail.preNode(head);
    }

    /**
     * return the value which the specified key is mapped
     */
    @Override
    public V get(@NonNull K key) {
        StoreNode<K, V> storeNode = storeMap.get(key);
        Objects.requireNonNull(storeNode);
        moveToHead(storeNode);
        return storeNode.value();
    }

    /**
     * Put the value to the storeMap
     * Neither the key nor the value can be null.
     * @throws NullPointerException if the key or the value is null
     * @throws RuntimeException when popTailNode or addNode failed
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        checkCapacity();
        StoreNode<K, V> node = new StoreNode<>(
                key,
                value
        );
        storeMap.put(key, node);
        addNode(node);
        return node.value();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            throw new NullPointerException();
        }
        if (map.isEmpty()) {
            // do nothing
            return;
        }
        map.forEach(this::put);
    }

    /**
     * check the capacity
     */
    private void checkCapacity() {
        if (count.incrementAndGet() >= capacity.get()) {
            StoreNode<K, V> poppedNode = popTailNode();
            if (poppedNode.key() != null) {
                storeMap.remove(
                        poppedNode.key()
                );
            }
            poppedNode = null;
        }
    }




    /**
     * add a node into the Linked Node List
     * @param storeNode a node use to store
     */
    private void addNode(StoreNode<K, V> storeNode) {
        storeNode.preNode(head);
        storeNode.postNode(
                head.postNode()
        );

        head.postNode().preNode(
                storeNode
        );
        head.postNode(
                storeNode
        );
    }

    /**
     * remove a node from Linked Node List
     * @param storeNode the node want to be removed
     */
    private void removeNode(StoreNode<K,V> storeNode) {
        storeNode.postNode().preNode(
                storeNode.preNode()
        );
        storeNode.preNode().postNode(
                storeNode.postNode()
        );
    }

    /**
     * move a node to head
     * @param storeNode the node want to be moved to the head
     */
    private void moveToHead(StoreNode<K, V> storeNode) {
        removeNode(storeNode);
        addNode(storeNode);
    }

    private StoreNode<K, V> popTailNode() {
        removeNode(
                tail.preNode()
        );
        return tail.preNode();
    }
}
