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