package net.came20.interaktive.server;


import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.AnnouncementRoutable;
import net.came20.interaktive.command.Announcements;
import net.came20.interaktive.server.auth.Auth;
import net.came20.interaktive.server.auth.Token;
import net.came20.interaktive.server.auth.User;

import java.util.ArrayList;

/**
 * Created by cameronearle on 8/14/16.
 */
public class Heartbeat implements Runnable {
    LogHelper logger = new LogHelper(this.getClass());
    long currentTime;
    @Override
    public void run() {
        while(true) {
            ArrayList<String> args = new ArrayList<String>();
            args.add(Token.nextToken());
            Announce.announce(new AnnouncementRoutable(Announcements.SERVER_HEARTBEAT, args));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {}
        }
    }   

}
