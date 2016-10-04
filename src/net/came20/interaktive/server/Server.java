package net.came20.interaktive.server;


import net.came20.interaktive.LogHelper;
import net.came20.interaktive.server.auth.InactiveLogout;

import org.zeromq.ZMQ;

/**
 ** Created by cameron on 8/9/2016.
 */
public class Server {

    LogHelper logger = new LogHelper(this.getClass());

    ZMQ.Context context;
    ZMQ.Socket commandSock;
    ZMQ.Socket workerSock;

    public Server(ZMQ.Context context, int port) {
        this.context = context;

        commandSock = this.context.socket(ZMQ.ROUTER);
        workerSock = this.context.socket(ZMQ.DEALER);

        commandSock.bind("tcp://*:" + port);
        workerSock.bind("inproc://interaktiveworkers");
        Announce.init(context, (port + 1));
        logger.log("All sockets connected");

        for (int worker_nbr = 0; worker_nbr < 5; worker_nbr++) {
            Thread worker = new Thread(new ClientListener(context), "Listener " + worker_nbr);
            worker.start();
            logger.log("Started Listener " + worker_nbr);
        }
        
        Thread inactiveLogout = new Thread(new InactiveLogout(), "Inactivity Kicker");
        inactiveLogout.start();
        logger.log("Started the Inactivity Kicker");

        Thread heartbeat = new Thread(new Heartbeat(), "Heartbeat");
        heartbeat.start();
        logger.log("Started heartbeat");
        
        logger.log("Bridging listeners to clients, bye!");

        ZMQ.proxy(commandSock, workerSock, null);

        commandSock.close();
        workerSock.close();
        context.term();

    }
}
