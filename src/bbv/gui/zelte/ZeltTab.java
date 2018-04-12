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
import javax.swing.JOptionPane;
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
import database.ZeltlagerDB;
import helper.ListBetreuerTransferHandler;
import helper.TableTeilnehmerTransferHandler;
import helper.TeilnehmerTableModel;

@SuppressWarnings("serial")
public class ZeltTab extends JPanel {
  private JPanel zeltView; 
  private JPanel inBetweenPannel;
  private JTextField textField;
  private JList<Betreuer> list_betreuer;
  private DefaultListModel<Betreuer> model;
  private JTable table;
  private TeilnehmerTableModel tableModel;
  JLabel lblZeltverwaltung = new JLabel("Zeltverwaltung");
  JButton btnGender = new JButton("");

  private ZeltlagerDB betreuerDB = ZeltlagerDB.getInstance();
  boolean viewIsFemale = true;


  public ZeltTab() {
    initializeGUI();
    update();
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

    table = new JTable(tableModel);
    table.setShowVerticalLines(false);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setDragEnabled(true);
    table.setTransferHandler(new TableTeilnehmerTransferHandler());
    table.setPreferredScrollableViewportSize(new Dimension(300, 100));
    table.setFillsViewportHeight(true);
    
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem deleteItem = new JMenuItem("L�schen");
    deleteItem.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Right-click performed on table and choose DELETE");
        }
    });
    popupMenu.add(deleteItem);
    table.setComponentPopupMenu(popupMenu);    
    panel_teilnehmer.setViewportView(table);

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
        update();
      }
    });
    panel_header.add(btnGender, BorderLayout.EAST);

    JPanel panel = new JPanel();
    panel_header.add(panel, BorderLayout.CENTER);

    
    panel.add(lblZeltverwaltung);
    lblZeltverwaltung.setFont(new Font("Tahoma", Font.BOLD, 16));

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    add(scrollPane, BorderLayout.CENTER);

    inBetweenPannel = new JPanel();
    scrollPane.setViewportView(inBetweenPannel);
    inBetweenPannel.setLayout(new BorderLayout(0, 0));

    zeltView = new JPanel();
    inBetweenPannel.add(zeltView, BorderLayout.CENTER);
    zeltView.setLayout(new BoxLayout(zeltView, BoxLayout.Y_AXIS));

    JPanel panel_addZelt = new JPanel();
    panel_addZelt.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    scrollPane.setColumnHeaderView(panel_addZelt);

    textField = new JTextField();
    panel_addZelt.add(textField);
    textField.setColumns(10);

    JButton btnAddZelt = new JButton("Hinzuf�gen");
    btnAddZelt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(!"".equals(textField.getText())) {
          if(viewIsFemale) {
            betreuerDB.save(new Zelt(textField.getText(), "w"));
          } else {
            betreuerDB.save(new Zelt(textField.getText(), "m"));
          }
          textField.setText("");
          fillZeltView();
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

  public void fillZeltView() {
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
          update();
        }
      });
      zeltView.add(zp); ///TODO Maybe change this to a list of panels like in betreuerview
    }
    zeltView.revalidate();
    zeltView.repaint();
  }

  public void fillTeilnehmerTable() {  //TODO make this gender specific
    String gender = "";
    if(viewIsFemale) {
      gender = "w";
    } else {
      gender = "m";
    }
    tableModel.clear();
    tableModel.add(betreuerDB.getUnusedTeilnehmerList(gender));
    table.updateUI();
  }

  public void update() {
    if(viewIsFemale) {
      btnGender.setIcon(new ImageIcon(new ImageIcon("./data/male.png").getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
      btnGender.setBackground(Color.CYAN);
      lblZeltverwaltung.setText("Zeltverwaltung (Jungen)");
    } else {
      btnGender.setIcon(new ImageIcon(new ImageIcon("./data/female.png").getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT)));
      btnGender.setBackground(Color.PINK);
      lblZeltverwaltung.setText("Zeltverwaltung (M�dchen)");
    }
    viewIsFemale = !viewIsFemale; 
    fillBetreuerList();
    fillZeltView();
    fillTeilnehmerTable();
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
}
