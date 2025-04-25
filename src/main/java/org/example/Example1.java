package org.example;

public class Example1 {
    //volatile у флага иначе не поменяется на true  в главном потоке и зависнет в цикле
    private static volatile boolean RUNNING;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!RUNNING) {
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        number = 42;
        Thread.sleep(5000);
        RUNNING = true;
    }
}
