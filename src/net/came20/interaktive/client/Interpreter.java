package net.came20.interaktive.client;

/**
 * Created by cameron on 8/21/2016.
 */
public abstract class Interpreter implements Runnable {
    @Override
    public abstract void run();

    public void display(Object message) {
        System.out.println(message.toString());
    }
}
