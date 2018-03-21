package bbv.gui.zelte;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import bbv.basics.Betreuer;
import helper.ListBetreuerTransferHandler;
import helper.TableTeilnehmerTransferHandler;
import helper.TeilnehmerTableModel;

public class ZeltPanel extends JPanel {
  private JList<Betreuer> list_betreuer;
  private JTable table_teilnehmer;
  
  
  public ZeltPanel() {
    initializeGUI();
//    showBetreuer();
  }
  
  public void initializeGUI() {
    setLayout(new BorderLayout(0, 0));
    setPreferredSize(new Dimension(300, 300));
    
    JPanel panel = new JPanel();
    add(panel, BorderLayout.CENTER);
    panel.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_1 = new JPanel();
    panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    panel.add(panel_1, BorderLayout.NORTH);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    JLabel lblNewLabel_1 = new JLabel("Betreuer:");
    panel_1.add(lblNewLabel_1, BorderLayout.WEST);
    
    DefaultListModel<Betreuer> listModel1 = new DefaultListModel<>();
    list_betreuer = new JList<Betreuer>();
    listModel1.addListDataListener(new ListDataListener() {
      
      @Override
      public void intervalRemoved(ListDataEvent e) {
        System.out.println("Betreuer entfernt");
        
      }
      
      @Override
      public void intervalAdded(ListDataEvent e) {
        System.out.println("Betreuer hinzugefügt");
      }
      
      @Override
      public void contentsChanged(ListDataEvent e) {
        System.out.println("Betreuer verändert");
        
      }
    });
    list_betreuer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    list_betreuer.setDropMode(DropMode.INSERT);
    list_betreuer.setModel(listModel1);
    list_betreuer.setDragEnabled(true);
    list_betreuer.setTransferHandler(new ListBetreuerTransferHandler());
    panel_1.add(list_betreuer);
    

    JScrollPane scrollPanelTeilnehmer = new JScrollPane();
    panel.add(scrollPanelTeilnehmer, BorderLayout.CENTER);
    
    TeilnehmerTableModel tableModel = new TeilnehmerTableModel();
    table_teilnehmer = new JTable(tableModel);
    table_teilnehmer.setFillsViewportHeight(true);
    table_teilnehmer.setShowVerticalLines(false);
    table_teilnehmer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table_teilnehmer.setDragEnabled(true);
    table_teilnehmer.setDropMode(DropMode.INSERT);
    table_teilnehmer.setTransferHandler(new TableTeilnehmerTransferHandler());
    scrollPanelTeilnehmer.setViewportView(table_teilnehmer);
    table_teilnehmer.setPreferredScrollableViewportSize(new Dimension(400, 100));

    
    
    JPanel panel_2 = new JPanel();
    add(panel_2, BorderLayout.NORTH);
    
    JLabel lblNewLabel = new JLabel("Zelt: ");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
    panel_2.add(lblNewLabel);
  }
  
  public void showBetreuer() {
//
  }

}
