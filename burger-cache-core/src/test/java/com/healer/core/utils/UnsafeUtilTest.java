package com.healer.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnsafeUtilTest {

    @Test
    void testGetAddress() {
        long result = UnsafeUtil.getAddress("o");
        Assertions.assertEquals(0L, result);
    }

    @Test
    void testFreeObject() {
        UnsafeUtil.freeObject("o");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme