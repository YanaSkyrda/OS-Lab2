package com.dreamteam.os.lab2;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BakeryLockTest {

    @Test
    void bakeryLockTest() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        BakeryLock bakeryLock = new BakeryLock(5);
        CallableSumWithLock.setMultiplier( 1 );
        Future<Long> sum1Future = executorService.submit(new CallableSumWithLock(bakeryLock));
        Future<Long> sum2Future = executorService.submit(new CallableSumWithLock(bakeryLock));
        long sum1 = sum1Future.get();
        long sum2 = sum2Future.get();

        if (sum1 > sum2) {
            long temp = sum1;
            sum1 = sum2;
            sum2 = temp;
        }

        assertEquals(4999950000L, sum1);
        assertEquals(9999900000L, sum2);
    }


}
