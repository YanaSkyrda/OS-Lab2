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
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        DekkersLock dekkersLock = new DekkersLock();
        Future<Integer> sum1Future = executorService.submit(new CallableSumWithLock(dekkersLock));
        Future<Integer> sum2Future = executorService.submit(new CallableSumWithLock(dekkersLock));
        int sum1 = sum1Future.get();
        int sum2 = sum2Future.get();

        if (sum1 > sum2) {
            int temp = sum1;
            sum1 = sum2;
            sum2 = temp;
        }

        assertEquals(sum1, 499500);
        assertEquals(sum2, 999000);
    }

    @Test
    void unsupportedFunctionsTest() {
        DekkersLock dekkersLock = new DekkersLock();
        assertThrows(UnsupportedOperationException.class, dekkersLock::tryLock);
        assertThrows(UnsupportedOperationException.class, dekkersLock::lockInterruptibly);
        assertThrows(UnsupportedOperationException.class, dekkersLock::newCondition);
    }
}
