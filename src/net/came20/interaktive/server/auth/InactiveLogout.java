package net.came20.interaktive.server.auth;


import net.came20.interaktive.LogHelper;

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
                    logger.log("Removed user [" + user.getUsername() + "] with token [" + user.getToken() + "] for inactivity");
                    break;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
    }   

}
