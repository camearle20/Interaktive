package net.came20.interaktive.server.actions;

import net.came20.interaktive.LogHelper;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.Parameter;
import net.came20.interaktive.command.parameter.ParameterCheckinConfirm;
import net.came20.interaktive.command.parameter.ParameterCheckinRequest;

/**
 * Created by cameron on 8/14/2016.
 */
public class ActionCheckinConfirm {
    static LogHelper logger = new LogHelper(ActionCheckinConfirm.class);
    public static CommandRoutable execute(ParameterCheckinRequest parameter) {
        logger.log("Building confirm response");
        return new CommandRoutable(Commands.CHECKIN_CONFIRM, new ParameterCheckinConfirm(parameter.getFirstName(),
                parameter.getLastName(), parameter.getMiddleInitial(), parameter.getFinalDestination(),
                parameter.getFlightNumber(), parameter.getSeatNumber(), parameter.getFfNumber()));

    }
}
