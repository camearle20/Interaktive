package net.came20.interaktive.server.auth;


import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.AnnouncementRoutable;
import net.came20.interaktive.command.Announcements;
import net.came20.interaktive.server.Announce;

import java.util.ArrayList;

/**
 * Created by cameronearle on 8/14/16.
 */
public class InactiveLogout implements Runnable {
    LogHelper logger = new LogHelper(this.getClass());
    long currentTime;
    @Override
    public void run() {
        while(true) {
            currentTime = System.currentTimeMillis() / 1000L;
            for (User user : Auth.getAuthList()) {
                logger.log("Time for user " + user.getUsername() + " is " + (currentTime - user.getTime()), LogHelper.Levels.DEBUG);
                //if (currentTime - user.getTime() >= 180) {
                if (currentTime - user.getTime() >= 10) {
                    Auth.removeAuth(user.getToken());
                    ArrayList<String> annArgs = new ArrayList<String>();
                    annArgs.add(user.getToken());
                    Announce.announce(new AnnouncementRoutable(Announcements.CLIENT_KICKED, annArgs));
                    logger.log("Removed user [" + user.getUsername() + "] with token [" + user.getToken() + "] for inactivity");
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }   

}
