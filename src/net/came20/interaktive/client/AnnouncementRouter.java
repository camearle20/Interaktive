package net.came20.interaktive.client;


import net.came20.interaktive.command.AnnouncementRoutable;
import net.came20.interaktive.command.Announcements;


import java.util.ArrayList;

/**
 * Created by cameron on 8/9/2016.
 */
public class AnnouncementRouter {
    static ArrayList<String> args;
    public static void route(AnnouncementRoutable announcement) {
        args = announcement.getArgs();
        switch (announcement.getAnnouncement()) {
            case CLIENT_KICKED:
                if (args.get(0).equals(Auth.getToken())) {
                    Client.getInterpreter().display("You have been kicked from the server for reason: " + args.get(1));
                    System.exit(143);
                }
                break;
            default:
                break;
        }
    }
}
