package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Постепенное выполнение задачи
public class ExecutorExample {
    public static void main(String[] args) {
        //создание пула из 2х потоков
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        //2 потока выполняют 10 задач
        for (int i = 0; i < 10; i++) {
            fixedPool.submit(new Task2(i));
        }
        //закрытие пула
        fixedPool.shutdown();
    }
}

class Task2 implements Runnable {
    private int number;

    public Task2(int number) { this.number = number; }

    public void run() {
        try {
            System.out.println("Я родился! " + number);
            Thread.sleep(2000);
            System.out.println("Я помер( " + number);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
