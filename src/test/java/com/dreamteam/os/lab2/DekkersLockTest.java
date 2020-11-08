package com.dreamteam.os.lab2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DekkersLockTest {

    @Test
    void dekkersLockTest() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        DekkersLock dekkersLock = new DekkersLock();
        CallableSumWithLock.setMultiplier( 1 );
        Future<Long> sum1Future = executorService.submit(new CallableSumWithLock(dekkersLock));
        Future<Long> sum2Future = executorService.submit(new CallableSumWithLock(dekkersLock));
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

    @Test
    void unsupportedFunctionsTest() {
        DekkersLock dekkersLock = new DekkersLock();
        assertThrows(UnsupportedOperationException.class, dekkersLock::tryLock);
        assertThrows(UnsupportedOperationException.class, dekkersLock::lockInterruptibly);
        assertThrows(UnsupportedOperationException.class, dekkersLock::newCondition);
    }
}
