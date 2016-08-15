package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
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

        CommandRoutable login = new CommandRoutable(Commands.LOGIN_REQUEST, new ParameterLoginRequest("thomas", "foolery"));
        String loginText = xstream.toXML(login);
        commandSock.send(loginText);
        logger.log("Logging in");
        String returnlogintext = new String(commandSock.recv());
        CommandRoutable returnlogin = (CommandRoutable) xstream.fromXML(returnlogintext);
        ParameterLoginAccept loginparameter = (ParameterLoginAccept) returnlogin.getParameter();

        String logintoken = loginparameter.getToken();

        CommandRoutable command = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), logintoken);
        String commandtext = xstream.toXML(command);
        commandSock.send(commandtext);
        logger.log("Sent data");
        byte[] returnedraw = commandSock.recv();
        String returned = new String(returnedraw);
        CommandRoutable commandreturned = (CommandRoutable) xstream.fromXML(returned);
        System.out.println(commandreturned.toString());
        if (commandreturned.getCommand() == Commands.CHECKIN_CONFIRM) {
            ParameterCheckinConfirm parameter = (ParameterCheckinConfirm) commandreturned.getParameter();
            System.out.println(parameter.getFirstName());
            System.out.println(parameter.getLastName());
            System.out.println(parameter.getMiddleInitial());
            System.out.println(parameter.getFinalDestination());
            System.out.println(parameter.getFlightNumber());
            System.out.println(parameter.getSeatNumber());
            System.out.println(parameter.getFfNumber());
            System.out.println("It worked!");
        }
    }
}
