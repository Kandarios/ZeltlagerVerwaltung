package bbv.gui.teilnehmer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bbv.basics.Teilnehmer;
import bbv.database.ZeltlagerDB;
import bbv.gui.ResponsiveDialog;
import bbv.helper.TableSelectionMouseListener;
import bbv.helper.TeilnehmerAbgereistTableModel;
import bbv.helper.TeilnehmerUnterwegsTableModel;

public class TeilnehmerListWindow extends ResponsiveDialog {
  private JTable table;
  private ZeltlagerDB database = ZeltlagerDB.getInstance();  
  private AbstractTableModel model;

  public TeilnehmerListWindow(AbstractTableModel tableModel, String title) { //TODO Abstract TeilnehmerModel
    setMinimumSize(new Dimension(800, 600));
    getContentPane().setLayout(new BorderLayout(0, 0));
    this.model = tableModel;
    setTitle(title);
    JPanel panel_1 = new JPanel();
    getContentPane().add(panel_1, BorderLayout.CENTER);
    panel_1.setLayout(new BorderLayout(0, 0));

    JScrollPane scrollPane = new JScrollPane();
    panel_1.add(scrollPane, BorderLayout.CENTER);

    table = new JTable(model);
    scrollPane.setViewportView(table); 
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.addMouseListener(new TableSelectionMouseListener(table));
    updateTable();

    JPopupMenu popupMenuRückgängig = new JPopupMenu();
    JMenuItem rückgängigItem = new JMenuItem("Rückgängig");
    rückgängigItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRow() != -1) {
          if(model instanceof TeilnehmerAbgereistTableModel) {
            TeilnehmerAbgereistTableModel aModel = (TeilnehmerAbgereistTableModel) model;
            Teilnehmer t = aModel.getTeilnehmerAt(table.getSelectedRow());
            database.updateTeilnehmerAbreise(t.getTeilnehmerId(), false, null);
            updateTable();
          } else if (model instanceof TeilnehmerUnterwegsTableModel) {
            TeilnehmerUnterwegsTableModel uModel = (TeilnehmerUnterwegsTableModel) model;
            Teilnehmer t = uModel.getTeilnehmerAt(table.getSelectedRow());
            database.updateTeilnehmerUnterwegs(t.getTeilnehmerId(), null);
            updateTable();
          }
        }
      }
    });
    popupMenuRückgängig.add(rückgängigItem);

    table.addMouseListener(new MouseListener() {

      @Override
      public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
          if(table.getSelectedRow() != -1) {
            popupMenuRückgängig.show(table, e.getX(), e.getY());
          }
        }
      }
      @Override
      public void mouseExited(MouseEvent e) {}
      @Override
      public void mouseEntered(MouseEvent e) {}
      @Override
      public void mouseClicked(MouseEvent e) {}
      @Override
      public void mouseReleased(MouseEvent e) {}
    });
  } 

  private void updateTable() {

    if(model instanceof TeilnehmerAbgereistTableModel) {
      TeilnehmerAbgereistTableModel aModel = (TeilnehmerAbgereistTableModel) model;
      List<Teilnehmer> results = database.getAbgereistTeilnehmerList();
      aModel.clear();
      aModel.add(results);
    } else if (model instanceof TeilnehmerUnterwegsTableModel) {
      TeilnehmerUnterwegsTableModel uModel = (TeilnehmerUnterwegsTableModel) model;
      List<Teilnehmer> results = database.getUnterwegsTeilnehmerList();
      uModel.clear();
      uModel.add(results);
    }
    table.revalidate();
    table.repaint();
  }

}



