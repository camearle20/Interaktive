package net.came20.interaktive;


import net.came20.interaktive.client.Client;
import net.came20.interaktive.server.Server;
import org.zeromq.ZMQ;

/**
 * Created by cameron on 8/9/2016.
 */
public class Interaktive {
    static LogHelper logger = new LogHelper(Interaktive.class);
    static final int port = 5260; //+1 for the announce socket
    static final String address = "localhost";
    public static void main(String[] args) {
        logger.log("Starting!");

        ZMQ.Context context = ZMQ.context(1);

        logger.log("Created context");
        String startArg = "none";
        String guiArg = "";
        //String guiArg = "";
        try {
            startArg = args[0].toLowerCase();
        } catch (Exception e) {}
        //try {
        //    guiArg = args[1].toLowerCase();
        //} catch (Exception e) {}
        switch (startArg) {
            case "client":
                logger.log("Got client argument, starting client");
                new Client(context, !guiArg.equals("nogui"));
                break;
            case "server":
                logger.log("Got server argument, starting server");
                new Server(context, port);
                break;
            default:
                logger.log("Got invalid argument, defaulting!");
                new Client(context, !guiArg.equals("nogui"));
                break;
        }
        context.term();
        logger.log("Exiting!");
    }
}
