package net.came20.interaktive.client.cli;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.LogHelper;
import net.came20.interaktive.client.Client;
import net.came20.interaktive.client.Interpreter;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.ParameterCheckinRequest;
import net.came20.interaktive.command.parameter.ParameterLoginRequest;
import org.zeromq.ZMQ;

public class CLI extends Interpreter {

    private String token;

    public CLI(String token) {
        this.token = token;
    }

    @Override
    public void run() {
        LogHelper logger = new LogHelper(this.getClass());
        //First we completely disable the logger, to prevent it from outputting to the stdout
        logger.log("CLI Starting, disabling logger!");
        //LogHelper.setEnabled(null, false);
        CommandRoutable command = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), token);
        CommandRoutable response = Client.sendCommand(command);
        logger.log("Got response");
        System.out.println(response.toString());
    }
}
