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
import bbv.database.ZeltlagerDB;
import bbv.gui.abwesenheit.AbwesenheitsTab;
import bbv.gui.betreuer.BetreuerDialog;
import bbv.gui.betreuer.BetreuerTab;
import bbv.gui.teamer.TeamerTab;
import bbv.gui.teilnehmer.TeilnehmerDialog;
import bbv.gui.teilnehmer.TeilnehmerListWindow;
import bbv.gui.teilnehmer.TeilnehmerSearchWindow;
import bbv.gui.uebersicht.UebersichtTab;
import bbv.gui.zelte.ZeltTab;
import bbv.helper.TeilnehmerAbgereistTableModel;
import bbv.helper.TeilnehmerSearchTableModel;
import bbv.helper.TeilnehmerUnterwegsTableModel;
import bbv.helper.ZeltSearchTableModel;

public class MainWindow {

  private JFrame frame;
  private BetreuerTab betreuerTab;
  private TeamerTab teamerTab;
  private ZeltTab zeltTab;
  private AbwesenheitsTab abwesenheitsTab;
  private UebersichtTab uebersichtsTab;
  private ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  private JTabbedPane mainTabs = new JTabbedPane();


  /**
   * Create the application.
   */
  public MainWindow() {
    MySplashScreen screen = new MySplashScreen();
    frame = new JFrame("Betreuer Verwaltung");
    initializeMenuBar();
    initializeTabs();
    this.frame.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initializeTabs() {
    betreuerTab = new BetreuerTab(frame);
    zeltTab = new ZeltTab();
    teamerTab = new TeamerTab(frame);
    abwesenheitsTab = new AbwesenheitsTab();
    uebersichtsTab = new UebersichtTab();
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
    mainTabs.addTab("Teamer", teamerTab);
    mainTabs.addTab("Zeltplan", zeltTab); 
    mainTabs.addTab("Abwesenheit", abwesenheitsTab);
    mainTabs.addTab("�bersicht", uebersichtsTab);
  }

  private void initializeMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JMenu mnFile = new JMenu("Datei");
    mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnFile);

    JMenuItem mntmExit = new JMenuItem("Schlie�en");
    mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        frame.dispatchEvent(new WindowEvent(
            frame, WindowEvent.WINDOW_CLOSING));
      }
    });
    mnFile.add(mntmExit);
    
    JMenu mnHinzuf�gen = new JMenu("Hinzuf�gen");
    mnHinzuf�gen.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnHinzuf�gen);
    
    JMenuItem menuItem = new JMenuItem("Betreuer");
    menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        BetreuerDialog dialog = new BetreuerDialog(frame);
        dialog.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            betreuerDB.save(dialog.getBetreuer());
            betreuerTab.betreuerFromMenu();
            zeltTab.updateView();
          }
        });
      }
    });
    mnHinzuf�gen.add(menuItem);
       
    JMenuItem mntmNeuerTeilnehmer = new JMenuItem("Teilnehmer");
    mntmNeuerTeilnehmer.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        TeilnehmerDialog dialog = new TeilnehmerDialog();
        dialog.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            for(Teilnehmer b : dialog.getTeilnehmer()) {
              betreuerDB.save(b);       
            }
            zeltTab.updateView();
          }
        });
      }
    });
    mntmNeuerTeilnehmer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mnHinzuf�gen.add(mntmNeuerTeilnehmer);
    
    JMenu mnSuchen = new JMenu("Suchen");
    mnSuchen.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnSuchen);
    
    JMenuItem mntmSearchTeilnehmer = new JMenuItem("Teilnehmer");
    mntmSearchTeilnehmer.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmSearchTeilnehmer.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        TeilnehmerSearchWindow dialog = new TeilnehmerSearchWindow(new TeilnehmerSearchTableModel(), "Teilnehmer suchen");
        switchToSearchResult(dialog);
        dialog.setVisible(true);
      }
    });
    mnSuchen.add(mntmSearchTeilnehmer);
    
    JMenuItem mntmSearchZelt = new JMenuItem("Zelt");
    mntmSearchZelt.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmSearchZelt.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        TeilnehmerSearchWindow dialog = new TeilnehmerSearchWindow(new ZeltSearchTableModel(), "Zelt suchen");
        switchToSearchResult(dialog);
        dialog.setVisible(true);
      }
    });
    mnSuchen.add(mntmSearchZelt);
    
    JMenu mnAuflisten = new JMenu("Auflisten");
    mnAuflisten.setFont(new Font("Segoe UI", Font.PLAIN, 17));
    menuBar.add(mnAuflisten);
    
    JMenuItem mntmAuflistenUnterwegs = new JMenuItem("Teilnehmer Unterwegs");
    mntmAuflistenUnterwegs.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmAuflistenUnterwegs.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        TeilnehmerListWindow dialog = new TeilnehmerListWindow(new TeilnehmerUnterwegsTableModel(), "Teilmehmer Unterwegs");
        dialog.setVisible(true);
      }
    });
    mnAuflisten.add(mntmAuflistenUnterwegs);
    
    JMenuItem mntmAuflistenAbgereist = new JMenuItem("Teilnehmer Abgereist");
    mntmAuflistenAbgereist.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    mntmAuflistenAbgereist.addActionListener(new ActionListener() {      
      public void actionPerformed(ActionEvent e) {
        TeilnehmerListWindow dialog = new TeilnehmerListWindow(new TeilnehmerAbgereistTableModel(), "Teilnehmer Abgereist");
        dialog.setVisible(true);
      }
    });
    mnAuflisten.add(mntmAuflistenAbgereist);

  }

  private void switchToSearchResult(TeilnehmerSearchWindow dialog) {
    dialog.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        mainTabs.setSelectedComponent(zeltTab);
        zeltTab.showSearchedZelt(dialog.getZelt());
      }
    });
  }
  
 
}
