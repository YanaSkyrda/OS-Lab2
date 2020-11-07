package com.dreamteam.os.lab2;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {
    long getId();
    boolean register();
    boolean unregister();
    void lock();
    void unlock();
}
