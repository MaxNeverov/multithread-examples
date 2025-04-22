package org.example;

import static java.lang.Thread.sleep;

public class WaitNotifyExample {
    public static void main(String[] args) {
        Message message = new Message(); //объект, через который будут общаться потоки

        Thread threadCook = new Thread(new Cook(message), "Повар");
        Thread threadWaiter = new Thread(new Waiter(message), "Официант");
        threadCook.start();
        threadWaiter.start();
    }

}

//Класс МОНИТОРА
class Message {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

class Cook implements Runnable {
    private Message msg;

    public Cook(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            System.out.println("Повар начал готовить " + count);
            sleep(2000);
                  /*
                    Перед msg.notify(); нужно захватить msg в монопольное использование,
                    чтобы убедиться, что никто кроме этого потока не имеет доступа к объекту.
                     */
            synchronized (msg) {
                msg.setMsg("Заказ " + count + ". Блюда готовы, можно забирать");
                count++;
                //оповещает официанта о начале выполнения своего потока, не передает ему выполнение моментально!!!!!
                msg.notify();
            }
        }
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Waiter implements Runnable {

    private Message msg;

    public Waiter(Message m) {
        this.msg = m;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            System.out.println("Официант ждет, пока повар начнет готовить " + count);
            count++;
            synchronized (msg) {
                try {
                    //передает выполнение другим потокам моментально, и будет ждать пока не сработает notify()
                    msg.wait();
                    System.out.println("Сообщение от повара получено: " + msg.getMsg());
                    System.out.println("___________________________");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
