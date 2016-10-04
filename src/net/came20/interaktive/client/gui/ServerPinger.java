package net.came20.interaktive.client.gui;

import net.came20.interaktive.client.gui.dialog.ServerConnectDialog;
import net.came20.interaktive.client.socket.PollSock;


/**
 * Created by cameronearle on 9/23/16.
 */
public class ServerPinger implements Runnable {

    private String address;
    private ServerConnectDialog dialog;
    private boolean finalResult;

    public ServerPinger(String address, ServerConnectDialog dialog) {
        this.address = address;
        this.dialog = dialog;
    }

    @Override
    public void run() {
        dialog.setProgress(0);
        dialog.setUnknownProgress(true);
        dialog.setStatus("Polling");
        boolean result = PollSock.poll(address);
        dialog.setProgress(50);
        dialog.setUnknownProgress(false);
        dialog.setStatus("Checking");
        if (result) {
            dialog.setProgress(100);
            dialog.setStatus("Connected to server");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            dialog.dispose();
        } else {
            dialog.setProgress(0);
            dialog.setStatus("Failed to connect to server, try again");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            dialog.dispose();
        }
        finalResult = result;
    }

    public boolean getResult() {
        return finalResult;
    }
}
