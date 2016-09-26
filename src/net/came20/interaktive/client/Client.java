package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.came20.interaktive.client.cli.CLI;
import net.came20.interaktive.client.gui.GUI;
import net.came20.interaktive.client.socket.AnnounceListener;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.LogHelper;

import org.zeromq.ZMQ;

import javax.swing.*;

/**
 * Created by cameron on 8/9/2016.
 */
public class Client {
    LogHelper logger = new LogHelper(this.getClass());

    static XStream xstream = new XStream(new DomDriver());

    static ZMQ.Context context = ZMQ.context(1);
    static ZMQ.Socket commandSock = context.socket(ZMQ.REQ);

    static Interpreter interpreter;
    static AnnounceListener announce;
    static HeartbeatListener heartbeat;

    static String token = null;

    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static CommandRoutable sendCommand(CommandRoutable command) {
        commandSock.send(command.toString());
        return (CommandRoutable) xstream.fromXML(new String(commandSock.recv()));
    }

    public static void returnCommand(CommandRoutable command) {
        interpreter.display(command);
    }

    public Client(int port, String address, boolean showGui) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}
        commandSock.connect("tcp://" + address + ":" + port);
        AnnounceListener.init(context, address, (port + 1));
        announce = new AnnounceListener();
        Thread announceThread = new Thread(announce, "Announce Listener");
        announceThread.start();
        heartbeat = new HeartbeatListener();
        Thread heartbeatThread = new Thread(heartbeat, "Heartbeat Listener");
        heartbeatThread.start();

        if (showGui) {
            logger.log("Starting GUI");
            interpreter = new GUI();
        } else {
            logger.log("Starting CLI");
            interpreter = new CLI();
        }

        //Thread cmdThread = new Thread(interpreter, "Interpreter");
        //cmdThread.start();
        interpreter.run();
    }
}
