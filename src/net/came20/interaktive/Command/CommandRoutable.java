package net.came20.interaktive.Command;

import net.came20.interaktive.Command.Parameter.Parameter;

/**
 * Created by cameron on 8/9/2016.
 */
public class CommandRoutable {
    private Commands command;
    private Parameter parameter;

    public CommandRoutable(Commands command, Parameter parameter) {
        this.command = command;
        this.parameter = parameter;
    }

    public Commands getCommand() {
        return command;
    }

    public void setCommand(Commands command) {
        this.command = command;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
