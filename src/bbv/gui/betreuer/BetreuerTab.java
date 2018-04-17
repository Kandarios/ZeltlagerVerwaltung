package bbv.gui.betreuer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bbv.gui.journal.JournalView;

public class BetreuerTab extends JScrollPane{
  private JFrame frame;
  private JPanel northOnlyPanel;
  private BetreuerView betreuerView;
  private JournalView journalView;
  private boolean betreuerViewDisplayed;
  private boolean betreuerChanged = false;
  
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
          break;
        case "update":
          betreuerChanged = true;
        }
        updateGUI();
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
        }
        updateGUI();
      }
    });
    northOnlyPanel.add(journalView, BorderLayout.NORTH);
  }
  
  public void betreuerFromMenu() {
    betreuerChanged = true;
    betreuerView.betreuerFromMenu();
    updateGUI();
  }
  
  public boolean betreuerHaveChanged() {
    if(betreuerChanged) {
      betreuerChanged = false; 
      return true;
    }
    return betreuerChanged;
  }
}
