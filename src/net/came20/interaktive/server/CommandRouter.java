package net.came20.interaktive.server;


import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.Parameter;
import net.came20.interaktive.command.parameter.ParameterAuthFail;
import net.came20.interaktive.command.parameter.ParameterCheckinRequest;
import net.came20.interaktive.command.parameter.ParameterLoginRequest;
import net.came20.interaktive.server.actions.ActionCheckinConfirm;
import net.came20.interaktive.server.actions.ActionLoginAuth;
import net.came20.interaktive.server.auth.Auth;

/**
 * Created by cameron on 8/9/2016.
 */
public class CommandRouter {
    static CommandRoutable response;
    static Parameter parameter;
    public static CommandRoutable route(CommandRoutable command) {
        parameter = command.getParameter();
        if (command.getCommand() == Commands.LOGIN_REQUEST) {
            response = ActionLoginAuth.execute((ParameterLoginRequest) parameter);
            return response;
        } else if (command.getCommand() == Commands.PING_REQUEST) {
            response = new CommandRoutable(Commands.PING_RESPONSE, new Parameter());
            return response;
        }
        if (!Auth.checkAuth(command.getToken())) {
            return new CommandRoutable(Commands.AUTH_FAIL, new ParameterAuthFail());
        } else {
            Auth.updateTime(command.getToken());
        }
        switch (command.getCommand()) {
            case CHECKIN_REQUEST:
                response = ActionCheckinConfirm.execute((ParameterCheckinRequest) parameter);
                break;
        }
        return response;
    }
}
