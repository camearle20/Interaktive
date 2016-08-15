package net.came20.interaktive.server.auth;


import net.came20.interaktive.LogHelper;

/**
 * Created by cameronearle on 8/14/16.
 */
public class InactiveLogout extends Thread {
    long currentTime;
    @Override
    public void run() {
        currentTime = System.currentTimeMillis() / 1000L;
        for (User user : Auth.getAuthList()) {
            if (currentTime - user.getTime() >= 600000) {
                Auth.removeAuth(user.getToken());
                new LogHelper(this.getClass()).log("Removed user with token " + user.getToken() + " for inactivity");
            }
        }
    }
}
