package net.came20.interaktive;


import net.came20.interaktive.client.Client;
import net.came20.interaktive.server.Server;

/**
 * Created by cameron on 8/9/2016.
 */
public class Interaktive {
    public static void main(String[] args) {
        String startArg = "client";
        try {
            startArg = args[0].toLowerCase();
        } catch (Exception e) {}
        if (startArg.contains("client")) {
            new Client();
        } else if (startArg.contains("server")) {
            new Server();
        }
    }
}
