package org.example;

public class DaemonExample {
    public static void main(String[] args) throws InterruptedException {

        //поток Counter
        Thread thread = new Thread(new Counter(), "Counter: ");
        //Если поток демон, то он закончит выполнение как только закончатся работающие потоки
        thread.setDaemon(true);
        thread.start();

        //основной поток main который спит 15 секунд и заканчивается
        Thread.sleep(15000);
        System.out.println(Thread.currentThread() + "finished!");
    }
}

class Counter implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
