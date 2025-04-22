package org.example;

import java.util.concurrent.atomic.AtomicLong;

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {

        Incrementor incrementor = new Incrementor();
        for (int i = 0; i < 200; i++) {
            Thread thread = new Thread(new IncrementalThread(incrementor));
            thread.start();
        }

        Thread.sleep(5000);
        System.out.println(incrementor.getAmount());//200*100 = 20 000
    }
}

class Incrementor{
    //Атомарная обертка, альтернатива synchrinized
    private AtomicLong amount = new AtomicLong(0);

    //создает состояние гонок
    public void increaseAmount(){
        //атомарный инкремент
        amount.incrementAndGet();
    }

    public AtomicLong getAmount() {
        return amount;
    }
}

class IncrementalThread implements Runnable{

    private Incrementor incrementor;

    public IncrementalThread(Incrementor incrementor) {
        this.incrementor = incrementor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            incrementor.increaseAmount();
        }
    }
}
