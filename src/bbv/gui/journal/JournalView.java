package bbv.gui.journal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import bbv.basics.Betreuer;
import bbv.basics.JournalEntry;
import database.BetreuerDB;

public class JournalView extends JPanel {

  Long betreuerID;
  Betreuer betreuer;
  BetreuerDB betreuerDB = BetreuerDB.getInstance();
  JPanel entryPanel = new JPanel();
  private List<AbstractJournalPanel> journalPanelList = new ArrayList<AbstractJournalPanel>();

  private List<ActionListener> listeners = new ArrayList<ActionListener>();

  public JournalView() {
    setLayout(new BorderLayout(0, 0));
    entryPanel.setLayout(new GridLayout(0, 1, 0, 0));
    add(entryPanel, BorderLayout.CENTER);
  }

  public JournalView(Long betreuerID) {
    this.betreuerID = betreuerID;
    betreuer = betreuerDB.getBetreuer(betreuerID);
    setLayout(new BorderLayout(0, 0));
    entryPanel.setLayout(new GridLayout(0, 1, 0, 0));
    add(entryPanel, BorderLayout.CENTER);
    initialize();
  }

  private void initialize() {
    JPanel panel_top = new JPanel();
    panel_top.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(panel_top, BorderLayout.NORTH);

    JButton btnNewButton = new JButton("Zurück");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        informListeners("return");
      }
    });
    panel_top.setLayout(new BorderLayout(0, 0));

    JLabel lbl_betreuer = new JLabel("Name: " + betreuer.getName() + ",      Zelt: " + betreuer.getZelt());
    panel_top.add(lbl_betreuer);
    panel_top.add(btnNewButton, BorderLayout.EAST);

    JPanel panel_bottom = new JPanel();
    add(panel_bottom, BorderLayout.SOUTH);

    listExistingJounralEntries();
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  private void informListeners(String command) {
    for(ActionListener listener : listeners) {
      ActionEvent event = new ActionEvent(this, 0, command);
      listener.actionPerformed(event);;
    }
  }

  private void listExistingJounralEntries() {
    journalPanelList.clear();
    JournalEditPanel editPanel = new JournalEditPanel(betreuerID);
    System.out.println("In JournalView the ID is " + betreuerID);
    addListeners(editPanel);
    journalPanelList.add(editPanel);
    List<JournalEntry> list = betreuerDB.getJournalEntryList(betreuerID);
    Collections.sort(list, new JournalComparator().reversed());
    for(JournalEntry entry : list)  {
      JournalShowPanel showPanel = new JournalShowPanel(entry);
      addListeners(showPanel);
      journalPanelList.add(showPanel);
    }
    updateJounralView();
  }

  private void updateJounralView() {
    //change something
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        update();
      }
    });
  }

  private void addListeners(AbstractJournalPanel panel) {

    if(panel instanceof JournalEditPanel) {
      JournalEditPanel editPanel = (JournalEditPanel) panel;
      editPanel.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          betreuerDB.saveJournalEntry(editPanel.getEntry());
          listExistingJounralEntries();
        }
      });
    } else {
      JournalShowPanel showPanel = (JournalShowPanel) panel;
      showPanel.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          int dialogResult = JOptionPane.showConfirmDialog (null, "Diesen Eintrag sicher löschen?");
          if(dialogResult == JOptionPane.YES_OPTION){
            betreuerDB.deleteJournalEntry(showPanel.getEntryID());;
            listExistingJounralEntries();
          }
        }
      });
    }
  }

  private void update() {
    entryPanel.removeAll();
    for(AbstractJournalPanel panel : journalPanelList) {
      entryPanel.add(panel);   
    }
    //    this.revalidate();
    entryPanel.validate();
    //    entryPanel.repaint();
    informListeners("update");
  }

  private class JournalComparator implements Comparator<JournalEntry> {
    @Override
    public int compare(JournalEntry o1, JournalEntry o2) {
      DateFormat df = new SimpleDateFormat("dd.mm.yyyy");
      Date firstDate;
      Date secondDate;
      try {
        firstDate = df.parse(o1.getDate());
        secondDate = df.parse(o2.getDate());
        return firstDate.compareTo(secondDate);
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      return -1;
    }
  }

}
