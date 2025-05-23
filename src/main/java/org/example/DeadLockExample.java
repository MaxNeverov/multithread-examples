package org.example;

public class DeadLockExample {
    public static void main(String[] args) {
        Object right = new Object();
        Object left = new Object();

        new Thread(() -> {
            synchronized (right) {
                String name = Thread.currentThread().getName();
                System.out.println("Поток " + name + ": Удерживает блокировку right");
                sleep(1000);
                synchronized (left) {
                    System.out.println("Поток " + name + ": Удерживает блокировку left");
                }
            }
        }, "First thread").start();

        //Если left и right поменять местами случится deadlock
        new Thread(() -> {
            synchronized (right){
                String name = Thread.currentThread().getName();
                System.out.println("Поток " + name + ": Удерживает блокировку left");
                sleep(1000);
                synchronized (left){
                    System.out.println("Поток "+name+ ": Удерживает блокировку right");
                }
            }
        }, "Second thread").start();
    }

    private static void sleep(long mills){
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
