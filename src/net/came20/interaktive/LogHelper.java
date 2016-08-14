package net.came20.interaktive;


import sun.reflect.Reflection;


/**
 * Created by cameron on 8/9/2016.
 */
public class LogHelper {
    public static enum Levels {
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(3),
        FATAL(4);
        private int levelNumber;
        private Levels(int levelNumber) {
            this.levelNumber = levelNumber;
        }
        private int getLevelNumber() {
            return this.levelNumber;
        }
    }

    public static Levels displayLevel = Levels.DEBUG;

    private Class from;
    public LogHelper(Class from) {
        this.from = from;
    }

    public void log(Object message, Levels level) {
        if (level.getLevelNumber() >= displayLevel.getLevelNumber()) {
            System.out.println("[" + Thread.currentThread().getName() + "] " + "[" + from.getName() + "] [" + level.toString() + "] " + message);
        }
    }
    public void log(Object message) {
        Levels level = Levels.INFO;
        log(message, level);
    }
}
