package bbv.gui.zelte;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import bbv.basics.Betreuer;
import bbv.basics.Teilnehmer;
import bbv.basics.Zelt;
import database.ZeltlagerDB;
import helper.ListBetreuerTransferHandler;
import helper.TableTeilnehmerTransferHandler;
import helper.TeilnehmerTableModel;

@SuppressWarnings("serial")
public class ZeltPanel extends JPanel {
  private JList<Betreuer> list_betreuer;
  private DefaultListModel<Betreuer> model;
  private JTable table_teilnehmer;
  private TeilnehmerTableModel tableModel;

  private List<JComponent> hideableComponents = new ArrayList<JComponent>();
  private boolean folded = false;
  
  private Zelt zelt;
  private ZeltlagerDB database = ZeltlagerDB.getInstance();

  private List<ActionListener> listeners = new ArrayList<ActionListener>();

  public ZeltPanel(Zelt z) {
    this.zelt = z;
    initializeGUI();
    fillBetreuerList();
    fillTeilnehmerTable();
    addInsertListeners();
  }

  public void initializeGUI() {
    setLayout(new BorderLayout(0, 0));
    setPreferredSize(new Dimension(300, 300));
    setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

    JPanel panel_content = new JPanel();
    add(panel_content, BorderLayout.CENTER);
    panel_content.setLayout(new BorderLayout(0, 0));
    hideableComponents.add(panel_content);

    JPanel panel_betreuer = new JPanel();
    panel_betreuer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    panel_content.add(panel_betreuer, BorderLayout.NORTH);
    panel_betreuer.setLayout(new BorderLayout(0, 0));
    hideableComponents.add(panel_betreuer);

    JLabel lbl_betreuer = new JLabel("Betreuer:");
    panel_betreuer.add(lbl_betreuer, BorderLayout.WEST);
    hideableComponents.add(panel_betreuer);

    model = new DefaultListModel<>();
    
    list_betreuer = new JList<Betreuer>();
    list_betreuer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    list_betreuer.setDropMode(DropMode.INSERT);
    list_betreuer.setModel(model);
    list_betreuer.setDragEnabled(true);
    list_betreuer.setTransferHandler(new ListBetreuerTransferHandler());
    panel_betreuer.add(list_betreuer);
    hideableComponents.add(list_betreuer);

    JScrollPane scrollPanelTeilnehmer = new JScrollPane();
    panel_content.add(scrollPanelTeilnehmer, BorderLayout.CENTER);
    hideableComponents.add(scrollPanelTeilnehmer);

    tableModel = new TeilnehmerTableModel();
    
    table_teilnehmer = new JTable(tableModel);
    table_teilnehmer.setFillsViewportHeight(true);
    table_teilnehmer.setShowVerticalLines(false);
    table_teilnehmer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table_teilnehmer.setDragEnabled(true);
    table_teilnehmer.setDropMode(DropMode.INSERT);
    table_teilnehmer.setTransferHandler(new TableTeilnehmerTransferHandler());
    scrollPanelTeilnehmer.setViewportView(table_teilnehmer);
    table_teilnehmer.setPreferredScrollableViewportSize(new Dimension(400, 100));
    hideableComponents.add(table_teilnehmer);


    JPanel panel_name = new JPanel();
    panel_name.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3) {
          JPopupMenu menu = new JPopupMenu();

          JMenuItem changeItem;
          JMenuItem deleteItem;

          changeItem = new JMenuItem("Bearbeiten");
          menu.add(changeItem);

          changeItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
          });

          deleteItem = new JMenuItem("Löschen");
          menu.add(deleteItem);

          deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              deleteThisZelt();
            }
          });
          menu.show(e.getComponent(), e.getX(), e.getY());
        } else {
          for(JComponent c : hideableComponents) {
            c.setVisible(!c.isVisible());
          }
          folded = !folded;
          if(folded) {
            setPreferredSize(new Dimension(30, 30));                              
          } else {
            setPreferredSize(new Dimension(300, 300));
          }
          revalidate();
          repaint();
        }
      }
    });
    add(panel_name, BorderLayout.NORTH);

    JLabel lbl_zeltname = new JLabel("Zelt: " + zelt.getName());
    lbl_zeltname.setFont(new Font("Tahoma", Font.BOLD, 14));
    panel_name.add(lbl_zeltname);
  }

  public void fillBetreuerList() {
    model.clear();
    for(Betreuer b : zelt.getBetreuerList()) {
      model.addElement(b);
    }
  }

  public void fillTeilnehmerTable() {
    tableModel.clear();
    tableModel.add(zelt.getTeilnehmerList());
  }
  
  public void addInsertListeners() {
    tableModel.addTableModelListener(new TableModelListener() {
      
      @Override
      public void tableChanged(TableModelEvent e) {
        switch(e.getType()) {
        case TableModelEvent.DELETE:
          zelt.removeTeilnehmer(tableModel.getTeilnehmerAt(e.getFirstRow()));
          break;
        case TableModelEvent.INSERT:
          Teilnehmer t = tableModel.getTeilnehmerAt(e.getFirstRow());
          zelt.insterTeilnehmer(t, e.getFirstRow()); /// Maybe not needed if i load from DB every time...
          database.updateTeilnehmerZelt(t.getTeilnehmerId(), zelt.getZeltId());
          break;
        }
      }
    });
    
    model.addListDataListener(new ListDataListener() {

      @Override
      public void intervalRemoved(ListDataEvent e) {
        zelt.removeBetreuerAt(e.getIndex0());
      }

      @Override
      public void intervalAdded(ListDataEvent e) {
        Betreuer addedBetreuer = model.getElementAt(e.getIndex0());
        database.updateBetreuerZelt(addedBetreuer.getBetreuerId(), zelt.getZeltId());
        zelt.insterBetreuer(addedBetreuer, e.getIndex0());
      }

      @Override
      public void contentsChanged(ListDataEvent e) {}
    });
  }
  
  private void deleteThisZelt() {
    for(Betreuer b : zelt.getBetreuerList()) {
      database.updateBetreuerZelt(b.getBetreuerId(), null);
    }
    list_betreuer.removeAll();
    for(Teilnehmer t : zelt.getTeilnehmerList()) {
      database.updateTeilnehmerZelt(t.getTeilnehmerId(), null);
    }
    table_teilnehmer.removeAll();
    database.delete(zelt);
    informListeners("delete");
  }
  
  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  private void informListeners(String reason) {
    for(ActionListener listener : listeners) {
      ActionEvent event = new ActionEvent(this, 0, reason);
      listener.actionPerformed(event);;
    }
  }
}
