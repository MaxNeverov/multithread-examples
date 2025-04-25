package practic.example2;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Example2 {
    public static void main(String[] args) throws InterruptedException {
        DressRoom dressRoom = new DressRoom();
        new Thread(new Person(dressRoom, Type.MAN), "Мужчина1").start();
        Thread.sleep(1000);
        new Thread(new Person(dressRoom, Type.WOMAN), "Женщина1").start();
        Thread.sleep(1000);
        new Thread(new Person(dressRoom, Type.MAN), "Мужчина2").start();
        Thread.sleep(1000);
        new Thread(new Person(dressRoom, Type.WOMAN), "Женщина2").start();
        Thread.sleep(1000);
        new Thread(new Person(dressRoom, Type.MAN), "Мужчина3").start();
        Thread.sleep(1000);
        new Thread(new Person(dressRoom, Type.WOMAN), "Женщина3").start();
        Thread.sleep(1000);
        new Thread(new Person(dressRoom, Type.MAN), "Мужчина4").start();
        Thread.sleep(1000);

    }
}

class DressRoom {
    ReentrantLock lock;
    Condition condition;
    private Type currentType;
    private boolean isFree = true;
    private final AtomicInteger count = new AtomicInteger(0);
    private boolean flag = false;

    public DressRoom() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void inRoom(Type type) {
        lock.lock();
        while (true){
            if (isFree) {
                currentType = type;
                isFree = false;
            }
            if (type != currentType || flag || count.get() == 3) {
                if (!flag){
                    flag = true;
                }
                try {
                        condition.await();

                    flag = false;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                break;
            }
        }

        if (count.get() < 3){
            count.incrementAndGet();

        }
        lock.unlock();

    }
    public void outRoom() {
        lock.lock();


        if (count.decrementAndGet() == 0) {
            System.out.println("Все комнаты своодны. Следующие...");
            isFree = true;
            condition.signalAll();
        }
        lock.unlock();
    }

}

class Person implements Runnable {

    private DressRoom dressRoom;
    private Type type;


    public Person(DressRoom dressRoom, Type type) {
        this.dressRoom = dressRoom;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            dressRoom.inRoom(type);
            System.out.println(Thread.currentThread().getName() + " вошел в раздевалку. Занято комнат: " + dressRoom.getCount() + "/3");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " вышел из раздевалки");
            dressRoom.outRoom();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


