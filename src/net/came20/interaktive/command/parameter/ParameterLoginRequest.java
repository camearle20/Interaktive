package net.came20.interaktive.command.parameter;

/**
 * Created by cameronearle on 8/14/16.
 */
public class ParameterLoginRequest extends Parameter {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ParameterLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;


    }
}
