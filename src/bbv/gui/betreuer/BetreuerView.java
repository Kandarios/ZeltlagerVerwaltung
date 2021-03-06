package bbv.gui.betreuer;

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

import bbv.basics.Betreuer;
import bbv.database.ZeltlagerDB;
import bbv.gui.AbstractGridPanel;
import bbv.gui.PlusPanel;
import bbv.gui.ResponsivePanel;

public class BetreuerView extends ResponsivePanel {
  private List<AbstractGridPanel> betreuerPanelList = new ArrayList<AbstractGridPanel>();
  private ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  private JFrame frame;
  private Long currentBetreuerID;

  public BetreuerView(JFrame frame) {
    this.frame = frame;
    this.setLayout(new GridLayout(0, 3, 0, 0));
    listExistingBetreuer();
  }

  public void newBetreuer(Betreuer betreuer) {
    betreuerDB.save(betreuer);
    listExistingBetreuer();
  }

  private void listExistingBetreuer() {
    List<Betreuer> list = betreuerDB.getBetreuerList();
    betreuerPanelList.clear();
    PlusPanel plusPanel= new PlusPanel();
    addListener(plusPanel);
    betreuerPanelList.add(plusPanel);
    for(Betreuer betreuer : list)  {
      BetreuerPanel betPanel = new BetreuerPanel(betreuer);
      addListener(betPanel);
      betreuerPanelList.add(0, betPanel);
    }
    updateBetreuerView();
  }


  private void addListener(AbstractGridPanel panel) {
    if(panel instanceof PlusPanel) {
      panel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          BetreuerDialog dialog = new BetreuerDialog(frame);
          dialog.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent arg0)
            {
              newBetreuer(dialog.getBetreuer());
            }
          });
        }
      });
    } else {
      BetreuerPanel betreuerPanel = (BetreuerPanel) panel;
      panel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          Betreuer selectedBetreuer = betreuerPanel.getBetreuer();
          currentBetreuerID = selectedBetreuer.getBetreuerId();
          informListeners("showJournal");

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
              BetreuerDialog dialog = new BetreuerDialog(frame, betreuerPanel.getBetreuer());
              dialog.addActionListener(new ActionListener()
              {
                public void actionPerformed(ActionEvent arg0)
                {
                  betreuerDB.updateBetreuer(betreuerPanel.getBetreuer().getBetreuerId(), dialog.getBetreuerName(), dialog.getBetreuerPicture());
                  listExistingBetreuer();
                }
              });
            }
          });

          deleteItem = new JMenuItem("L�schen");
          menu.add(deleteItem);

          deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              betreuerDB.delete(betreuerPanel.getBetreuer());
              listExistingBetreuer();
            }
          });
          menu.show(e.getComponent(), e.getX(), e.getY());
        }
      });
    }
  }

  public void updateBetreuerView() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        update();
      }
    });
  }

  private void update() {
    this.removeAll();
    for(AbstractGridPanel panel : betreuerPanelList) {
      addPanel(panel);   
    }
    this.validate();
    informListeners("update");
  }

  public void addPanel(JPanel panel) {
    this.add(panel);
  }

  public Long getCurrentBetreuerID() {
    return currentBetreuerID;
  }

  public void betreuerFromMenu() {
    listExistingBetreuer();
  }
}
