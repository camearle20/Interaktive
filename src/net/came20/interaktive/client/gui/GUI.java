package net.came20.interaktive.client.gui;

import net.came20.interaktive.client.AnnounceListener;
import net.came20.interaktive.client.Client;
import net.came20.interaktive.client.HeartbeatListener;
import net.came20.interaktive.client.Interpreter;
import net.came20.interaktive.client.gui.dialog.InteraktiveSplash;
import net.came20.interaktive.client.gui.dialog.ServerConnectDialog;
import net.came20.interaktive.client.gui.dialog.ServerSelectDialog;
import org.zeromq.ZMQ;

import javax.swing.*;

/**
 * Created by cameronearle on 8/27/16.
 */
public class GUI extends Interpreter {

    String serverAddress;
    String announceAddress;
    boolean pingResult;

    ZMQ.Context context;
    ZMQ.Socket socket;

    public GUI(ZMQ.Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        Thread splashThread = new InteraktiveSplash("/resources/splash.png", new JFrame(), 5000).getSplashThread();

        socket = context.socket(ZMQ.REP);

        try {
            splashThread.join();
        } catch (InterruptedException e) {
        }

        //Splashscreen done, continue
        main:
        while (true) {

            serverselect:
            while (true) {
                ServerSelectDialog serverSelectDialog = new ServerSelectDialog();
                serverSelectDialog.pack();
                serverSelectDialog.setLocationRelativeTo(null);
                serverSelectDialog.setVisible(true);
                if (!serverSelectDialog.getResponse()) { //Cancel clicked, kill the program
                    System.exit(0);
                }
                serverAddress = serverSelectDialog.getServer();
                //System.out.println(PollSock.poll(server));
                ServerConnectDialog serverConnectDialog = new ServerConnectDialog(serverAddress);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        serverConnectDialog.pack();
                        serverConnectDialog.setLocationRelativeTo(null);
                        serverConnectDialog.setVisible(true);
                    }
                });
                ServerPinger pinger = new ServerPinger(serverAddress, serverConnectDialog);
                pinger.run();
                pingResult = pinger.getResult();
                if (pingResult) {
                    break serverselect;
                }
            }

            announceAddress = (serverAddress.split(":")[0]) + ":" + ((Integer.parseInt(serverAddress.split(":")[1])) + 1);

            HeartbeatListener heartbeatListener = new HeartbeatListener();
            AnnounceListener announceListener = new AnnounceListener(context, announceAddress);


            Thread heartbeatListenerThread = new Thread(heartbeatListener, "Heartbeat Listener");
            Thread announceListenerThread = new Thread(announceListener, "Announce Listener");
            heartbeatListenerThread.start();
            announceListenerThread.start();



            try {
                Thread.sleep(5000);
            } catch (Exception ignored) {}
            try {
                heartbeatListenerThread.interrupt();
                heartbeatListenerThread.join();
            } catch (Exception ignored) {}
            try {
                announceListenerThread.interrupt();
                announceListenerThread.join();
            } catch (Exception ignored) {}

        }
    }

    @Override
    public void display(Object message) {
        JOptionPane.showMessageDialog(null, message.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
