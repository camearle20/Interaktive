package net.came20.interaktive.command.parameter;

/**
 * Created by cameron on 8/28/2016.
 */
public class ParameterLogoutReject extends Parameter {
    private String reason;

    public ParameterLogoutReject(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
