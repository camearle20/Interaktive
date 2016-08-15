package net.came20.interaktive.server.auth;


import java.security.SecureRandom;
import java.math.BigInteger;


/**
 * came20's Interaktive
 * Copyright (C) 2016 came20 (http://came20.net)
 * License information can be found in the "LICENSE" file,
 * found inside this package.  Should a "LICENSE" file not
 * be present, it is most likely not an official package,
 * released by myself (came20), and should be used with caution
 */
public final class Token {
    private static SecureRandom random = new SecureRandom();
    public static String nextToken() {
        return new BigInteger(130, random).toString(32);
    }
}

