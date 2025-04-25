package org.example;

import java.util.Random;
import java.util.concurrent.Exchanger;

import static java.lang.String.valueOf;

//обмен значениями через exchange
public class ExchangingExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new Cook4(exchanger)).start();
        new Thread(new Waiter4(exchanger)).start();
    }
}
class Cook4 implements Runnable {
    private Exchanger<String> exchanger;

    public Cook4(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int randomNumber = new Random().nextInt(100); // придумываем рандомный номер блюда
                System.out.println("Повар: Я приготовил блюдо под номером " + randomNumber + "! Забирай!");
                String exchange = exchanger.exchange(valueOf(randomNumber));
                System.out.println("Ответ " + exchange);
                Thread.sleep(1_000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

class Waiter4 implements Runnable {
    private Exchanger<String> exchanger;
    public String message = "привет";

    public Waiter4(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                String gettingMessage = exchanger.exchange(message);
                System.out.println("Официант: Я готов унести блюдо под номером: " + gettingMessage);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
