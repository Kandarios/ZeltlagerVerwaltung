package bbv.gui.journal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import bbv.basics.JournalEntry;
import bbv.gui.ResponsivePanel;

public class JournalShowPanel extends ResponsivePanel {
  
  JournalEntry entry;
  
  public JournalShowPanel(JournalEntry entry) {
    setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    this.entry = entry;
    setLayout(new BorderLayout(0, 0));
    
    JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);
    
    JTextArea textArea = new JTextArea(5, 25);
    scrollPane.setViewportView(textArea);
    textArea.setMargin(new Insets(6, 10, 4, 6));
    textArea.setText(entry.getText());
    textArea.setEditable(false);
    
    JPanel panel = new JPanel();
    add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    
    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.NORTH);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    JLabel lblNewLabel = new JLabel("Datum: " + entry.getDate());
    panel_1.add(lblNewLabel);
    
    JButton btnNewButton = new JButton("X");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        informListeners("delete");
      }
    });
    panel_1.add(btnNewButton, BorderLayout.EAST);
    
    JPanel panel_2 = new JPanel();
    add(panel_2, BorderLayout.WEST);
    
    Component horizontalStrut = Box.createHorizontalStrut(20);
    panel_2.add(horizontalStrut);
    
    JPanel panel_3 = new JPanel();
    add(panel_3, BorderLayout.EAST);
    
    Component horizontalStrut_1 = Box.createHorizontalStrut(20);
    panel_3.add(horizontalStrut_1);
  }

  JournalEntry getEntry() {
    return entry;
  }
}
