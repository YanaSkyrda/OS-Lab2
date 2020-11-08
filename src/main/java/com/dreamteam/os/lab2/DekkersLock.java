package com.dreamteam.os.lab2;

import lombok.extern.slf4j.Slf4j;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;

@Slf4j
public class DekkersLock extends AbstractFixnumLock {

    private final List<AtomicBoolean> wantsToEnter
            = Arrays.asList(new AtomicBoolean(false), new AtomicBoolean(false));
    private AtomicBoolean turn = new AtomicBoolean(false);

    public DekkersLock() {
        super(2);
    }

    @Override
    public void lock() {
        wantsToEnter.get(getId()).set(true);
        while (wantsToEnter.get(otherId(getId())).get()) {
            if ((turn.get() ? 1 : 0) != getId()) {
                wantsToEnter.get(getId()).set(false);
                while ((turn.get() ? 1 : 0) != getId()) {
                    //busy wait
                    Thread.yield();
                }
                wantsToEnter.get(getId()).set(true);
            }
        }
    }

    @Override
    public void lockInterruptibly() {
        throw new UnsupportedOperationException("lockInterruptibly() is unsupported for DekkersLock");
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("tryLock() is unsupported for DekkersLock");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        throw new UnsupportedOperationException("tryLock() is unsupported for DekkersLock");
    }

    @Override
    public void unlock() {
        turn.set(!turn.get());
        wantsToEnter.get(getId()).set(false);
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("newCondition() is unsupported for DekkersLock");
    }

    private int otherId(int id) {
        if (id == 0) {
            return 1;
        }
        return 0;
    }
}
