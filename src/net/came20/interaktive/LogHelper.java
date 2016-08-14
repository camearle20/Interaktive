package net.came20.interaktive;


import sun.reflect.Reflection;


/**
 * Created by cameron on 8/9/2016.
 */
public class LogHelper {
    public static enum Levels {
        DEBUG,
        ERROR,
        FATAL,
        INFO,
        WARN
    }

    public static Levels level = Levels.INFO;

    private Class from;
    public LogHelper(Class from) {
        this.from = from;
    }

    public void log(Object message, Levels level) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + "[" + from.getName() + "] [" + level.toString() + "] " + message);
    }
    public void log(Object message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + "[" + from.getName() + "] [" + Levels.INFO.toString() + "] " + message);
    }
}
