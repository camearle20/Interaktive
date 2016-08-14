package net.came20.interaktive.server.auth;

import java.util.ArrayList;

/**
 * Created by cameronearle on 8/14/16.
 */
public class Auth {
    private static ArrayList<User> authList = new ArrayList<User>();

    public static boolean checkAuth(String token) {
        for (User user : authList) {
            if (user.getToken() == token) {
                return true;
            }
        }
        return false;
    }
    public static boolean removeAuth(String token) {
        for (User user : authList) {
            if (user.getToken() == token) {
                authList.remove(user);
                return true;
            }
        }
        return false;
    }
    public static boolean addAuth(String token) {
        try {
            authList.add(new User(token, System.currentTimeMillis() / 1000L));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected static ArrayList<User> getAuthList() {
        return authList;
    }
}
