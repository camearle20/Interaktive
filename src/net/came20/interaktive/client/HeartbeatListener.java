package net.came20.interaktive.client;

import javax.swing.*;

/**
 * Created by cameronearle on 9/26/16.
 */
public class HeartbeatListener implements Runnable {
    static long currentTime = 0;
    static long time;
    static int count;

    public static void updateTime() {
        time = System.currentTimeMillis() / 1000L;
        count = 0;
    }


    @Override
    public void run() {
        currentTime = System.currentTimeMillis() / 1000L;
        time = currentTime;
        while (true) {
            currentTime = System.currentTimeMillis() / 1000L;
            System.out.println(currentTime - time);
            if ((currentTime - time) > 60) {
                count ++;
                if (count >= 5) {
                    Client.getInterpreter().display("No heartbeat recieved for 5 minutes, exiting!");
                    System.exit(123);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Client.getInterpreter().display("No heartbeat recieved from server in 60 seconds");
                    }
                });
                time = currentTime;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
}
