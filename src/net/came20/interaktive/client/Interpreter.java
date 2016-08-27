package net.came20.interaktive.client;

/**
 * Created by cameron on 8/21/2016.
 */
public class Interpreter implements Runnable {
    @Override
    public void run() {
        Thread.currentThread().stop();
        Thread.currentThread().destroy();
    }

    public void display(Object message) {
        System.out.println(message.toString());
    }
}
