package bbv.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import bbv.gui.betreuer.BetreuerDialog;
import bbv.gui.betreuer.BetreuerTab;

public class MainWindow {

  private JFrame frame;
  private BetreuerTab betreuerTab;



  /**
   * Create the application.
   */
  public MainWindow() {
    initialize();
    this.frame.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame("Betreuer Verwaltung");
    frame.setBounds(100, 100, 1200, 900);
    frame.setMinimumSize(new Dimension(900, 500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   

    betreuerTab = new BetreuerTab(frame);
//    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
    JTabbedPane mainTabs = new JTabbedPane();
    frame.getContentPane().add(mainTabs, BorderLayout.CENTER);
    mainTabs.addTab("Betreuer", betreuerTab);
    
    initializeMenuBar();
  }

  private void initializeMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JMenu mnFile = new JMenu("Datei");
    mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnFile);

    JMenuItem mntmExit = new JMenuItem("Schlieﬂen");
    mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        frame.dispatchEvent(new WindowEvent(
            frame, WindowEvent.WINDOW_CLOSING));
      }
    });

    JMenuItem mntmNew = new JMenuItem("Neuer Betreuer");
    mntmNew.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        BetreuerDialog dialog = new BetreuerDialog(frame);
        dialog.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent arg0)
          {
            betreuerTab.betreuerFromMenu(dialog.getBetreuer());
          }
        });
      }
    });

    mnFile.add(mntmNew);
    mnFile.add(mntmExit);
  }

 
}
