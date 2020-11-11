package com.dreamteam.os.lab2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

@Slf4j
public class BakeryLock extends AbstractFixnumLock {

    int[] priority_numbers;
    boolean[] entering;
    private int maxThreads = 20;

    private int Counter = 0;
    private boolean randomComponent = false;


    public BakeryLock(Integer numberOfThreads) {
        super(numberOfThreads);
        this.priority_numbers = new int[maxThreads];
        this.entering = new boolean[maxThreads];
        for(int i=0;i<maxThreads;i++){
            entering[i]=false;
        }
    }

    @Override
    public void lock()
    {
        entering[getId()]=true;
        int max = 0;
        for (int i = 0; i < maxThreads; i++) {
            if (priority_numbers[i] > max) {
                max = priority_numbers[i];
            }
        }
        priority_numbers[getId()]= max + 1;
        entering[getId()]=false;



        for (int i = 0; i < maxThreads; ++i) {
            if (i != getId()) {

                while (entering[i]) {
                    Thread.yield();
                }
                while (priority_numbers[i]!=0 && (priority_numbers[getId()] > priority_numbers[i] ||
                        (priority_numbers[getId()] == priority_numbers[i] && getId() > i))) {
                    Thread.yield();
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        priority_numbers[getId()] = 0;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}