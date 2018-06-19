package bbv.gui;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;

public class PlusPanel extends AbstractGridPanel {
  public PlusPanel() {
    super();

    lblNewLabel.setText("Neu");
    btnNewButton.setIcon(new ImageIcon(new ImageIcon("./data/plus.png").getImage().getScaledInstance(220, 220, Image.SCALE_DEFAULT)));
    add(btnNewButton, BorderLayout.CENTER);
  }
}
