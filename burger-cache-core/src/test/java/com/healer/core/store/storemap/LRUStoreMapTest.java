package com.healer.core.store.storemap;

import com.healer.core.store.node.DoubleLinkedStoreNode;
import com.healer.core.store.node.StoreNode;
import com.healer.core.store.storemap.mock.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


class LRUStoreMapTest {


    LRUStoreMap lRUStoreMap = new LRUStoreMap<>(20);

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGet() {
        Object result = lRUStoreMap.get("key");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testPut() {

        for (int i = 0; i < 30; i++) {
            Object result = lRUStoreMap.put("user"+i, new User());
        }
//        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testPutAll() {
        lRUStoreMap.putAll(Map.of("String", "String"));
    }

    @Test
    void testStoreMap() {
        Map result = lRUStoreMap.storeMap();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCount() {
        AtomicLong result = lRUStoreMap.count();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCapacity() {
        AtomicLong result = lRUStoreMap.capacity();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testHead() {
        StoreNode result = lRUStoreMap.head();
        Assertions.assertEquals(new DoubleLinkedStoreNode("key", "value"), result);
    }

    @Test
    void testTail() {
        StoreNode result = lRUStoreMap.tail();
        Assertions.assertEquals(new DoubleLinkedStoreNode("key", "value"), result);
    }

    @Test
    void testStoreMap2() {
        LRUStoreMap result = lRUStoreMap.storeMap(null);
        Assertions.assertEquals(new LRUStoreMap(0), result);
    }

    @Test
    void testCount2() {
        LRUStoreMap result = lRUStoreMap.count(null);
        Assertions.assertEquals(new LRUStoreMap(0), result);
    }

    @Test
    void testCapacity2() {
        LRUStoreMap result = lRUStoreMap.capacity(null);
        Assertions.assertEquals(new LRUStoreMap(0), result);
    }

    @Test
    void testHead2() {
        LRUStoreMap result = lRUStoreMap.head(new DoubleLinkedStoreNode("key", "value"));
        Assertions.assertEquals(new LRUStoreMap(0), result);
    }

    @Test
    void testTail2() {
        LRUStoreMap result = lRUStoreMap.tail(new DoubleLinkedStoreNode("key", "value"));
        Assertions.assertEquals(new LRUStoreMap(0), result);
    }

    @Test
    void testEquals() {
        boolean result = lRUStoreMap.equals("o");
        Assertions.assertEquals(true, result);
    }


    @Test
    void testHashCode() {
        int result = lRUStoreMap.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = lRUStoreMap.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme