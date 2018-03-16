package bbv.gui.betreuer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bbv.basics.Betreuer;
import bbv.gui.journal.JournalView;




public class BetreuerTab extends JScrollPane{

  private JFrame frame;
  private JPanel northOnlyPanel;
  private BetreuerView betreuerView;
  private JournalView journalView;
  private boolean betreuerViewDisplayed;
  
  public BetreuerTab(JFrame frame) {
    this.frame = frame;
    initialize();
    setViewportView(northOnlyPanel);
  }
  
  private void initialize() {
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
  }
  
  
  private void updateGUI() {
    northOnlyPanel.revalidate();
    betreuerView.revalidate();
    journalView.revalidate();
    this.revalidate();
    northOnlyPanel.repaint();
    betreuerView.repaint();
    journalView.repaint();
    this.repaint();
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
  
  public void betreuerFromMenu(Betreuer betreuer) {
    betreuerView.newBetreuer(betreuer);
  }
  
  
}