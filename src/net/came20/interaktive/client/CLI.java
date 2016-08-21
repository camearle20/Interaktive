package net.came20.interaktive.client;

import net.came20.interaktive.LogHelper;

public class CLI implements Runnable {

    @Override
    public void run() {
        //First we completely disable the logger, to prevent it from outputting to the stdout
        new LogHelper(this.getClass()).log("Disabling Logger, see ya sucker!");
        LogHelper.setEnabled(null, false);
        
    }
}
