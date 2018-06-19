package bbv.gui.teamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bbv.gui.journal.JournalView;

public class TeamerTab extends JScrollPane {

  private JFrame frame;
  private JPanel northOnlyPanel;
  private TeamerView teamerView;
  private JournalView journalView;
  private boolean teamerViewDisplayed;
  private boolean teamerChanged = false;
  
  public TeamerTab(JFrame frame) {
    this.frame = frame;
    initialize();
    setViewportView(northOnlyPanel);
  }
  
  private void initialize() {
    teamerViewDisplayed = true;

    journalView = new JournalView();

    teamerView = new TeamerView(frame);
    teamerView.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        case "showJournal":
          northOnlyPanel.removeAll();
          if(teamerViewDisplayed) {
            newJournalView(teamerView.getCurrentTeamerID());
          } else {
            northOnlyPanel.add(teamerView, BorderLayout.NORTH);
          }
          teamerViewDisplayed = !teamerViewDisplayed;
          break;
        case "update":
          teamerChanged = true;
        }
        updateGUI();
      }
    });
    northOnlyPanel = new JPanel();
    northOnlyPanel.setLayout(new BorderLayout());
    northOnlyPanel.add(teamerView, BorderLayout.NORTH);
  }
  
  private void updateGUI() {
    northOnlyPanel.revalidate();
    teamerView.revalidate();
    journalView.revalidate();
    this.revalidate();
    northOnlyPanel.repaint();
    teamerView.repaint();
    journalView.repaint();
    this.repaint();
  }

  private void newJournalView(Long teamerID) {
    journalView = new JournalView(teamerID);
    journalView.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
        case "return":
          northOnlyPanel.removeAll();
          northOnlyPanel.add(teamerView, BorderLayout.NORTH);
          teamerViewDisplayed = !teamerViewDisplayed;        
        }
        updateGUI();
      }
    });
    northOnlyPanel.add(journalView, BorderLayout.NORTH);
  }
  
  public void teamerFromMenu() {
    teamerChanged = true;
    teamerView.teamerFromMenu();
    updateGUI();
  }
  
  public boolean teamerHaveChanged() {
    if(teamerChanged) {
      teamerChanged = false; 
      return true;
    }
    return teamerChanged;
  }

  
}
