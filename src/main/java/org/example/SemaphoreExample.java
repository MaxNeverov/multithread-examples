package org.example;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        // 3 разрешения на проход и true - обслуживание в порядке очереди
        Semaphore semaphore = new Semaphore(3, true);

        for (int i = 0; i < 100; i++) {
            new Thread(new Client(i, semaphore)).start();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Client implements Runnable {

    private final Semaphore semaphore;
    private final int number;

    public Client(int number, Semaphore sem) {
        this.number = number;
        this.semaphore = sem;
    }

    @Override
    public void run() {
        try {
            System.out.println("Я " + number + "й клиент! Могу у вас подстричься?");
            System.out.println("В очереди сидит " + semaphore.getQueueLength() + " клиентов");
            //запрашиваем доступ у семафора
            //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
            //пока семафор не разрешит доступ
            semaphore.acquire();
            //стрижемся
            Thread.sleep(1500);
            //освобождаем ресурс
            semaphore.release();
            System.out.println("Клиент под номером " + number + " был обслужен.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
