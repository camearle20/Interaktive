package net.came20.interaktive.server;


import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.ParameterCheckinRequest;
import net.came20.interaktive.command.parameter.ParameterLoginRequest;
import net.came20.interaktive.server.actions.ActionCheckinConfirm;

/**
 * Created by cameron on 8/9/2016.
 */
public class CommandRouter {
    static CommandRoutable response;
    public static CommandRoutable route(CommandRoutable command) {
        switch (command.getCommand()) {
            //case LOGIN_REQUEST:
            //    ParameterLoginRequest parameter = (ParameterLoginRequest) command.getParameter();
            //    response = ActionLoginAuth.execute(Commands.LOGIN_ACCEPT, parameter);
            //    break;
            case CHECKIN_REQUEST:
                ParameterCheckinRequest parameter = (ParameterCheckinRequest) command.getParameter();
                response = ActionCheckinConfirm.execute(Commands.CHECKIN_REQUEST, parameter);
                break;
        }
        return response;
    }
}
