package bbv.gui.betreuer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


public abstract class AbstractBetreuerPanel extends JPanel {
  JButton btnNewButton = new JButton("");
  JLabel lblNewLabel = new JLabel("");
  
  
  public AbstractBetreuerPanel() {
    setBorder(new LineBorder(Color.GRAY, 1, true));
    setLayout(new BorderLayout(0, 0));

    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(lblNewLabel, BorderLayout.NORTH);

    btnNewButton.setBackground(Color.LIGHT_GRAY);
    btnNewButton.setHorizontalAlignment(SwingConstants.CENTER);
    
    btnNewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        informListeners();
      }
    });
  }

  private List<ActionListener> listeners = new ArrayList<ActionListener>();

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  private void informListeners() {
    for(ActionListener listener : listeners) {
      ActionEvent event = new ActionEvent(this, 0, "Aproved");
      listener.actionPerformed(event);;
    }
  }





}
