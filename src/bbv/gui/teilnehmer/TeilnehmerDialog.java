package bbv.gui.teilnehmer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;

import bbv.basics.Teilnehmer;
import bbv.gui.ResponsiveDialog;
import helper.ExcelAdapter;
import helper.TeilnehmerImportTableModel;


@SuppressWarnings("serial")
public class TeilnehmerDialog extends ResponsiveDialog {
  private JButton button_add;
  private JButton button_cancel;

  private JPanel panel;
  private JPanel panel_1;
  private JTable table;
  private JScrollPane scrollPane;

  public TeilnehmerDialog() {
    setupGUI(); 
  }

  private void setupGUI() {
    setBounds(100, 100, 738, 529);
    setVisible(true);

    button_add = new JButton("Hinzufügen");
    getContentPane().setLayout(new BorderLayout(0, 0));

    panel = new JPanel();
    panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    getContentPane().add(panel, BorderLayout.SOUTH);
    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    button_add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        informListeners("Aprooved");
        dispose();
      }
    });
    panel.add(button_add);

    button_cancel = new JButton("Abbrechen");
    button_cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    panel.add(button_cancel);

    panel_1 = new JPanel();
    getContentPane().add(panel_1, BorderLayout.CENTER);
    panel_1.setLayout(new BorderLayout(0, 0));

    scrollPane = new JScrollPane();
    panel_1.add(scrollPane);

    table = new JTable(new TeilnehmerImportTableModel());
    table.setFillsViewportHeight(true);
    table.setShowVerticalLines(false);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ExcelAdapter copyAdapter = new ExcelAdapter(table);
    scrollPane.setViewportView(table);
  }

  public List<Teilnehmer> getTeilnehmer() {
    TeilnehmerImportTableModel model = (TeilnehmerImportTableModel) table.getModel();
    return model.getAllTeilnehmer();
  }
}
