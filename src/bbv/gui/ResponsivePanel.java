package bbv.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public abstract class ResponsivePanel extends JPanel {
  protected List<ActionListener> listeners = new ArrayList<ActionListener>();

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  protected void informListeners(String string) {
    for(ActionListener listener : listeners) {
      ActionEvent event = new ActionEvent(this, 0, string);
      listener.actionPerformed(event);
    }
  }
  
  protected void informListeners() {
    informListeners("");
  }
}
