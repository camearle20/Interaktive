package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.came20.interaktive.client.cli.CLI;
import net.came20.interaktive.client.gui.GUI;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.*;
import net.came20.interaktive.LogHelper;

import org.zeromq.ZMQ;

/**
 * Created by cameron on 8/9/2016.
 */
public class Client {
    LogHelper logger = new LogHelper(this.getClass());

    static XStream xstream = new XStream(new DomDriver());

    static ZMQ.Context context = ZMQ.context(1);
    static ZMQ.Socket commandSock = context.socket(ZMQ.REQ);
    static ZMQ.Socket announceSock = context.socket(ZMQ.SUB);

    static Interpreter interpreter;

    static String token = null;

    public static CommandRoutable sendCommand(CommandRoutable command) {
        commandSock.send(command.toString());
        return (CommandRoutable) xstream.fromXML(new String(commandSock.recv()));
    }

    public static void returnCommand(CommandRoutable command) {
        interpreter.display(command);
    }

    public static void logout(boolean doExit) {
        sendCommand(new CommandRoutable(Commands.LOGOUT_REQUEST, new ParameterLogoutRequest(token)));
        if (doExit) {
            System.exit(0);
        } else {
            interpreter.login();
        }
    }

    public Client(int port, String address, boolean showGui) {
        commandSock.connect("tcp://" + address + ":" + port);
        announceSock.connect("tcp://" + address + ":" + (port + 1));

        if (showGui) {
            interpreter = new GUI();
        } else {
            interpreter = new CLI(token);
        }

        Thread cmdThread = new Thread(interpreter, "Interpreter");
        cmdThread.start();
    }
}
