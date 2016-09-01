package net.came20.interaktive.server.auth;

import java.util.ArrayList;

/**
 * Created by cameronearle on 8/14/16.
 */
public class Auth {
    private static ArrayList<User> authList = new ArrayList<User>();

    public static boolean checkAuth(String token) {
        for (User user : authList) {
            if (user.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }
    public static boolean removeAuth(String token) {
        for (User user : authList) {
            if (user.getToken().equals(token)) {
                authList.remove(user);
                return true;
            }
        }
        return false;
    }
    public static boolean addAuth(String token, String username) {
        try {
            authList.add(new User(token, username, System.currentTimeMillis() / 1000L));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void updateTime(String token) {
        if (token != null) {
            long currentTime = System.currentTimeMillis() / 1000L;
            User user = getUser(token);
            user.updateTime(currentTime);
        }
    }
    
    
    private static User getUser(String token) {
        for (User user : authList) {
            if (user.getToken().equals(token)) {
                return user;
            }
        }
        return null;
    }

    public static String getUsername(String token) {
        for (User user : authList) {
            if (user.getToken().equals(token)) {
                return user.getUsername();
            }
        }
        return null;
    }
    protected static ArrayList<User> getAuthList() {
        return authList;
    }
}
