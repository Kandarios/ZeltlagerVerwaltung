package bbv.gui.zelte;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;

import bbv.basics.Betreuer;
import bbv.basics.Teilnehmer;
import database.ZeltlagerDB;
import helper.ListBetreuerTransferHandler;
import helper.TableTeilnehmerTransferHandler;
import helper.TeilnehmerTableModel;

public class ZeltTab extends JPanel {
  private DefaultListModel<Betreuer> model;
  private JTable table;
  private JList<Betreuer> list_betreuer;
  private ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  private JPanel zeltView; 
  private JPanel inBetweenPannel;

  public ZeltTab() {
    initializeGUI();
    fillBetreuerList();
    fillZeltView();
    setOpaque(true);
//    activeTeilnehmer();
  }

  private void initializeGUI() {
    setLayout(new BorderLayout(0, 0));

    
    JPanel arround_teilnehmer = new JPanel();
    add(arround_teilnehmer, BorderLayout.WEST);
    arround_teilnehmer.setLayout(new BorderLayout(0, 0));
    
    JLabel lblNewLabel = new JLabel("Teilnehmer:");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
    arround_teilnehmer.add(lblNewLabel, BorderLayout.NORTH);
    
    JScrollPane panel_teilnehmer = new JScrollPane();
    arround_teilnehmer.add(panel_teilnehmer, BorderLayout.CENTER);
    
    TeilnehmerTableModel tableModel = new TeilnehmerTableModel();
    
    tableModel.add(new Teilnehmer("Kind1", "m", 8, "mit Kind2"));
    tableModel.add(new Teilnehmer("Kind2", "m", 8, "mit Kind1"));
    tableModel.add(new Teilnehmer("Kind3", "m", 8, ""));
    tableModel.add(new Teilnehmer("Kind4", "m", 9, "mit Kind5"));
    tableModel.add(new Teilnehmer("Kind5", "m", 9, "mit Kind4"));
    table = new JTable(tableModel);
    table.setShowVerticalLines(false);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setDragEnabled(true);
    table.setTransferHandler(new TableTeilnehmerTransferHandler());
    table.setPreferredScrollableViewportSize(new Dimension(300, 100));
    table.setFillsViewportHeight(true);
   
    panel_teilnehmer.setViewportView(table);
    
    
    
    JPanel panel_betreuer = new JPanel();
    add(panel_betreuer, BorderLayout.EAST);
    panel_betreuer.setLayout(new BorderLayout(0, 0));
    
    JLabel lblNewLabel_1 = new JLabel("Betreuer:                ");
    lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
    panel_betreuer.add(lblNewLabel_1, BorderLayout.NORTH);

    list_betreuer = new JList<Betreuer>();
    panel_betreuer.add(list_betreuer);

    JPanel panel_header = new JPanel();
    panel_header.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(panel_header, BorderLayout.NORTH);

    JLabel lblZeltverwaltung = new JLabel("Zeltverwaltung");
    lblZeltverwaltung.setFont(new Font("Tahoma", Font.BOLD, 16));
    panel_header.add(lblZeltverwaltung);


    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(scrollPane, BorderLayout.CENTER);

    inBetweenPannel = new JPanel();
    scrollPane.setViewportView(inBetweenPannel);
    inBetweenPannel.setLayout(new BorderLayout(0, 0));

    zeltView = new JPanel();
    inBetweenPannel.add(zeltView, BorderLayout.CENTER);
    zeltView.setLayout(new GridLayout(0, 1, 0, 0));
  }


  private void fillBetreuerList() {
    List<Betreuer> betreuerList = betreuerDB.getBetreuerList();
    model = new DefaultListModel<Betreuer>();
    for(Betreuer b : betreuerList) {
      model.addElement(b);
    }
    list_betreuer.setModel(model);
    list_betreuer.setDragEnabled(true);
    list_betreuer.setTransferHandler(new ListBetreuerTransferHandler());
    list_betreuer.setDropMode(DropMode.INSERT);
//    BetreuerMoveHandler.createFor(list_betreuer);
  }


  public void fillZeltView() {
    ZeltPanel panel = new ZeltPanel();
    zeltView.add(panel);
    ZeltPanel panel2 = new ZeltPanel();
    zeltView.add(panel2);
    ZeltPanel panel3 = new ZeltPanel();
    zeltView.add(panel3);
    ZeltPanel panel4 = new ZeltPanel();
    zeltView.add(panel4);
    ZeltPanel panel5 = new ZeltPanel();
    zeltView.add(panel5);
    ZeltPanel panel6 = new ZeltPanel();
    zeltView.add(panel6);

  }

//  private void activeTeilnehmer() {
//    RowFilter<TeilnehmerTableModel, Integer> rf = new RowFilter<TeilnehmerTableModel, Integer>() {
//
//      @Override
//      public boolean include(RowFilter.Entry<? extends TeilnehmerTableModel, ? extends Integer> entry) {
//        int row = entry.getIdentifier();
//        Teilnehmer pd = entry.getModel().geTeilnehmerAt(row);
//        return pd.getActiveStatus() == 1;
//      }
//
//    };
//    ((TableRowSorter) table.getRowSorter()).setRowFilter(rf);
//    setTeilnehmerCount();
//  }
//
//  private void inActivePatients() {
//    RowFilter<TeilnehmerTableModel, Integer> rf = new RowFilter<TeilnehmerTableModel, Integer>() {
//
//      @Override
//      public boolean include(RowFilter.Entry<? extends TeilnehmerTableModel, ? extends Integer> entry) {
//        int row = entry.getIdentifier();
//        Teilnehmer pd = entry.getModel().geTeilnehmerAt(row);
//        return pd.getActiveStatus() != 1;
//      }
//
//    };
//    ((TableRowSorter) patients.getRowSorter()).setRowFilter(rf);
//    setTeilnehmerCount();
//  }
//
//  public void allPatients() {
//    ((TableRowSorter) patients.getRowSorter()).setRowFilter(null);
//    setPatientCount();
//  }



}
