package net.came20.interaktive.server;

import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.LogHelper;
import org.zeromq.ZMQ;
import com.thoughtworks.xstream.XStream;

public class ClientListener extends Thread {
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
            logger.log("Got request, unwrapping.");
            logger.log("\n***RECEIVED DATA***\n" + data + "\n***END RECEIVED DATA***", LogHelper.Levels.DEBUG);
            CommandRoutable command = (CommandRoutable) new XStream(new DomDriver()).fromXML(data);
            logger.log("Command unwrapped");
            CommandRoutable response = CommandRouter.route(command);
            logger.log("Sending response");
            socket.send(response.toString());
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {}
        }
        
    }
}
