package com.healer.core.store.storemap;

import com.healer.core.enums.ExpirationStrategy;
import com.healer.core.store.StoreMap;
import com.healer.core.store.node.DoubleLinkedStoreNode;
import com.healer.core.store.node.StoreNode;
import lombok.*;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class LRUStoreMap<K, V> implements StoreMap<K, V> {

    private Map<K, DoubleLinkedStoreNode<K, V>> storeMap;

    private AtomicLong count;

    private AtomicLong capacity;

    private ExpirationStrategy expirationStrategy = ExpirationStrategy.LRU;

    private DoubleLinkedStoreNode<?, ?> head, tail;

    public LRUStoreMap(int capacity) {
        Objects.requireNonNull(capacity);
        this.capacity = new AtomicLong(capacity);
        this.storeMap = new ConcurrentHashMap<>((int) (this.capacity.get() / 0.75));
        this.count = new AtomicLong(0L);
        this.head = new DoubleLinkedStoreNode<>(this);
        this.tail = new DoubleLinkedStoreNode<>(this);

        head.postNode(tail);
        tail.preNode(head);
    }

    /**
     * return the value which the specified key is mapped
     */
    @Override
    public DoubleLinkedStoreNode<K, V> get(@NonNull K key) {
        DoubleLinkedStoreNode<K, V> storeNode = storeMap.get(key);
        Objects.requireNonNull(storeNode);
        moveToHead(storeNode);
        return storeNode;
    }


    @Override
    public V put(K key, V value) {
        return this.put(key, value, -1);
    }

    /**
     * Put the value to the storeMap
     * Neither the key nor the value can be null.
     * @throws NullPointerException if the key or the value is null
     * @throws RuntimeException when popTailNode or addNode failed
     */
    @Override
    public V put(K key, V value, long expirationTime) {
        synchronized (this) {
            if (key == null || value == null) {
                throw new NullPointerException();
            }
            checkCapacity();
            DoubleLinkedStoreNode<K, V> node = new DoubleLinkedStoreNode<>(key, value, this, expirationTime);
            storeMap.put(key, node);
            addNode(node);
            return node.value();
        }
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

    @Override
    public int size() {
        return count.intValue();
    }

    @Override
    public void expireCache(StoreNode<K , V> expiredNode) {
        DoubleLinkedStoreNode<K , V> storeNode = (DoubleLinkedStoreNode<K , V>) expiredNode;
        if (storeMap.containsKey(storeNode.key())) {
            removeNode(storeNode);
            storeMap.remove(expiredNode.key());
            count.decrementAndGet();
        }
    }

    /**
     * check the capacity
     */
    private void checkCapacity() {
        if (count.getAndIncrement() >= capacity.get()) {
            DoubleLinkedStoreNode<?, ?> poppedNode = popTailNode();
            if (poppedNode.key() != null) {
                storeMap.remove(
                        poppedNode.key()
                );
            }
            poppedNode.expirationTime(-1);
            count.set(
                    capacity.get()
            );
            poppedNode = null;
        }
    }




    /**
     * add a node into the Linked Node List
     * @param storeNode a node use to store
     */
    private void addNode(DoubleLinkedStoreNode<?, ?> storeNode) {
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
    private synchronized void removeNode(DoubleLinkedStoreNode<?, ?> storeNode) {
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
    private void moveToHead(DoubleLinkedStoreNode<?, ?> storeNode) {
        removeNode(storeNode);
        addNode(storeNode);
    }

    private DoubleLinkedStoreNode<?, ?> popTailNode() {
        removeNode(
                tail.preNode()
        );
        return tail.preNode();
    }
}
