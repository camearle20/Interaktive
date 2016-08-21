package net.came20.interaktive.server;

import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.LogHelper;
import net.came20.interaktive.server.auth.Auth;
import org.zeromq.ZMQ;
import com.thoughtworks.xstream.XStream;

public class ClientListener implements Runnable {
    private LogHelper logger = new LogHelper(this.getClass());
    private ZMQ.Context context;
    private int port;

    public ClientListener(ZMQ.Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.connect("inproc://interaktiveworkers");

        while(true) {
            String data = new String(socket.recv());
            logger.log("Got command", LogHelper.Levels.DEBUG);
            logger.log("\n***RECEIVED DATA***\n" + data + "\n***END RECEIVED DATA***", LogHelper.Levels.DEBUG);

            CommandRoutable command = (CommandRoutable) new XStream(new DomDriver()).fromXML(data);
            logger.log("Got command [" + command.getCommand() + "] from user [" + Auth.getUsername(command.getToken()) + "] with token [" + command.getToken() + "]");

            logger.log("Routing command", LogHelper.Levels.DEBUG);
            CommandRoutable response = CommandRouter.route(command);
            logger.log("Built response", LogHelper.Levels.DEBUG);
            logger.log("\n***RESPONSE DATA***\n" + response.toString() + "\n***END RESPONSE DATA***", LogHelper.Levels.DEBUG);

            logger.log("Sending response [" + response.getCommand() + "] to user [" + Auth.getUsername(command.getToken()) + "] with token [" + command.getToken() + "]");
            socket.send(response.toString());

            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {}
        }
        
    }
}
