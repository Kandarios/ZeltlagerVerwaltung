package bbv.gui.journal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public abstract class AbstractJournalPanel extends JPanel {
//  JButton btnNewButton = new JButton("");
//  JLabel lblNewLabel = new JLabel("");
  
  
  public AbstractJournalPanel() {
//    setBorder(new LineBorder(Color.GRAY, 1, true));
//    setLayout(new BorderLayout(0, 0));
//
//    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
//    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
//    add(lblNewLabel, BorderLayout.NORTH);
//
//    btnNewButton.setBackground(Color.LIGHT_GRAY);
//    btnNewButton.setHorizontalAlignment(SwingConstants.CENTER);
//    
//    btnNewButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        informListeners();
//      }
//    });
  }

  protected List<ActionListener> listeners = new ArrayList<ActionListener>();

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  protected void informListeners(String command) {
    for(ActionListener listener : listeners) {
      ActionEvent event = new ActionEvent(this, 0, "Aproved");
      listener.actionPerformed(event);;
    }
  }





}
