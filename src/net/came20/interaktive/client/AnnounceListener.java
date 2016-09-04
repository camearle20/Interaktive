package net.came20.interaktive.client;

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
public class AnnounceListener implements Runnable {
    static LogHelper logger = new LogHelper(AnnounceListener.class);
    private static ZMQ.Socket socket;

    public static void init(ZMQ.Context context, String address, int port) {
        socket = context.socket(ZMQ.SUB);
        socket.connect("tcp://" + address + ":" + port);
        socket.subscribe("".getBytes());
        logger.log("Connected to announce socket");
    }

    @Override
    public void run() {
        while (true) {
            String data = new String(socket.recv());
            AnnouncementRoutable announcement = new AnnouncementRoutable(data);
            logger.log("Got announcement: [" + announcement.getAnnouncement().toString() + "] with args [" + String.join(",", announcement.getArgs()) + "]");
            AnnouncementRouter.route(announcement);
            try {
                Thread.sleep(10);
            } catch (Exception e) {}
        }
    }
}
