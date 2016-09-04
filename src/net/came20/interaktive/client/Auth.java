package net.came20.interaktive.client;

/**
 * came20's Interaktive
 * Copyright (C) 2016 came20 (http://came20.net)
 * License information can be found in the "LICENSE" file,
 * found inside this package.  Should a "LICENSE" file not
 * be present, it is most likely not an official package,
 * released by myself (came20), and should be used with caution
 */
public class Auth {
    private static String token;

    public static void setToken(String token) {
        Auth.token = token;
    }

    public static String getToken() {
        return token;
    }
}
