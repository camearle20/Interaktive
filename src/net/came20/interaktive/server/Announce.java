package net.came20.interaktive.server;

import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.AnnouncementRoutable;
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
    static LogHelper logger = new LogHelper(Announce.class);
    public static void init(ZMQ.Context context, int port) {
        Announce.context = context;
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:" + port);
        logger.log("Connected to announce socket");
    }

    public static void announce(AnnouncementRoutable message) {
        socket.send(message.toString());
        logger.log("Announced: [" + message.toString() + "]");
    }
}
