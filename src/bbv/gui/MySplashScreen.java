package bbv.gui;


import java.awt.SplashScreen;

import javax.swing.JFrame; 

public class MySplashScreen extends JFrame { 

  private static final int SHOW_FOR = 4000; 

  public MySplashScreen() { 

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    this.setSize(500, 400); 
    this.setLocationRelativeTo(null); 
    this.setTitle("Splash-Demo"); 

    Thread t = new Thread() { 
      public void run() { 
        SplashScreen splash = SplashScreen.getSplashScreen(); 
        if (splash != null) { 
          try { 
            Thread.sleep(SHOW_FOR); 
          } catch (InterruptedException e) { 
            System.err.println("Thread unterbrochen"); 
          } 
        } 
      } 
    }; 
    t.start(); 
  } 
} 