package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLetchExample {
    public static void main(String[] args) {
        //максимальный набор в группу 15 человек
        CountDownLatch countDownLatch = new CountDownLatch(15);
        for (int i = 1; i < 16; i++) {     // создадим 15 желающих
            new Thread(new Student(i, countDownLatch)).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Student implements Runnable {
    private final CountDownLatch countDownLatch;
    private final int number;

    public Student(int number, CountDownLatch countDownLatch) {
        this.number = number;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        //Уменьшает счетчик на 1
        countDownLatch.countDown();
        System.out.println("Добавился новый студент под номером "+number+". Осталось набрать " + countDownLatch.getCount());
        try {
            // здесь студен приостанавливается и ждет, пока наберется группа больше 15 человек
            countDownLatch.await();
            // здесь студен приостанавливается и ждет, пока наберется группа больше 15 человек ИЛИ истечет время ожидания
//            countDownLatch.await(15L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Тут каждый поток выведет строку, как наберется 15 человек
        System.out.println("Студент " + number + " начал занятия");
    }
}
