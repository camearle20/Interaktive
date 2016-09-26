package net.came20.interaktive.client.gui.dialog;

import javax.swing.*;

public class ServerConnectDialog extends JDialog {
    private JPanel contentPane;
    private JLabel addressLabel;
    private JProgressBar progressBar1;
    private JLabel statusLabel;
    private JButton buttonOK;

    public ServerConnectDialog(String address) {
        addressLabel.setText("Connecting to server: [" + address + "]");
        setResizable(false);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public void setProgress(int progress) {
        progressBar1.setValue(progress);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setUnknownProgress(boolean set) {
        progressBar1.setIndeterminate(set);
    }
}
