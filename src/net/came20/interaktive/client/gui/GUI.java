package net.came20.interaktive.client.gui;

import net.came20.interaktive.LogHelper;
import net.came20.interaktive.client.Interpreter;
import javax.swing.JOptionPane;
/**
 * Created by cameronearle on 8/27/16.
 */
public class GUI extends Interpreter {

    LogHelper logger = new LogHelper(this.getClass());
    LoginDialog dialog;

    @Override
    public void run() {
        LoginDialog.start();
        dialog = LoginDialog.getDialog();
    }

    @Override
    public void display(Object message) {
        JOptionPane.showMessageDialog(null, message.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
