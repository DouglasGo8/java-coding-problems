package com.packtpub.java.coding.problems.concurrency.threads;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class App {


    /**
     * The different lifecycle states
     * The NEW state
     * The RUNNABLE state
     * The BLOCKED state
     * The WAITING state
     * The TIMED_WAITING state
     * The TERMINATED state
     */


    @Test
    public void ofState_NEW_RUNNABLE_Thread() {

        final Runnable run = () -> out.println("Which your state?");
        var thread = new Thread(run);
        out.println(thread.getState()); // NEW
        thread.start();
        out.println(thread.getState()); // RUNNABLE


    }

    @Test
    @SneakyThrows
    public void ofState_RUNNABLE_BLOCKED_Thread() {

        var thread1 = new Thread(new SyncCode());
        var thread2 = new Thread(new SyncCode());

        thread1.start();
        TimeUnit.SECONDS.sleep(2);
        thread2.start();
        TimeUnit.SECONDS.sleep(2);
        //
        out.println(thread1.getState() + "-" + thread1.getName());

        out.println(thread2.getState() + "-" + thread2.getName()); // BLOCKED status cause class is static
        //
        //TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void ofSynchronized() {

        class Foo {
            synchronized void method() {
            } // Synchronized method case
        }

        class Bar {
            void method() {
                synchronized (this) {
                } // Synchronized block of code
            }
        }

        class Metal {
            private final Object allLock = new Object();

            void method() {
                synchronized (allLock) {
                } // Synchronized block of code
            }
        }


    }

    @Test
    public void ofThreadPools() {

        Executor p = (runnable) -> new Thread(runnable).start(); // Executor is a interface
        p.execute(() -> out.println("Hi"));
    }

    @Test
    @Ignore
    public void howToExecutorServiceWithPools() {

        @AllArgsConstructor
        final class SimpleThreadPool implements Runnable {

            private final int taskId;

            @Override
            @SneakyThrows
            public void run() {
                Thread.sleep(2000);
                out.println("Executing task " + taskId + " via " + Thread.currentThread().getName());
            }
        }

        var counter = new AtomicInteger();
        var queue = new LinkedBlockingDeque<>(5);

        ThreadFactory treadFactory = (Runnable r) -> {
            counter.incrementAndGet();
          return new Thread(r, "Cooled-Thread-" + counter.get());
        };


    }


    private static class SyncCode implements Runnable {

        @Override
        @SneakyThrows
        public void run() {
            syncMethod();
        }

        private static synchronized void syncMethod() {
            out.println(Thread.currentThread().getName() + " is in syncMethod() method");
            while (true) {
                // out.println("Hi from forever Thread");
                //TimeUnit.MINUTES.sleep(10);
            }
        }
    }

}
