package com.dreamteam.os.lab2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFixnumLockTest {
    private AbstractFixnumLock lock = new FixnumLockExample(10);
    private List<Integer> idFromRegister = new ArrayList<>(11);

    class TestThread extends Thread {
        public void run() {
            idFromRegister.add(lock.register());
        }
    }

    public class Test2Thread extends Thread {
        public void run() {
            lock.register();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            lock.unregister();
        }
    }

    @Test
    void limitShouldBeReached() throws InterruptedException {
        for (int i = 0; i < 11; i++) {
            TestThread testThread = new TestThread();
            testThread.start();
        }
        Thread.sleep(2000);
        assertEquals(11, idFromRegister.size());
        for (int i = -1; i < 10; i++) {
            assertTrue(idFromRegister.contains(i));
        }
    }

    @Test
    void shouldRemoveOneThreadAndThenRegisterItAgainWithSameID() throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            TestThread testThread = new TestThread();
            testThread.start();
        }

        int idOfCurr = lock.register();
        idFromRegister.add(idOfCurr);
        Thread.sleep(2000);
        assertEquals(10, idFromRegister.size());
        assertTrue(idFromRegister.contains(idOfCurr));

        lock.unregister();
        assertEquals(-1, lock.getId());
        lock.register();
        assertEquals(idOfCurr, lock.getId());
    }

    @Test
    void shouldNotRemoveAnythingAndReturnMinus1() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            TestThread testThread = new TestThread();
            testThread.start();
        }
        Thread.sleep(2000);
        assertEquals(10, idFromRegister.size());

        assertEquals(-1, lock.unregister());
        assertEquals(-1, lock.getId());
        assertEquals(10, idFromRegister.size());
    }

    @Test
    void shouldNotRegisterOneThreadTwoTimes() {
        lock.register();
        assertEquals(-1, lock.register());
    }
}
