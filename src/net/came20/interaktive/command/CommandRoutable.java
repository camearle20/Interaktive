package net.came20.interaktive.command;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.command.parameter.Parameter;

/**
 * Created by cameron on 8/9/2016.
 */
public class CommandRoutable {
    private Commands command;
    private Parameter parameter;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CommandRoutable(Commands command, Parameter parameter) {
        this.command = command;

        this.parameter = parameter;
        this.token = token;
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

    @Override
    public String toString() {
        return new XStream(new DomDriver()).toXML(this);
    }
}
