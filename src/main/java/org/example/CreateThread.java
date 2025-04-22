package org.example;

//запуск потоков
public class CreateThread {
    public static void main(String[] args) {
        new MyThread("First Thread").start();
    }
}

//класс создания потоков и описание действий потоков(но лучше через лямбда выражение или интерфейс Runnable)
class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("This thread " + Thread.currentThread());
    }
}