package org.example;

public class StarvationExample {
    public static void main(String[] args) {
        System.out.println("Main поток стартанул");
        Thread t1 = new Thread(new MyThread2(), "Первый поток");
        Thread t2 = new Thread(new MyThread2(), "Второй поток");
        t1.start();
        t2.start();
        System.out.println("Main поток завершился");
    }
}

class MyThread2 implements Runnable {
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " стартанул");
        synchronized (MyThread.class) {
            System.out.println(threadName + " зашел в критический блок");
            while (true) {

            }
        }
    }
}
