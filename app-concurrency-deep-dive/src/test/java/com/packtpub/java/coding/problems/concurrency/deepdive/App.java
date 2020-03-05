package com.packtpub.java.coding.problems.concurrency.deepdive;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


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


}
