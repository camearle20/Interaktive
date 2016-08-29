package net.came20.interaktive.client.gui;

import javax.swing.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private static LoginDialog dialog;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JProgressBar progressBar1;

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        System.exit(1);
    }

    public String getUsername() {
        return dialog.textField1.getText();
    }

    public String getPassword() {
        return dialog.passwordField1.getText();
    }

    public static LoginDialog getDialog() {
        return dialog;
    }

    public static void start() {
        LoginDialog dialog = new LoginDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
