package bbv.gui.teamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import bbv.basics.Teamer;
import bbv.gui.AbstractGridPanel;
import bbv.helper.ImageUtilities;

public class TeamerPanel extends AbstractGridPanel {

  private Teamer teamer;
  
  public TeamerPanel(Teamer teamer) {
    this.teamer = teamer;
    lblNewLabel.setText(teamer.getName());
    if (teamer.getPicture().isEmpty() || !ImageUtilities.imageExists(teamer.getPicture())) {
      btnNewButton.setIcon(new ImageIcon(new ImageIcon("./data/default_person.png").getImage().getScaledInstance(220, 220, Image.SCALE_DEFAULT)));
    } else {
      ImageIcon icon = new ImageIcon(teamer.getPicture());
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

  public Teamer getTeamer() {
    return teamer;
  }

  
}
