package net.came20.interaktive.client;

import net.came20.interaktive.LogHelper;
import org.zeromq.ZMQ;

public class CLI extends Interpreter {

    private ZMQ.Context context;
    private int port;

    public CLI(ZMQ.Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        //First we completely disable the logger, to prevent it from outputting to the stdout
        new LogHelper(this.getClass()).log("CLI Starting, disabling logger!");
        //LogHelper.setEnabled(null, false);

        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        socket.connect("inproc://interaktiveinterpreter");

    }
}
