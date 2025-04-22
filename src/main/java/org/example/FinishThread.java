package org.example;

//ОСТАНОВКА ПОТОКА С ПОМОЩЬЮ ФЛАГА
public class FinishThread {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        new Thread(task).start();

        Thread.sleep(5000);
        task.disable();
    }
}

class Task implements Runnable {
    private int count = 0;
    private boolean isActive = true;

    //флаг выхода из цикла
    void disable(){
        isActive = false;
    }

    @Override
    public void run() {
        while(isActive){
            count++;
            System.out.println(count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("конец");
    }
}
