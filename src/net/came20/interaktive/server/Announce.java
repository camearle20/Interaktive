package net.came20.interaktive.server;

import org.zeromq.ZMQ;

/**
 * came20's Interaktive
 * Copyright (C) 2016 came20 (http://came20.net)
 * License information can be found in the "LICENSE" file,
 * found inside this package.  Should a "LICENSE" file not
 * be present, it is most likely not an official package,
 * released by myself (came20), and should be used with caution
 */
public class Announce {
    private static ZMQ.Context context;
    private static ZMQ.Socket socket;
    public static void init(ZMQ.Context context, int port) {
        Announce.context = context;
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:" + port);
    }

    public static void announce(String message) {
        socket.send(message);
    }
}
