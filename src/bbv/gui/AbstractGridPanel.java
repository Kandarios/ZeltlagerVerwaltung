package bbv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


public abstract class AbstractGridPanel extends ResponsivePanel {
  protected JButton btnNewButton = new JButton("");
  protected JLabel lblNewLabel = new JLabel("");


  public AbstractGridPanel() {
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
}
