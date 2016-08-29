package net.came20.interaktive.command.parameter;

/**
 * Created by cameron on 8/28/2016.
 */
public class ParameterLogoutRequest extends Parameter {
    private String token;

    public ParameterLogoutRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
