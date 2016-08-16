package net.came20.interaktive.server.actions;


import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.ParameterLoginAccept;
import net.came20.interaktive.command.parameter.ParameterLoginReject;
import net.came20.interaktive.command.parameter.ParameterLoginRequest;
import net.came20.interaktive.server.auth.Auth;
import net.came20.interaktive.server.auth.Token;
import net.came20.interaktive.LogHelper;

/**
 * Created by cameronearle on 8/14/16.
 */
public class ActionLoginAuth {
    static LogHelper logger = new LogHelper(ActionLoginAuth.class);
    public static CommandRoutable execute(Commands command, ParameterLoginRequest parameter) {
        boolean valid_username = true; //debug
        boolean valid_password = true; //replace with sql queries

        if (parameter.getUsername().equals("tom") && parameter.getPassword().equals("foolery")) {
            logger.log("Authenticating user [" + parameter.getUsername() + "]");
            String token = Token.nextToken();
            Auth.addAuth(token);
            logger.log("Authenticated user [" + parameter.getUsername() + "] with token [" + token + "]");
            return new CommandRoutable(Commands.LOGIN_ACCEPT, new ParameterLoginAccept(token));
        } else {
            return new CommandRoutable(Commands.LOGIN_REJECT, new ParameterLoginReject("Invalid Login"));
        }
    }
}
