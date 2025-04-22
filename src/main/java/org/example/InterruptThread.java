package org.example;

//ОСТАНОВКУА ПОТОКА С ПОМОЩЬЮ МЕТОДА INTERRUPT
public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new TaskWithInterrupt());
        thread.start();

        Thread.sleep(5000);

        //СТАВИТ ФЛАГ isInterrupted() В TRUE
        thread.interrupt();
    }
}

class TaskWithInterrupt implements Runnable {
    private int count = 0;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            count++;
            System.out.println(count);
            try {
                //Будет выбрасывать ошибку тк прерывание будет попадать на сон потока
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //При исключении со сном будет возвращать значение false
                System.out.println(Thread.currentThread().isInterrupted());

                //Чтобы выйти из цикла при срабатывании исключения(МОЖНО ЧЕРЕЗ BREAK;)
                Thread.currentThread().interrupt(); //восстанавливаем флаг прерывания
            }
        }
        System.out.println("конец");
    }
}



