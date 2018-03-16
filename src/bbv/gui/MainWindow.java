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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import bbv.gui.betreuer.BetreuerDialog;
import bbv.gui.betreuer.BetreuerView;
import bbv.gui.journal.JournalView;

public class MainWindow {

  private JFrame frame;
  private JScrollPane scrollPane;
  private JPanel northOnlyPanel;
  private BetreuerView betreuerView;
  private JournalView journalView;
  private boolean betreuerViewDisplayed;


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
    betreuerViewDisplayed = true;

    journalView = new JournalView();



    betreuerView = new BetreuerView(frame);
    betreuerView.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        case "showJournal":
          northOnlyPanel.removeAll();
          if(betreuerViewDisplayed) {
            newJournalView(betreuerView.getCurrentBetreuerID());
          } else {
            northOnlyPanel.add(betreuerView, BorderLayout.NORTH);
          }
          betreuerViewDisplayed = !betreuerViewDisplayed;
        case "update":
        default:
          updateGUI();
        }
      }
    });
    northOnlyPanel = new JPanel();
    northOnlyPanel.setLayout(new BorderLayout());
    northOnlyPanel.add(betreuerView, BorderLayout.NORTH);

    scrollPane = new JScrollPane();
//    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    scrollPane.setViewportView(northOnlyPanel);
    
    JTabbedPane mainTabs = new JTabbedPane();
    frame.getContentPane().add(mainTabs, BorderLayout.CENTER);
    mainTabs.addTab("Betreuer", scrollPane);
    
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
            betreuerView.newBetreuer(dialog.getBetreuer());
          }
        });
      }
    });

    mnFile.add(mntmNew);
    mnFile.add(mntmExit);
  }

  private void updateGUI() {
    northOnlyPanel.revalidate();
    betreuerView.revalidate();
    journalView.revalidate();
    scrollPane.revalidate();
    northOnlyPanel.repaint();
    betreuerView.repaint();
    journalView.repaint();
    scrollPane.repaint();
  }

  private void newJournalView(Long betreuerID) {
    journalView = new JournalView(betreuerID);
    journalView.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
        case "return":
          northOnlyPanel.removeAll();
          northOnlyPanel.add(betreuerView, BorderLayout.NORTH);
          betreuerViewDisplayed = !betreuerViewDisplayed;
          updateGUI();
          break;
        case "update":
          updateGUI();
        }
      }
    });
    northOnlyPanel.add(journalView, BorderLayout.NORTH);
  }
}
