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
import bbv.database.ZeltlagerDB;
import bbv.gui.ResponsivePanel;

public class JournalView extends ResponsivePanel {

  Long betreuerID;
  Betreuer betreuer;
  ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  JPanel entryPanel = new JPanel();
  private List<ResponsivePanel> journalPanelList = new ArrayList<ResponsivePanel>();

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

    JButton btnNewButton = new JButton("Zur�ck");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        informListeners("return");
      }
    });
    panel_top.setLayout(new BorderLayout(0, 0));

    JLabel lbl_betreuer = new JLabel("Name: " + betreuer.getName() + ",      Zelt: ");
    panel_top.add(lbl_betreuer);
    panel_top.add(btnNewButton, BorderLayout.EAST);

    JPanel panel_bottom = new JPanel();
    add(panel_bottom, BorderLayout.SOUTH);

    listExistingJounralEntries();
  }

  private void listExistingJounralEntries() {
    
    journalPanelList.clear();
    JournalEditPanel editPanel = new JournalEditPanel(betreuerID);
    addListeners(editPanel);
    journalPanelList.add(editPanel);
    betreuerDB.refresh(betreuer);
    List<JournalEntry> list = betreuer.getJournalEntries();
    Collections.sort(list, new JournalComparator().reversed());
    for(JournalEntry entry : list)  {
      JournalShowPanel showPanel = new JournalShowPanel(entry);
      addListeners(showPanel);
      journalPanelList.add(showPanel);
    }
    updateJounralView();
  }

  private void updateJounralView() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        update();
      }
    });
  }

  private void addListeners(ResponsivePanel panel) {

    if(panel instanceof JournalEditPanel) {
      JournalEditPanel editPanel = (JournalEditPanel) panel;
      editPanel.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          betreuerDB.save(editPanel.getEntry());
          listExistingJounralEntries();
        }
      });
    } else {
      JournalShowPanel showPanel = (JournalShowPanel) panel;
      showPanel.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          int dialogResult = JOptionPane.showConfirmDialog (null, "Diesen Eintrag sicher l�schen?");
          if(dialogResult == JOptionPane.YES_OPTION){
            betreuerDB.delete(showPanel.getEntry());
            listExistingJounralEntries();
          }
        }
      });
    }
  }

  private void update() {
    entryPanel.removeAll();
    for(ResponsivePanel panel : journalPanelList) {
      entryPanel.add(panel);   
    }
    entryPanel.validate();
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
        e.printStackTrace();
      }
      return -1;
    }
  }

}
