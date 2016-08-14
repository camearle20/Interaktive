package net.came20.interaktive.server.auth;


/**
 * Created by cameronearle on 8/14/16.
 */
public class InactiveLogout extends Thread {
    long currentTime;
    @Override
    public void run() {
        currentTime = System.currentTimeMillis() / 1000L;
        for (User user : Auth.getAuthList()) {
            if (currentTime - user.getTime() >= 600000);
        }
    }
}
