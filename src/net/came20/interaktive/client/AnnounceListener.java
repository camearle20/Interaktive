package net.came20.interaktive.client;

import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.AnnouncementRoutable;
import org.zeromq.ZMQ;

/**
 * Created by cameronearle on 9/26/16.
 */
public class AnnounceListener implements Runnable {


    private String address;
    ZMQ.Context context;
    ZMQ.Socket socket;

    LogHelper logger = new LogHelper(this.getClass());

    public AnnounceListener(ZMQ.Context context, String address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public void run() {
        socket = context.socket(ZMQ.SUB);
        socket.subscribe("".getBytes());
        socket.connect("tcp://" + address);
        main: while (!Thread.currentThread().isInterrupted()) {
            if (Thread.currentThread().isInterrupted()) {
                break main;
            }
            try {
                String data = new String(socket.recv());
                AnnouncementRoutable announcement = new AnnouncementRoutable(data);
                logger.log("Got announcement: [" + announcement.getAnnouncement().toString() + "] with args [" + String.join(",", announcement.getArgs()) + "]");
                AnnouncementRouter.route(announcement);
            } catch (Exception ignored) {
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
                break main;
            }
        }
        logger.log("Closing Announce Listener");
    }
}
