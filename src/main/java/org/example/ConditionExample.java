package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static void main(String[] args) {
        Table table = new Table();
        Thread threadCook = new Thread(new Cook2(table), "Повар");
        Thread threadWaiter = new Thread(new Waiter2(table), "Официант");
        threadCook.start();
        threadWaiter.start();
    }
}

//монитор
class Table {
    ReentrantLock lock;  // блокировка
    Condition condition;  // условие блокировки
    int dishes = 0;

    Table() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    //забрать со стола
    public void getDish() {
        lock.lock();
        try {
            // ожидание на пустом столе
            while (dishes < 1) {
                condition.await();
            }
            dishes--;
            System.out.println("Официант забрал одну тарелку");
            System.out.println("На столе осталось " + dishes + " тарелок");
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //поставить на стол
    public void putDish(int numberDish) {
        lock.lock();
        try {
            // ожидание освобождения места на столе
            while (dishes >= 3) {
                condition.await();
            }
            dishes++;
            System.out.println("Повар приготовил " + numberDish + "-е блюдо. На столе " + dishes + " тарелок");
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Cook2 implements Runnable {
    private Table table;

    public Cook2(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        System.out.println("Повар начал готовить");
        try {
            for (int i = 1; i <= 10; i++) {
                Thread.sleep(1000);
                table.putDish(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Waiter2 implements Runnable {

    private Table table;

    public Waiter2(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(10_000); //официант работает медленнее повара
                table.getDish();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
