package net.came20.interaktive.client.cli;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.came20.interaktive.LogHelper;
import net.came20.interaktive.client.Client;
import net.came20.interaktive.client.Interpreter;
import net.came20.interaktive.command.AnnouncementRoutable;
import net.came20.interaktive.command.Announcements;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.*;
import net.came20.interaktive.server.Announce;
import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends Interpreter {

    private String token;

    public CLI(String token) {
        this.token = token;
    }

    @Override
    public void run() {
        LogHelper logger = new LogHelper(this.getClass());
        Scanner scanner = new Scanner(System.in);
        //First we completely disable the logger, to prevent it from outputting to the stdout

        logger.log("Routable Announcement Test");
        ArrayList<String> annArgs = new ArrayList<String>();
        annArgs.add("hi");
        annArgs.add("bye");
        AnnouncementRoutable announcement = new AnnouncementRoutable(Announcements.CLIENT_CONNECT, annArgs);
        logger.log(announcement.toString());

        logger.log(new AnnouncementRoutable(announcement.toString()).getAnnouncement());
        logger.log(new AnnouncementRoutable(announcement.toString()).getArgs());
        logger.log("End Routable Announcement Test");
        logger.log("CLI Starting, disabling logger!");

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.println();
        System.out.print("Password: ");
        String password = scanner.nextLine();



        Parameter parameter;


        CommandRoutable login = new CommandRoutable(Commands.LOGIN_REQUEST, new ParameterLoginRequest(username, password));
        CommandRoutable loginResponse = Client.sendCommand(login);
        logger.log("Logging in");
        parameter = loginResponse.getParameter();
        switch (loginResponse.getCommand()) {
            case LOGIN_ACCEPT:
                token = ((ParameterLoginAccept) parameter).getToken();
                break;
            case LOGIN_REJECT:
                logger.log("Login rejected for reason: " + ((ParameterLoginReject) loginResponse.getParameter()).getReason());
                break;
        }
        //LogHelper.setEnabled(null, false);
        CommandRoutable command = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), token);
        CommandRoutable response = Client.sendCommand(command);
        logger.log("Got response");
        System.out.println(response.toString());
        //debug kicker testing
        logger.log("Starting kicker test");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {}
        CommandRoutable command3 = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), token);
        CommandRoutable response3 = Client.sendCommand(command);
        logger.log("Got response");
        System.out.println(response3.toString());
        logger.log("Kicker test 1 done, kicker test 2 starting");
        try{
            Thread.sleep(20000);
        } catch (InterruptedException e) {}
        CommandRoutable command2 = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), token);
        CommandRoutable response2 = Client.sendCommand(command);
        logger.log("Got response");
        System.out.println(response2.toString());
        logger.log("Kicker test ended");
    }

    @Override
    public String[] login() {
        return new String[0];
    }
}
