package net.came20.interaktive.server;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.Command.CommandRoutable;
import net.came20.interaktive.Command.Commands;
import net.came20.interaktive.Command.Parameter.ParameterCheckinConfirm;
import net.came20.interaktive.Command.Parameter.ParameterCheckinRequest;
import org.zeromq.ZMQ;

import java.util.Random;

/**
 * Created by cameron on 8/9/2016.
 */
public class Server {

    XStream xstream = new XStream(new DomDriver());

    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket commandSock = context.socket(ZMQ.REP);
    ZMQ.Socket announceSock = context.socket(ZMQ.PUB);

    public Server(int port) {
        commandSock.bind("tcp://*:" + port);
        announceSock.bind("tcp://*:" + (port + 1));
        System.out.println("Socket connected");

        while (true) {
            byte[] dataraw = commandSock.recv();
            String data = new String(dataraw);
            System.out.println(data);
            System.out.println("Got data");
            CommandRoutable returned = (CommandRoutable) xstream.fromXML(data);
            if (returned.getCommand() == Commands.CHECKIN_REQUEST) {
                ParameterCheckinRequest parameter = (ParameterCheckinRequest) returned.getParameter();
                CommandRoutable returncommand = new CommandRoutable(Commands.CHECKIN_CONFIRM, new ParameterCheckinConfirm(parameter.getFirstName(),
                                                                    parameter.getLastName(), parameter.getMiddleInitial(), parameter.getFinalDestination(), parameter.getFlightNumber(),
                                                                    parameter.getSeatNumber(), parameter.getFfNumber()));
                String returntext = xstream.toXML(returncommand);
                commandSock.send(returntext);
            }
        }

    }
}
