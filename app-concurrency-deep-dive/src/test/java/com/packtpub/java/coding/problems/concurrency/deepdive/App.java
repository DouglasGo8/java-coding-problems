package com.packtpub.java.coding.problems.concurrency.deepdive;

import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;


import static java.lang.System.out;

public class App {

    @Test

    @SneakyThrows
    public void runningAsyncTaskWithVoidReturn() {

        CompletableFuture.runAsync(() -> {

            out.println("Order is printed by:");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).get();

    }

    @Test
    @SneakyThrows
    public void runningAsyncTaskWithStringReturn() {


        var cfPrinter = CompletableFuture.supplyAsync(() -> {

            out.println("Order is printed by:");

            try {
                Thread.sleep(500);

                return "Hi";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }, Executors.newSingleThreadExecutor()).get();

        out.println(cfPrinter);

    }


    @Test
    @SneakyThrows
    public void attachCallbackThatProcess() {
        var message = CompletableFuture
                .supplyAsync(() -> "Hi")
                .thenApply("Say "::concat)
                .thenApply(r -> r.concat(" to Java")).get();

        out.println(message);

    }

    @Test
    @SneakyThrows
    public void whenOccursException() {

        var result = CompletableFuture.supplyAsync(() -> {

            final int surrogate = new Random().nextInt(1000);

            if (surrogate < 500) {
                throw new IllegalStateException("Invoice error");
            }

            return surrogate;

        }).exceptionally(ex -> {
            out.println(Thread.currentThread().getName());
            out.println(ex.getMessage());
            return 0;
        }).get();

        out.println(result);

    }

    @Test
    @SneakyThrows

    public void chainOfExceptions() {

        var result = CompletableFuture.supplyAsync(() -> {

            final int surrogate = new Random().nextInt(1000);

            if (surrogate < 500) {
                throw new IllegalStateException("Invoice error");
            }

            return surrogate;
        }).exceptionally(ex -> {
            out.println(Thread.currentThread().getName());
            return 0;
        }).thenApply((r) -> {
            final int surrogate = new Random().nextInt(1000);

            if (surrogate < 500) {
                throw new IllegalStateException("Invoice error");
            }

            return surrogate * r;
        }).exceptionally(ex -> {
            out.println(Thread.currentThread().getName());
            return 0;
        }).get();

        out.println(result);
    }

    @Test
    @SneakyThrows
    public void usingHandle() {
        CompletableFuture.supplyAsync(() -> {
            final int surrogate = new Random().nextInt(1000);

            if (surrogate < 500) {
                throw new IllegalStateException("Invoice error");
            }

            return surrogate;
        }).handle((res, ex) -> {
            if (null != ex) {
                return 0;
            }
            if (null != res) {
                res *= 2;
            }
            return res;
        }).get();
    }

    @Test
    @SneakyThrows
    public void combiningMultipleFuture() {

        final Function<String, CompletableFuture<String>> f1 = (n) -> CompletableFuture.supplyAsync(() -> "Hi ".concat(n));
        final Function<String, CompletableFuture<String>> f2 = (n) -> CompletableFuture.supplyAsync(() -> n.concat(" well done.."));

        var result = f1.apply("Cargo").thenCompose(f2).get();

        out.println(result);

    }

    @Test
    public void optimizeBusyWait() throws InterruptedException {

        final class StartService implements Runnable {

            @Setter
            private volatile boolean isServiceAvailable;

            @Override
            public void run() {

                while (!isServiceAvailable) {
                    // Use spin-wait hit (ask to processor to optimize the resource)
                    // This should perform better if the underling hardware support
                    // the hint
                    Thread.onSpinWait();
                }
                serviceRun();
            }

            private void serviceRun() {
                out.println("Service is running...");
            }

        }

        var startService = new StartService();
        new Thread(startService).start();
        Thread.sleep(5000);

        startService.setServiceAvailable(true);


    }

    @Test
    public void taskCancellation() throws InterruptedException {

        final class RandomList implements Runnable {

            private volatile boolean isCancelled;
            private Random random = new Random();
            private final List<Integer> randoms = new CopyOnWriteArrayList<>();

            @Override
            public void run() {
                while (!isCancelled) {
                    randoms.add(random.nextInt(100));
                }
            }

            public void cancel() {
                isCancelled = true;
            }

            public List<Integer> getRandoms() {
                return randoms;
            }

        }

        var randomList = new RandomList();
        var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executor.execute(randomList);
        }
        Thread.sleep(100);
        randomList.cancel();
        randomList.getRandoms().forEach(out::println);
    }


    

}
