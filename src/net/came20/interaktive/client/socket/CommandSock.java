package net.came20.interaktive.client.socket;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.AnnouncementRoutable;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.Parameter;
import net.came20.interaktive.server.Announce;
import org.zeromq.ZMQ;

/**
 * Created by cameronearle on 9/23/16.
 */
public class CommandSock {

    private static ZMQ.Context context;
    private static ZMQ.Socket socket;
    static LogHelper logger = new LogHelper(CommandSock.class);
    public static void init(ZMQ.Context context, String address) {
        CommandSock.context = context;
        socket = context.socket(ZMQ.REQ);
        socket.setReceiveTimeOut(120000);
        socket.bind("tcp://" + address);
        logger.log("Connected to command socket");
    }

    public static CommandRoutable send(CommandRoutable message) {
        socket.send(message.toString());
        logger.log("Sent Command: [" + message.getCommand().toString() + "]");
        return ((CommandRoutable) new XStream(new DomDriver()).fromXML(new String(socket.recv())));
    }

    public static boolean poll(String address) {
        ZMQ.Context pollcontext = ZMQ.context(1);
        ZMQ.Socket pollsock = pollcontext.socket(ZMQ.REQ);
        pollsock.setReceiveTimeOut(3000);
        try {
            logger.log("Connecting to poll socket");
            pollsock.connect("tcp://" + address);
            logger.log("Connected to poll socket");
        } catch (Exception e) {
            logger.log("Failed to connect to poll socket");
            return false;
        }
        pollsock.send(new CommandRoutable(Commands.PING_REQUEST, new Parameter()).toString());
        logger.log("Sent poll");
        try {
            if (pollsock.recv() != null) {
                logger.log("Successfully polled server");
                return true;
            } else {
                logger.log("Server not reached");
                return false;
            }
        } catch (Exception e) {
            logger.log("Exception while polling server", LogHelper.Levels.ERROR);
            return false;
        }
    }
}
