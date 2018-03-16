package bbv.gui.betreuer;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;

public class BetreuerPlusPanel extends AbstractBetreuerPanel {
  public BetreuerPlusPanel() {
    super();

    lblNewLabel.setText("Neuer Betreuer");
    btnNewButton.setIcon(new ImageIcon(new ImageIcon("./data/plus.png").getImage().getScaledInstance(220, 220, Image.SCALE_DEFAULT)));
    add(btnNewButton, BorderLayout.CENTER);

  }

}
