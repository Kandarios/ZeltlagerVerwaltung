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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bbv.basics.Teilnehmer;
import bbv.gui.betreuer.BetreuerDialog;
import bbv.gui.betreuer.BetreuerTab;
import bbv.gui.teilnehmer.TeilnehmerDialog;
import bbv.gui.zelte.ZeltTab;
import database.ZeltlagerDB;
import helper.TeilnehmerSearchTableModel;
import helper.ZeltSearchTableModel;

public class MainWindow {

  private JFrame frame;
  private BetreuerTab betreuerTab;
  private ZeltTab zeltTab;
  private ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  private JTabbedPane mainTabs = new JTabbedPane();


  /**
   * Create the application.
   */
  public MainWindow() {
    frame = new JFrame("Betreuer Verwaltung");
    betreuerTab = new BetreuerTab(frame);
    zeltTab = new ZeltTab(frame);
    initializeMenuBar();
    initializeTabs();
    this.frame.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initializeTabs() {
    frame.setBounds(100, 100, 1200, 900);
    frame.setMinimumSize(new Dimension(900, 500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
    frame.getContentPane().add(mainTabs, BorderLayout.CENTER);
    mainTabs.addChangeListener(new ChangeListener() {
      
      @Override
      public void stateChanged(ChangeEvent e) {
        if(betreuerTab.betreuerHaveChanged()) {
          zeltTab.updateView();          
        }
      }
    });
  
    mainTabs.addTab("Betreuer", betreuerTab);
    mainTabs.addTab("Zeltplan", zeltTab); 
  }

  private void initializeMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JMenu mnFile = new JMenu("Datei");
    mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnFile);

    JMenuItem mntmExit = new JMenuItem("Schließen");
    mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        frame.dispatchEvent(new WindowEvent(
            frame, WindowEvent.WINDOW_CLOSING));
      }
    });
    mnFile.add(mntmExit);
    
    JMenu mnHinzufügen = new JMenu("Hinzufügen");
    mnHinzufügen.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnHinzufügen);
    
    JMenuItem menuItem = new JMenuItem("Betreuer");
    menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        BetreuerDialog dialog = new BetreuerDialog(frame);
        dialog.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent arg0)
          {
            betreuerDB.save(dialog.getBetreuer());
            betreuerTab.betreuerFromMenu();
            zeltTab.updateView();
          }
        });
      }
    });
    mnHinzufügen.add(menuItem);
    
    
    
    JMenuItem mntmNeuerTeilnehmer = new JMenuItem("Teilnehmer");
    mntmNeuerTeilnehmer.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        TeilnehmerDialog dialog = new TeilnehmerDialog();
        dialog.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent arg0)
          {
            for(Teilnehmer b : dialog.getTeilnehmer()) {
              betreuerDB.save(b);       
            }
            betreuerTab.betreuerFromMenu();
            zeltTab.updateView();
          }
        });
      }
    });
    mntmNeuerTeilnehmer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mnHinzufügen.add(mntmNeuerTeilnehmer);
    
    JMenu mnSuchen = new JMenu("Suchen");
    mnSuchen.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnSuchen);
    
    JMenuItem mntmSearchTeilnehmer = new JMenuItem("Teilnehmer");
    mntmSearchTeilnehmer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmSearchTeilnehmer.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        SearchWindow dialog = new SearchWindow(new TeilnehmerSearchTableModel(), "Teilnehmer suchen");
        switchToSearchResult(dialog);
        dialog.setVisible(true);
      }
    });
    mnSuchen.add(mntmSearchTeilnehmer);
    
    JMenuItem mntmSearchZelt = new JMenuItem("Zelt");
    mntmSearchZelt.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmSearchZelt.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        SearchWindow dialog = new SearchWindow(new ZeltSearchTableModel(), "Zelt suchen");
        switchToSearchResult(dialog);
        dialog.setVisible(true);
      }
    });
    mnSuchen.add(mntmSearchZelt);
  }

  private void switchToSearchResult(SearchWindow dialog) {
    dialog.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        mainTabs.setSelectedComponent(zeltTab);
        zeltTab.showSearchedZelt(dialog.getZelt());
      }
    });
  }
  
 
}
