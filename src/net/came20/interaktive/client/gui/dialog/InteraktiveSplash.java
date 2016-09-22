package net.came20.interaktive.client.gui.dialog;


import javax.swing.*;
import java.awt.*;

/**
 * Created by cameronearle on 9/21/16.
 */
public class InteraktiveSplash extends JWindow {

    private Thread splashThread;

    public InteraktiveSplash(String filename, Frame f, int waitTime)
    {
        super(f);
        java.net.URL imgURL = getClass().getResource(filename);
        JLabel l = new JLabel(new ImageIcon(imgURL));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                screenSize.height/2 - (labelSize.height/2));
        final int pause = waitTime;
        final Runnable closerRunner = new Runnable()
        {
            public void run()
            {
                setVisible(false);
                dispose();
            }
        };
        Runnable waitRunner = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(pause);
                    SwingUtilities.invokeAndWait(closerRunner);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    // can catch InvocationTargetException
                    // can catch InterruptedException
                }
            }
        };
        setVisible(true);
        splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
    }

    public Thread getSplashThread() {
        return splashThread;
    }
}
