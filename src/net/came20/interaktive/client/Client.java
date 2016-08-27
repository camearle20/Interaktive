package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.came20.interaktive.client.cli.CLI;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.Parameter;
import net.came20.interaktive.command.parameter.ParameterCheckinConfirm;
import net.came20.interaktive.command.parameter.ParameterCheckinRequest;
import net.came20.interaktive.command.parameter.ParameterLoginAccept;
import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.parameter.ParameterLoginReject;
import net.came20.interaktive.command.parameter.ParameterLoginRequest;

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

    public static CommandRoutable sendCommand(CommandRoutable command) {
        commandSock.send(command.toString());
        return (CommandRoutable) xstream.fromXML(new String(commandSock.recv()));
    }

    public static void returnCommand(CommandRoutable command) {
        interpreter.display(command);
    }

    public Client(int port, String address, boolean showGui) {
        commandSock.connect("tcp://" + address + ":" + port);
        announceSock.connect("tcp://" + address + ":" + (port + 1));

        
        Parameter parameter;

        CommandRoutable login = new CommandRoutable(Commands.LOGIN_REQUEST, new ParameterLoginRequest("tom", "foolery"));
        String loginText = xstream.toXML(login);
        commandSock.send(loginText);
        logger.log("Logging in");
        String returnlogintext = new String(commandSock.recv());
        CommandRoutable returnlogin = (CommandRoutable) xstream.fromXML(returnlogintext);
        parameter = returnlogin.getParameter();
        String logintoken = null;
        switch (returnlogin.getCommand()) {
            case LOGIN_ACCEPT:
                logintoken = ((ParameterLoginAccept) parameter).getToken();
                break;
            case LOGIN_REJECT:
                logger.log("Login rejected for reason: " + ((ParameterLoginReject) returnlogin.getParameter()).getReason());
                break;
        }
        if (showGui) {
            //interpreter = new GUI();
            interpreter = null;
        } else {
            interpreter = new CLI(logintoken);
        }

        Thread cmdThread = new Thread(interpreter, "Interpreter");
        cmdThread.start();
    }
}
