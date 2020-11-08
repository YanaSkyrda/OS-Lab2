package com.dreamteam.os.lab2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class CallableSumWithLock implements Callable<Integer> {

    private AbstractFixnumLock lock;

    private static int multiplier = 1;

    public CallableSumWithLock(AbstractFixnumLock lock) {
        this.lock = lock;
    }

    @Override
    public Integer call() {
        lock.register();
        lock.lock();
        log.info("Locked thread: " + Thread.currentThread().getName());
        int counter = 0;
        for (int i = 0; i < 1000; i++) {
            counter += i * multiplier;
        }
        multiplier++;
        lock.unlock();
        log.info("Unlocked thread: " + Thread.currentThread().getName());
        return counter;
    }
}
