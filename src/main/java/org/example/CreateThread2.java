package org.example;

public class CreateThread2 {
    public static void main(String[] args) {

        //создание потока через лямбда выражение
        Runnable runnable = () -> System.out.println(Thread.currentThread());

        //запуск через лямба выражение
        Thread thread = new Thread(runnable, "MyLambdaThread");
        System.out.println(thread.getState().name());
        thread.start();
        System.out.println(thread.getState().name());

        //запуск через интерфейс Runnable
        new Thread(new MySecondThread(), "MyInterfaceThread").start();

    }
}

//создание потока через интерфейс runnable
class MySecondThread implements Runnable  {

    @Override
    public void run() {
        System.out.println(Thread.currentThread());
    }
}
