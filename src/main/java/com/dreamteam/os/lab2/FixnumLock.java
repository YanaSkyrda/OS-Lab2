package com.dreamteam.os.lab2;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {
    int getId();
    int register();
    int unregister();
    void lock();
    void unlock();
}
