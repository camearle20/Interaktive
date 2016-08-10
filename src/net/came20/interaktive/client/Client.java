package net.came20.interaktive.client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.Command.CommandRoutable;
import net.came20.interaktive.Command.Commands;
import net.came20.interaktive.Command.Parameter.ParameterCheckinConfirm;
import net.came20.interaktive.Command.Parameter.ParameterCheckinRequest;
import net.came20.interaktive.LogHelper;
import org.zeromq.ZMQ;

/**
 * Created by cameron on 8/9/2016.
 */
public class Client {
    LogHelper logger = new LogHelper(this.getClass());

    XStream xstream = new XStream(new DomDriver());

    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket commandSock = context.socket(ZMQ.REQ);
    ZMQ.Socket announceSock = context.socket(ZMQ.SUB);
    public Client(int port, String address) {
        commandSock.connect("tcp://" + address + ":" + port);
        announceSock.connect("tcp://" + address + ":" + (port + 1));
        CommandRoutable command = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"));
        String commandtext = xstream.toXML(command);
        commandSock.send(commandtext);
        logger.log("Sent data");
        byte[] returnedraw = commandSock.recv();
        String returned = new String(returnedraw);
        CommandRoutable commandreturned = (CommandRoutable) xstream.fromXML(returned);
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
