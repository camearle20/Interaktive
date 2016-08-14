package net.came20.interaktive.server;


import net.came20.interaktive.LogHelper;
import org.zeromq.ZMQ;

/**
 ** Created by cameron on 8/9/2016.
 */
public class Server {

    LogHelper logger = new LogHelper(this.getClass());

    ZMQ.Context context = ZMQ.context(1);
    ZMQ.Socket commandSock = context.socket(ZMQ.ROUTER);
    ZMQ.Socket workerSock = context.socket(ZMQ.DEALER);
    ZMQ.Socket announceSock = context.socket(ZMQ.PUB);

    public Server(int port) {
        commandSock.bind("tcp://*:" + port);
        workerSock.bind("inproc://interaktiveworkers");
        announceSock.bind("tcp://*:" + (port + 1));
        logger.log("All sockets connected");

        for (int worker_nbr = 0; worker_nbr < 5; worker_nbr++) {
            Thread worker = new ClientListener(context);
            worker.setName("Listener " + worker_nbr);
            worker.start();
            logger.log("Started Listener " + worker_nbr);
        }
        logger.log("Bridging listeners to clients, bye!");

        ZMQ.proxy(commandSock, workerSock, null);

        commandSock.close();
        workerSock.close();
        context.term();

    }
}
