package com.sparta.spartime.helper;

import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyHelper {

    private static final int THREAD = 300;
    private static final int THREAD_POOL = 20;
    private static final int DELAY_MILLIS = 10;
//    private static final int DELAY_MILLIS = 0;

    public static int execute(final Executable executable)
            throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD);
        CountDownLatch latch = new CountDownLatch(THREAD_POOL);

        for (long i = 0; i < THREAD; i++) {
            executorService.submit(() -> {
                try {
                    executable.execute();
                } catch (final Throwable e) {
                    System.out.println(e.getClass().getName());
                } finally {
                    latch.countDown();
                }
            });

            if (DELAY_MILLIS > 0) {
                // Introduce a delay between each submission
                try {
                    Thread.sleep(DELAY_MILLIS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // Restore interrupted status
                    throw e;
                }
            }
        }

        latch.await();

        return THREAD_POOL;
    }

    public static int getTHREAD() {
        return THREAD;
    }
}