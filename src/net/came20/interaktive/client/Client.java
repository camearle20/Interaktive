package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.came20.interaktive.client.cli.CLI;
import net.came20.interaktive.client.gui.GUI;
import net.came20.interaktive.LogHelper;
import org.zeromq.ZMQ;

import javax.swing.*;

/**
 * Created by cameron on 8/9/2016.
 */
public class Client {

    ZMQ.Context context;
    LogHelper logger = new LogHelper(this.getClass());



    static Interpreter interpreter;


    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public Client(ZMQ.Context context, boolean showGui) {
        this.context = context;
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        if (showGui) {
            logger.log("Starting GUI");
            interpreter = new GUI(this.context);
        } else {
            logger.log("Starting CLI");
            interpreter = new CLI(this.context);
        }

        //Thread cmdThread = new Thread(interpreter, "Interpreter");
        //cmdThread.start();
        interpreter.run();
        context.close();
        System.exit(0x0);
    }
}
