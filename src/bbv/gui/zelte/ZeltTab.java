package bbv.gui.zelte;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import bbv.basics.Betreuer;
import bbv.basics.Teilnehmer;
import bbv.basics.Zelt;
import bbv.database.ZeltlagerDB;
import bbv.helper.ListBetreuerTransferHandler;
import bbv.helper.TableSelectionMouseListener;
import bbv.helper.TableTeilnehmerTransferHandler;
import bbv.helper.TeilnehmerTableModel;

@SuppressWarnings("serial")
public class ZeltTab extends JPanel {
  private JPanel zeltView; 
  private JTextField textField;
  private JList<Betreuer> list_betreuer;
  private DefaultListModel<Betreuer> model;
  private JTable tableTeilnehmer;
  private TeilnehmerTableModel tableModel;
  private JLabel lblZeltverwaltung = new JLabel("Zeltverwaltung");
  private JButton btnGender = new JButton("");
  private JScrollPane zeltScrollPane = new JScrollPane();

  private ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  boolean viewIsFemale = true;


  public ZeltTab() {
    initializeGUI();
    updateView();
    addInsertListeners();
  }

  private void initializeGUI() {
    setLayout(new BorderLayout(0, 0));
    setOpaque(true);

    JPanel arround_teilnehmer = new JPanel();
    arround_teilnehmer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(arround_teilnehmer, BorderLayout.WEST);
    arround_teilnehmer.setLayout(new BorderLayout(0, 0));

    JLabel lblNewLabel = new JLabel("Teilnehmer:");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
    arround_teilnehmer.add(lblNewLabel, BorderLayout.NORTH);

    JScrollPane panel_teilnehmer = new JScrollPane();
    arround_teilnehmer.add(panel_teilnehmer, BorderLayout.CENTER);

    tableModel = new TeilnehmerTableModel();

    tableTeilnehmer = new JTable(tableModel);
    tableTeilnehmer.setShowVerticalLines(false);
    tableTeilnehmer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableTeilnehmer.setDragEnabled(true);
    tableTeilnehmer.setTransferHandler(new TableTeilnehmerTransferHandler());
    tableTeilnehmer.setPreferredScrollableViewportSize(new Dimension(300, 100));
    tableTeilnehmer.setFillsViewportHeight(true);
    tableTeilnehmer.addMouseListener(new TableSelectionMouseListener(tableTeilnehmer));

    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem("Löschen");
    deleteItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        int index = tableTeilnehmer.getSelectedRow();
        betreuerDB.delete(tableModel.getTeilnehmerAt(index));
        fillTeilnehmerTable();
      }
    });
    popupMenu.add(deleteItem);
    tableTeilnehmer.setComponentPopupMenu(popupMenu);    
    panel_teilnehmer.setViewportView(tableTeilnehmer);

    JPanel panel_betreuer = new JPanel();
    panel_betreuer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(panel_betreuer, BorderLayout.EAST);
    panel_betreuer.setLayout(new BorderLayout(0, 0));

    JLabel lblNewLabel_1 = new JLabel("Betreuer:                ");
    lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
    panel_betreuer.add(lblNewLabel_1, BorderLayout.NORTH);

    list_betreuer = new JList<Betreuer>();
    panel_betreuer.add(list_betreuer);

    model = new DefaultListModel<Betreuer>();
    list_betreuer.setModel(model);
    list_betreuer.setDragEnabled(true);
    list_betreuer.setTransferHandler(new ListBetreuerTransferHandler());
    list_betreuer.setDropMode(DropMode.INSERT);

    JPanel panel_header = new JPanel();
    panel_header.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(panel_header, BorderLayout.NORTH);
    panel_header.setLayout(new BorderLayout(0, 0));

    btnGender.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewIsFemale = !viewIsFemale; 
        updateView();
      }
    });
    panel_header.add(btnGender, BorderLayout.EAST);

    JPanel panel = new JPanel();
    panel_header.add(panel, BorderLayout.CENTER);

    panel.add(lblZeltverwaltung);
    lblZeltverwaltung.setFont(new Font("Tahoma", Font.BOLD, 16));

    zeltScrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(zeltScrollPane, BorderLayout.CENTER);

    zeltView = new JPanel();
    zeltScrollPane.setViewportView(zeltView);
    zeltView.setLayout(new BoxLayout(zeltView, BoxLayout.Y_AXIS));

    JPanel panel_addZelt = new JPanel();
    panel_addZelt.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    zeltScrollPane.setColumnHeaderView(panel_addZelt);

    textField = new JTextField();
    panel_addZelt.add(textField);
    textField.setColumns(10);

    JButton btnAddZelt = new JButton("Hinzufügen");
    btnAddZelt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(!"".equals(textField.getText())) {
          if(viewIsFemale) {
            betreuerDB.save(new Zelt(textField.getText(), "w"));
          } else {
            betreuerDB.save(new Zelt(textField.getText(), "m"));
          }
          textField.setText("");
          fillZeltView(null);
        }
      }
    });
    panel_addZelt.add(btnAddZelt);
  }

  private void fillBetreuerList() {
    model.clear();
    for(Betreuer b : betreuerDB.getUnusedBetreuerList()) {
      model.addElement(b);
    }
  }

  public void fillZeltView(Long searchID) {
    zeltView.removeAll();
    String gender = "";
    if(viewIsFemale) {
      gender = "w";
    } else {
      gender = "m";
    }
    for(Zelt z : betreuerDB.getZeltList(gender)) {
      ZeltPanel zp = new ZeltPanel(z);
      zp.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          updateView();
        }
      });
      if(searchID != null) {
        if(searchID.equals(z.getZeltId())) {
          zeltView.add(zp, 0);
          continue;
        }
      }
      zeltView.add(zp); 
    }
    zeltView.revalidate();
    zeltView.repaint();
  }

  public void fillTeilnehmerTable() { 
    String gender = "";
    if(viewIsFemale) {
      gender = "w";
    } else {
      gender = "m";
    }
    tableModel.clear();
    tableModel.add(betreuerDB.getUnusedTeilnehmerList(gender));
    tableTeilnehmer.updateUI();
  }

  public void updateView() {
    update();
    fillZeltView(null);
  }

  private void update() {
    if(viewIsFemale) {
      btnGender.setIcon(new ImageIcon(new ImageIcon("./data/male.png").getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
      btnGender.setBackground(Color.CYAN);
      lblZeltverwaltung.setText("Zeltverwaltung (Mädchen)");
    } else {
      btnGender.setIcon(new ImageIcon(new ImageIcon("./data/female.png").getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
      btnGender.setBackground(Color.PINK);
      lblZeltverwaltung.setText("Zeltverwaltung (Jungen)");
    }
    fillBetreuerList();
    fillTeilnehmerTable();
  }

  public void updateAfterSearch(Long zeltID) {
    update();
    fillZeltView(zeltID);
  }

  public void addInsertListeners() {
    tableModel.addTableModelListener(new TableModelListener() {

      @Override
      public void tableChanged(TableModelEvent e) {
        if(e.getType() == TableModelEvent.INSERT) {
          Teilnehmer t = tableModel.getTeilnehmerAt(e.getFirstRow());
          betreuerDB.updateTeilnehmerZelt(t.getTeilnehmerId(), null);      
        }
      }
    });

    model.addListDataListener(new ListDataListener() {

      @Override
      public void intervalRemoved(ListDataEvent e) {}

      @Override
      public void intervalAdded(ListDataEvent e) {
        Betreuer addedBetreuer = model.getElementAt(e.getIndex0());
        betreuerDB.updateBetreuerZelt(addedBetreuer.getBetreuerId(), null);
      }

      @Override
      public void contentsChanged(ListDataEvent e) {}
    });
  }

  public void showSearchedZelt(Zelt zelt) {
    if("w".equals(zelt.getGeschlecht())) {
      viewIsFemale = true;
    } else {
      viewIsFemale = false;
    }
    updateAfterSearch(zelt.getZeltId());
  }
}
