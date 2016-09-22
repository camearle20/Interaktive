package net.came20.interaktive.client.gui;

import net.came20.interaktive.LogHelper;
import net.came20.interaktive.client.Interpreter;
import net.came20.interaktive.client.gui.dialog.InteraktiveSplash;
import net.came20.interaktive.client.gui.dialog.ServerSelectDialog;

import javax.swing.*;

/**
 * Created by cameronearle on 8/27/16.
 */
public class GUI extends Interpreter {

    @Override
    public void run() {
        Thread splashThread = new InteraktiveSplash("/resources/splash.png", new JFrame(), 5000).getSplashThread();
        try {
            splashThread.join();
        } catch (InterruptedException e) {}

        //Splashscreen done, continue

        main: while (true) {
            serverselect: while (true) {
                ServerSelectDialog serverSelectDialog = new ServerSelectDialog();
                serverSelectDialog.pack();
                serverSelectDialog.setLocationRelativeTo(null);
                serverSelectDialog.setVisible(true);
                if (!serverSelectDialog.getResponse()) { //Cancel clicked, kill the program
                    System.exit(0);
                }
                //OK clicked, continue
            }

        }
    }

    @Override
    public void display(Object message) {
        JOptionPane.showMessageDialog(null, message.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
