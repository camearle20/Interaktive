package net.came20.interaktive.command.parameter;

/**
 * came20's Interaktive
 * Copyright (C) 2016 came20 (http://came20.net)
 * License information can be found in the "LICENSE" file,
 * found inside this package.  Should a "LICENSE" file not
 * be present, it is most likely not an official package,
 * released by myself (came20), and should be used with caution
 */
public class ParameterLoginAccept extends Parameter {
    private String token;

    public ParameterLoginAccept(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
