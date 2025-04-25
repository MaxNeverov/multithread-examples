package org.example;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallAndFuture {
    public static void main(String []args) throws Exception {
        //метод call - выполняет при запуске потока
        Callable<Long> task = () -> {
            Thread.sleep(5_000);
            return System.currentTimeMillis();
        };

        // поток с результатом из Callable
        FutureTask<Long> future = new FutureTask<>(task);
        new Thread(future, "My Thread").start();

        System.out.println(future.get());
        System.out.println(Thread.currentThread() + " finished!");
    }
}
