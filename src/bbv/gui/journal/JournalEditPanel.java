package bbv.gui.journal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bbv.basics.JournalEntry;

public class JournalEditPanel extends AbstractJournalPanel {
  
  Long betreuerID;
  private JTextField textField;
  private JTextArea textArea;
  
  public JournalEditPanel(Long betreuerID) {
    this.betreuerID = betreuerID;
    setLayout(new BorderLayout(0, 0));
    setPreferredSize(new Dimension(600, 300));
    
    initialize();
  }
  
  void initialize() {
    JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);
    textArea = new JTextArea(5, 20);
    scrollPane.setViewportView(textArea);
    textArea.setMargin(new Insets(6, 10, 4, 6));
    
    JPanel panel = new JPanel();
    add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    
    JButton btnNewButton = new JButton("Speichern");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!"".equals(textArea.getText())) {
          saveEntry();
        }
      }
    });
    panel.add(btnNewButton);
    
    JButton btnNewButton_1 = new JButton("Löschen");
    btnNewButton_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textArea.setText("");
      }
    });
    panel.add(btnNewButton_1);
    
    JPanel panel_1 = new JPanel();
    add(panel_1, BorderLayout.NORTH);
    panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    
    JLabel lblNewLabel = new JLabel("Datum: ");
    panel_1.add(lblNewLabel);
    
    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String dateText = date.format(formatter);
    
    textField = new JTextField();
    panel_1.add(textField);
    textField.setColumns(10);
    textField.setText(dateText);
    
    JPanel panel_2 = new JPanel();
    add(panel_2, BorderLayout.WEST);
    
    Component horizontalStrut = Box.createHorizontalStrut(20);
    panel_2.add(horizontalStrut);
    
    JPanel panel_3 = new JPanel();
    add(panel_3, BorderLayout.EAST);
    
    Component horizontalStrut_1 = Box.createHorizontalStrut(20);
    panel_3.add(horizontalStrut_1);
  }
  
  void saveEntry() {
    System.out.println("In EditPanel the ID is " + betreuerID);

    informListeners("saved");
  }
  
  JournalEntry getEntry() {
    return new JournalEntry(betreuerID, textField.getText(), textArea.getText());
  }
  


}
