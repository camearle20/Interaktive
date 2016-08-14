package net.came20.interaktive.server.auth;

/**
 * Created by cameronearle on 8/14/16.
 */
public class User {
    private String token;
    private long time;

    public User(String token, long time) {
        this.token = token;
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public long getTime() {
        return time;
    }

    public boolean updateTime(long time) {
        if (time >= this.time) {
            this.time = time;
            return true;
        } else {
            return false;
        }
    }

}
