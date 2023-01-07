package com.healer.core.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final class UnsafeUtil {

    private static Unsafe UNSAFE;

    private UnsafeUtil(){}


    private static Unsafe getUnsafe() {
        if (UNSAFE != null) {
            return UNSAFE;
        }
        try {
            UNSAFE = Unsafe.getUnsafe();
            return UNSAFE;
        } catch (SecurityException e) {
            // do nothing
        }

        try {
            Class<Unsafe> unsafeClass = Unsafe.class;
            for (Field field : unsafeClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object o = field.get(null);
                if (unsafeClass.isInstance(o)) {
                    UNSAFE = (Unsafe) o;
                    return UNSAFE;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException(" no unsafe class");
    }


    public static long getAddress(Object o) {
        Unsafe unsafe = getUnsafe();
        Object[] objects = {o};

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();

        switch (addressSize) {
            case 4 -> {
                return unsafe.getInt(objects, baseOffset);
            }
            case 8 -> {
                return unsafe.getLong(objects, baseOffset);
            }
            default -> throw new RuntimeException("unsupported address size: " + addressSize);
        }

    }

    public static void freeObject(Object o) {
        getUnsafe().freeMemory(
                getAddress(o)
        );
    }



}
