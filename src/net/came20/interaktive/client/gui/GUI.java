package net.came20.interaktive.client.gui;

import net.came20.interaktive.client.Interpreter;
import javax.swing.JOptionPane;
/**
 * Created by cameronearle on 8/27/16.
 */
public class GUI extends Interpreter {

    @Override
    public void display(Object message) {
        JOptionPane.showMessageDialog(null, message.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
