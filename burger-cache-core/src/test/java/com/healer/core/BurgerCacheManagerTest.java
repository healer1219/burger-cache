package com.healer.core;

import com.healer.core.store.storemap.mock.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.*;

class BurgerCacheManagerTest {
    @Mock
    AtomicLong capacity;
    //Field expirationStrategy of type ExpirationStrategy - was not mocked since Mockito doesn't mock enums
    @Mock
    BurgerCache cache;
    @InjectMocks
    BurgerCacheManager burgerCacheManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCache() {
        BurgerCache cache = new BurgerCacheManager(null, null)
                .expirationStrategy(BurgerCacheManager.ExpirationStrategy.LRU)
                .capacity(new AtomicLong(20))
                .buildCache();

        User testPut = cache.put("test1", new User());

        User testGet = cache.get("test1", User.class);

        Assertions.assertEquals(testGet, testPut);
    }

    @Test
    void testCacheCapacity() {
        List<Thread> putThreadList = new ArrayList<>();
        List<Thread> getThreadList = new ArrayList<>();
        BurgerCacheManager burgerCacheManager = new BurgerCacheManager(null, null);

        BurgerCache cache = burgerCacheManager
                .expirationStrategy(BurgerCacheManager.ExpirationStrategy.LRU)
                .capacity(new AtomicLong(20))
                .buildCache();

        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            putThreadList.add(
                    new Thread(
                            () -> cache.put(
                                    "test" + finalI,
                                    new User("User-" + Thread.currentThread())
                            )
                    )
            );
            getThreadList.add(
                    new Thread(
                            () -> {
                                try {
                                    User user = cache.get(
                                            "test" + finalI,
                                            User.class
                                    );

                                    System.out.println(user.getUserName());
                                } catch (Exception e) {
                                    //do nothing
                                }
                            }
                    )
            );
        }
        putThreadList.parallelStream().forEach(thread -> {
            try {
                thread.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        getThreadList.parallelStream().forEach(Thread :: run);

        Assertions.assertEquals(
                cache.getStoreMapSize(BurgerCache.StoreNames.DEFAULT_STORE.storeName()),
                burgerCacheManager.capacity().intValue()
        );
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme