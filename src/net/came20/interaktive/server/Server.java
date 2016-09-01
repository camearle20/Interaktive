package net.came20.interaktive.server;


import net.came20.interaktive.LogHelper;
import net.came20.interaktive.server.auth.InactiveLogout;

import org.zeromq.ZMQ;

/**
 ** Created by cameron on 8/9/2016.
 */
public class Server {

    LogHelper logger = new LogHelper(this.getClass());

    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket commandSock = context.socket(ZMQ.ROUTER);
    ZMQ.Socket workerSock = context.socket(ZMQ.DEALER);

    public Server(int port) {
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
        
        logger.log("Bridging listeners to clients, bye!");

        ZMQ.proxy(commandSock, workerSock, null);

        commandSock.close();
        workerSock.close();
        context.term();

    }
}
