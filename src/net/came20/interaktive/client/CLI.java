package net.came20.interaktive.client;

import net.came20.interaktive.LogHelper;

public class CLI extends CommandStrategy {

    @Override
    public void run() {
        //First we completely disable the logger, to prevent it from outputting to the stdout
        LogHelper.setEnabled(null, false);
        
    }
}
