package org.example;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    public static void main(String[] args) throws InterruptedException {

        IncrementorRL incrementorRL = new IncrementorRL();
        for (int i = 0; i < 200; i++) {
            Thread thread = new Thread(new IncrementalThreadRL(incrementorRL));
            thread.start();
        }

        Thread.sleep(5000);
        System.out.println(incrementorRL.getAmount());//200*100 = 20 000
    }
}

class IncrementorRL{
    // создаем объект ReentrantLock
    private ReentrantLock lock = new ReentrantLock();
    private long amount = 0;

    //блок ReentrantLock
    public void increaseAmount(){
        lock.lock();
        System.out.println("HoldCount - " + lock.getHoldCount()); //количество удержаний блокировки текущим потоком
        System.out.println("QueueLength - " + lock.getQueueLength()); //длина очереди ожидания

        //опасный код
        try {
            amount++;
        } finally {
            // непременно снять блокировку, даже если генерируется исключение
            lock.unlock();
        }
    }

    public long getAmount() {
        return amount;
    }
}

class IncrementalThreadRL implements Runnable{

    private IncrementorRL incrementorRL;

    public IncrementalThreadRL(IncrementorRL incrementorRL) {
        this.incrementorRL = incrementorRL;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            incrementorRL.increaseAmount();
        }
    }
}
