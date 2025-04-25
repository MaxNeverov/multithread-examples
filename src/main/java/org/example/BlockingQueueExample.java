package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueExample {
    //сигнал прекращения работы бля официанта, когда посетители закончатся
    private static final Integer POISON = -1;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10); //10 - количество столиков

        new Thread(new Hostess(queue, POISON), "Hostess").start();
        new Thread(new Waiter3(queue, POISON), "Waiter").start();
    }
}

class Hostess implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final Integer POISON;

    public Hostess(BlockingQueue<Integer> queue, Integer POISON) {
        this.queue = queue;
        this.POISON = POISON;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                System.out.println("[Hostess] пригласил гостя №" + i);
                //положить элемент в очередь
                queue.put(i);
                System.out.println("[Hostess] еще есть свободных столиков : " + queue.remainingCapacity());
                Thread.sleep(200);
            }
            // последний элемент - пилюля (закончились посетители)
            queue.put(POISON);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Waiter3 implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final Integer POISON;

    public Waiter3(BlockingQueue<Integer> queue, Integer POISON) {
        this.queue = queue;
        this.POISON = POISON;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //вытаскивает из очереди
                Integer take = queue.take();
                // если это отравленная пилюлю, то завершай работу
                if (POISON.equals(take)) {
                    System.out.println("Официант пошел домой.");
                    break;
                }
                System.out.println("[Waiter] обслужил гостя №" + take);
                System.out.println("[Waiter] еще есть свободных столиков : " + queue.remainingCapacity());
                Thread.sleep(700);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
