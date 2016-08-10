package net.came20.interaktive;


import net.came20.interaktive.client.Client;
import net.came20.interaktive.server.Server;

/**
 * Created by cameron on 8/9/2016.
 */
public class Interaktive {
    static LogHelper logger = new LogHelper(Interaktive.class);
    static final int port = 5260; //+1 for the announce socket
    static final String address = "localhost";
    public static void main(String[] args) {
        logger.log("Starting!");
        String startArg = "none";
        try {
            startArg = args[0].toLowerCase();
        } catch (Exception e) {}
        switch (startArg) {
            case "client":
                logger.log("Got client argument, starting client");
                new Client(port, address);
                break;
            case "server":
                logger.log("Got server argument, starting server");
                new Server(port);
                break;
            default:
                logger.log("Got invalid argument, defaulting!");
                new Client(port, address);
                break;
        }
    }
}
