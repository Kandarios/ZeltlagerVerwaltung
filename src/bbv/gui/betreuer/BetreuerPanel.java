package bbv.gui.betreuer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import bbv.basics.Betreuer;
import helper.ImageUtilities;

public class BetreuerPanel extends AbstractBetreuerPanel {

  Betreuer betreuer;

  public BetreuerPanel(Betreuer betreuer) {
    super();
    this.betreuer = betreuer;
    lblNewLabel.setText(betreuer.getName());
    if (betreuer.getPicture().isEmpty() || !ImageUtilities.imageExists(betreuer.getPicture())) {
      btnNewButton.setIcon(new ImageIcon(new ImageIcon("./data/default_person.png").getImage().getScaledInstance(220, 220, Image.SCALE_DEFAULT)));
    } else {
      ImageIcon icon = new ImageIcon(betreuer.getPicture());
      Dimension scaledDimension = ImageUtilities.getScaledDimension(new Dimension(icon.getIconWidth(), icon.getIconHeight()),  new Dimension(220, 220));
      btnNewButton.setIcon(new ImageIcon(icon.getImage().getScaledInstance((int) scaledDimension.getWidth(), (int) scaledDimension.getHeight(), Image.SCALE_DEFAULT)));
    }
    add(btnNewButton, BorderLayout.CENTER);

    btnNewButton.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {
        dispatchEvent(e);    
      }

      @Override
      public void mousePressed(MouseEvent e) {
        dispatchEvent(e);        
        }

      @Override
      public void mouseExited(MouseEvent e) {
        dispatchEvent(e);        
        }

      @Override
      public void mouseEntered(MouseEvent e) {
        dispatchEvent(e);       
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        dispatchEvent(e);        
        }
    }); 

  }

  public Betreuer getBetreuer() {
    return betreuer;
  }

}
