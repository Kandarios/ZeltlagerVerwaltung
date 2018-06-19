package bbv.gui.teamer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import bbv.basics.Teamer;
import bbv.database.ZeltlagerDB;
import bbv.gui.AbstractGridPanel;
import bbv.gui.PlusPanel;
import bbv.gui.ResponsivePanel;

public class TeamerView extends ResponsivePanel {

  private List<AbstractGridPanel> teamerPanelList = new ArrayList<AbstractGridPanel>();
  private ZeltlagerDB teamerDB = ZeltlagerDB.getInstance();
  private JFrame frame;
  private Long currentTeamerID;

  public TeamerView(JFrame frame) {
    this.frame = frame;
    this.setLayout(new GridLayout(0, 3, 0, 0));
    listExistingTeamer();
  }

  public void newTeamer(Teamer teamer) {
    teamerDB.save(teamer);
    listExistingTeamer();
  }

  private void listExistingTeamer() {
    List<Teamer> list = teamerDB.getTeamerList();
    teamerPanelList.clear();
    PlusPanel plusPanel= new PlusPanel();
    addListener(plusPanel);
    teamerPanelList.add(plusPanel);
    for(Teamer teamer : list)  {
      TeamerPanel betPanel = new TeamerPanel(teamer);
      addListener(betPanel);
      teamerPanelList.add(0, betPanel);
    }
    updateTeamerView();
  }


  private void addListener(AbstractGridPanel panel) {
    if(panel instanceof PlusPanel) {
      panel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          TeamerDialog dialog = new TeamerDialog(frame);
          dialog.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent arg0)  {
              newTeamer(dialog.getTeamer());
            }
          });
        }
      });
    } else {
      TeamerPanel teamerPanel = (TeamerPanel) panel;
      panel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          Teamer selectedTeamer = teamerPanel.getTeamer();
          currentTeamerID = selectedTeamer.getTeamerId();
        }
      });
      panel.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e){
          if (e.isPopupTrigger())
            doPop(e);
        }

        public void mouseReleased(MouseEvent e){
          if (e.isPopupTrigger())
            doPop(e);
        }

        private void doPop(MouseEvent e){

          JPopupMenu menu = new JPopupMenu();

          JMenuItem changeItem;
          JMenuItem deleteItem;

          changeItem = new JMenuItem("Bearbeiten");
          menu.add(changeItem);

          changeItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              TeamerDialog dialog = new TeamerDialog(frame, teamerPanel.getTeamer());
              dialog.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent arg0)
                {
                  teamerDB.updateTeamer(teamerPanel.getTeamer().getTeamerId(), dialog.getName());
                  listExistingTeamer();
                }
              });
            }
          });

          deleteItem = new JMenuItem("Löschen");
          menu.add(deleteItem);

          deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              teamerDB.delete(teamerPanel.getTeamer());
              listExistingTeamer();
            }
          });
          menu.show(e.getComponent(), e.getX(), e.getY());
        }
      });
    }
  }

  public void updateTeamerView() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        update();
      }
    });
  }

  private void update() {
    this.removeAll();
    for(AbstractGridPanel panel : teamerPanelList) {
      addPanel(panel);   
    }
    this.validate();
    informListeners("update");
  }

  public void addPanel(JPanel panel) {
    this.add(panel);
  }

  public Long getCurrentTeamerID() {
    return currentTeamerID;
  }

  public void teamerFromMenu() {
    listExistingTeamer();
  }
  
}
