package com.ckguys.test;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author XYQ
 */
class ResourceBundleTest {
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    @Test
    void testPost() throws InterruptedException {
        final int[] i = {0};
        Runnable r = () -> System.out.println(Thread.currentThread().getName());
        Runnable r2 = () -> System.out.println(Thread.currentThread().getName());
        ScheduledFuture<?> future = service.scheduleAtFixedRate(r, 0, 1000, TimeUnit.MILLISECONDS);
        ScheduledFuture<?> future2 = service.scheduleAtFixedRate(r2, 0, 1000, TimeUnit.MILLISECONDS);
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(5000);
        future.cancel(true);
        future2.cancel(true);
    }

}

