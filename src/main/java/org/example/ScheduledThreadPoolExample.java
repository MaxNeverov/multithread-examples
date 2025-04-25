package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExample {
    public static void main(final String[] arguments) throws InterruptedException {

//        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        //создает и выполняет однократное действие, которое выполняется после заданной задержки
//        scheduler.schedule(new Alarm(), 5, TimeUnit.SECONDS);
//        scheduler.shutdown();

        int count = 0;
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //поток который каждые три скекунды с начальной паузой в 0 секунд производит действие Reminder()
        scheduler.scheduleAtFixedRate(new Reminder(), 0, 3, TimeUnit.SECONDS);

        //основной поток
        while (count < 10) {
            Thread.sleep(1000);
            count++;
            System.out.println(count);
        }
        scheduler.shutdown();
    }
}

class Alarm implements Runnable {

    public void run() {
        System.out.println("Дзинь-дзинь");
    }
}

class Reminder implements Runnable {

    public void run() {
        System.out.println("Пора изучать многопоточку!");
    }
}