package bbv.main;

import java.awt.EventQueue;

import bbv.gui.MainWindow;

public class ZeltlagerVerwaltung {

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MainWindow window = new MainWindow();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
