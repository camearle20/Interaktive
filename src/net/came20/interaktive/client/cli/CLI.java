package net.came20.interaktive.client.cli;

import net.came20.interaktive.LogHelper;
import net.came20.interaktive.client.Auth;
import net.came20.interaktive.client.Client;
import net.came20.interaktive.client.Interpreter;
import net.came20.interaktive.command.CommandRoutable;
import net.came20.interaktive.command.Commands;
import net.came20.interaktive.command.parameter.*;
import java.util.Scanner;

public class CLI extends Interpreter {

    LogHelper logger = new LogHelper(this.getClass());
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        //LogHelper.setEnabled(null, false);
        main:
        while (true) {
            String username = null;
            String password = null;
            login:
            while (true) {
                System.out.print("Username: ");
                username = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();

                CommandRoutable login = new CommandRoutable(Commands.LOGIN_REQUEST, new ParameterLoginRequest(username, password));
                CommandRoutable response = Client.sendCommand(login);
                switch (response.getCommand()) {
                    case LOGIN_ACCEPT:
                        Auth.setToken(((ParameterLoginAccept) response.getParameter()).getToken());
                        break login;
                    case LOGIN_REJECT:
                        System.out.println("Login rejected for reason: " + ((ParameterLoginReject) response.getParameter()).getReason());
                        break;
                }
            }

            command:
            while (true) {
                CommandRoutable command = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), Auth.getToken());
                CommandRoutable response = Client.sendCommand(command);
                logger.log("Got response");
                System.out.println(response.toString());
                logger.log("Starting kicker test");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                }
                CommandRoutable command3 = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), Auth.getToken());
                CommandRoutable response3 = Client.sendCommand(command3);
                logger.log("Got response");
                System.out.println(response3.toString());
                logger.log("Kicker test 1 done, kicker test 2 starting");
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                }
                CommandRoutable command2 = new CommandRoutable(Commands.CHECKIN_REQUEST, new ParameterCheckinRequest("Jorge", "Gonzoles", "J", "Cuba", "DL564", "3D", "12345"), Auth.getToken());
                CommandRoutable response2 = Client.sendCommand(command2);
                logger.log("Got response");
                System.out.println(response2.toString());
                logger.log("Kicker test ended");
                break command;
            }


        }
    }
}
