package bbv.gui.teilnehmer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import bbv.basics.Teilnehmer;
import bbv.basics.Zelt;
import bbv.database.ZeltlagerDB;
import bbv.gui.ResponsiveDialog;
import bbv.helper.TeilnehmerSearchTableModel;
import bbv.helper.ZeltSearchTableModel;

public class TeilnehmerSearchWindow extends ResponsiveDialog {
  private JTable table;
  private JTextField textField;
  private ZeltlagerDB database = ZeltlagerDB.getInstance();  
  private AbstractTableModel model;

  private Zelt z = null;

  public TeilnehmerSearchWindow(AbstractTableModel tableModel, String title) {
    setMinimumSize(new Dimension(800, 600));
    getContentPane().setLayout(new BorderLayout(0, 0));
    this.model = tableModel;
    setTitle(title);

    JPanel panel = new JPanel();
    getContentPane().add(panel, BorderLayout.NORTH);

    ActionListener searchAction = new ActionListener(){

      public void actionPerformed(ActionEvent e){
        if(model instanceof TeilnehmerSearchTableModel) {
          TeilnehmerSearchTableModel tmodel = (TeilnehmerSearchTableModel) model;
          tmodel.clear();
          if(!"".equals(textField.getText())) {
            List<Teilnehmer> results = database.searchTeilnehmer(textField.getText());
            tmodel.add(results);
          }
        } else if(model instanceof ZeltSearchTableModel) {
          ZeltSearchTableModel zmodel = (ZeltSearchTableModel) model;
          zmodel.clear();
          if(!"".equals(textField.getText())) {
            List<Zelt> result = database.searchZelt(textField.getText());
            zmodel.add(result);
          }
        }
      }};

      textField = new JTextField();
      panel.add(textField);
      textField.setColumns(10);
      textField.addActionListener(searchAction);

      JButton btnSuchen = new JButton("Suchen");
      btnSuchen.addActionListener(searchAction);
      panel.add(btnSuchen);

      JPanel panel_1 = new JPanel();
      getContentPane().add(panel_1, BorderLayout.CENTER);
      panel_1.setLayout(new BorderLayout(0, 0));

      JScrollPane scrollPane = new JScrollPane();
      panel_1.add(scrollPane, BorderLayout.CENTER);

      table = new JTable(model);
      scrollPane.setViewportView(table); 
      table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

      JPanel panel_2 = new JPanel();
      getContentPane().add(panel_2, BorderLayout.SOUTH);
      panel_2.setLayout(new BorderLayout(0, 0));

      JButton btnNewButton = new JButton("Zum Zelt");
      btnNewButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int rowIndex = table.getSelectedRow();
          if(rowIndex != -1) {
            if(model instanceof TeilnehmerSearchTableModel) {
              TeilnehmerSearchTableModel tmodel = (TeilnehmerSearchTableModel) model;
              Long zeltID = tmodel.getTeilnehmerAt(rowIndex).getZeltId();
              if(zeltID != null) {
                z = database.getZelt(zeltID);
              }
            } else if(model instanceof ZeltSearchTableModel) {
              ZeltSearchTableModel zmodel = (ZeltSearchTableModel) model;
              z = zmodel.getZeltAt(rowIndex);
            }
          }
          if (z != null) {
            informListeners();
          }
        }
      });
      panel_2.add(btnNewButton, BorderLayout.EAST);
  }

  public Zelt getZelt() {
    return z;
  } 
}
