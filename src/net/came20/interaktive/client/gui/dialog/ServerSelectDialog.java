package net.came20.interaktive.client.gui.dialog;

import javax.swing.*;
import java.awt.event.*;

public class ServerSelectDialog extends JDialog {
    private boolean response;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField addressField;
    private JSpinner portSpinner;
    private JComboBox comboBox1;

    public ServerSelectDialog() {
        setResizable(false);
        portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner, "#"));
        portSpinner.setValue(5260);
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
        response = true;
        dispose();
    }

    private void onCancel() {
        response = false;
        dispose();
    }

    public boolean getResponse() {
        return response;
    }

    public String getServer() {
        return ((String) addressField.getText() + ":" + portSpinner.getValue());
    }
}
