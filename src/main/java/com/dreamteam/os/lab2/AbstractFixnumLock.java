package com.dreamteam.os.lab2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractFixnumLock implements FixnumLock{
    private int numberOfThreads;
    private List<Long> registeredThreads;

    private static final Object syncObj = new Object();

    AbstractFixnumLock(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.registeredThreads = new ArrayList<>();
    }

    @Override
    public long getId() {
        return Thread.currentThread().getId();
    }

    @Override
    public boolean register() {
        synchronized (syncObj) {
            long id = getId();
            if (registeredThreads.size() < numberOfThreads) {
                if (registeredThreads.contains(id)) {
                    log.info("Thread with id " + id + " already registered.");
                } else {
                    registeredThreads.add(id);
                    log.info("Thread with id " + id + " is registered.");
                    return true;
                }
            } else {
                log.info("Threads number limit already reached. Can't register thread with id " + id);
            }
            return false;
        }
    }

    @Override
    public boolean unregister() {
        synchronized (syncObj) {
            long id = getId();
            if (registeredThreads.contains(id)) {
                if (registeredThreads.remove(id)) {
                    log.info("Unregister thread with id " + id + ".");
                    return true;
                } else {
                    log.info("Can't unregister thread with id " + id + ".");
                }

            } else {
                log.info("Thread with id " + id + " is not registered.");
            }
            return false;
        }
    }

    private void reset() {
        synchronized (syncObj) {
            registeredThreads.clear();
        }
    }
}
