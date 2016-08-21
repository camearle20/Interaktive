package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.came20.interaktive.client.gui.LoginForm;
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
import org.zeromq.ZProxy;

/**
 * Created by cameron on 8/9/2016.
 */
public class Client {
    LogHelper logger = new LogHelper(this.getClass());

    XStream xstream = new XStream(new DomDriver());

    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket commandSock = context.socket(ZMQ.REQ);
    ZMQ.Socket announceSock = context.socket(ZMQ.SUB);
    public Client(int port, String address, boolean showGui) {
        commandSock.connect("tcp://" + address + ":" + port);
        announceSock.connect("tcp://" + address + ":" + (port + 1));
        Thread cmdThread;
        if (showGui) {
            //cmdThread = new Thread(GUI, "Interpreter");
            cmdThread = null;
        } else {
            cmdThread = new Thread(new CLI(), "Interpreter");
        }
        
        cmdThread.start();
        
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
        



        CommandRoutable command = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), logintoken);
        String commandtext = xstream.toXML(command);
        commandSock.send(commandtext);
        logger.log("Sent data");
        byte[] returnedraw = commandSock.recv();
        String returned = new String(returnedraw);
        CommandRoutable commandreturned = (CommandRoutable) xstream.fromXML(returned);
        System.out.println(commandreturned.toString());
        if (commandreturned.getCommand() == Commands.CHECKIN_CONFIRM) {
            parameter = commandreturned.getParameter();
            System.out.println(((ParameterCheckinConfirm) parameter).getFirstName());
            System.out.println(((ParameterCheckinConfirm) parameter).getLastName());
            System.out.println(((ParameterCheckinConfirm) parameter).getMiddleInitial());
            System.out.println(((ParameterCheckinConfirm) parameter).getFinalDestination());
            System.out.println(((ParameterCheckinConfirm) parameter).getFlightNumber());
            System.out.println(((ParameterCheckinConfirm) parameter).getSeatNumber());
            System.out.println(((ParameterCheckinConfirm) parameter).getFfNumber());
            System.out.println("It worked!");
        }
    }
}
