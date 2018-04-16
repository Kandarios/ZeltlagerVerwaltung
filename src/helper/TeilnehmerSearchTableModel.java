package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import bbv.basics.Teilnehmer;
import database.ZeltlagerDB;

public class TeilnehmerSearchTableModel extends AbstractTableModel {
  
  protected static final String[] COLUMN_NAMES = {
      "Name",
      "Geschlecht",
      "Alter",
      "Zelt", 
      "Betreuer",
      "Abgereist", 
      "Abreisedatum"
  };

  private List<Teilnehmer> rowData;
  private ZeltlagerDB database = ZeltlagerDB.getInstance();
  
  public TeilnehmerSearchTableModel() {
    rowData = new ArrayList<Teilnehmer>();
  }

  public void add(Teilnehmer... pd) {
    add(Arrays.asList(pd));
  }

  public void add(List<Teilnehmer> pd) {
    rowData.addAll(pd);
    if(pd.size() != 0) {
      fireTableRowsInserted(0, 1);      
    }
  }
  
  public void insert(Teilnehmer t, int rowIndex) {
    if(rowIndex == -1) {
      rowData.add(t);
      fireTableRowsInserted(rowData.size() -1, rowData.size() -1);
    } else {
      rowData.add(rowIndex, t);
      fireTableRowsInserted(rowIndex, rowIndex);
    }
  }

  @Override
  public int getRowCount() {
    return rowData.size();
  }

  @Override
  public int getColumnCount() {
    return COLUMN_NAMES.length;
  }

  @Override
  public String getColumnName(int column) {
    return COLUMN_NAMES[column];
  }

  public Teilnehmer getTeilnehmerAt(int row) {
    return rowData.get(row);
  }

  public Teilnehmer removeTeilnehmerAt(int row) {
    fireTableRowsDeleted(0, 1);
    return rowData.remove(row);
  }
  
  
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Teilnehmer pd = getTeilnehmerAt(rowIndex);
    Object value = null;
    switch (columnIndex) {
    case 0: //Name
      value = pd.getName();
      break;
    case 1: //Geschlecht
      value = pd.getGeschlecht();
      break;
    case 2: //Alter
      value = pd.getAlter();
      break;
    case 3: //Zelt
      if(pd.getZeltId() == null) {
        value = "-";
      } else {
        value = database.getZelt(pd.getZeltId()).getName();
      }
      break;
    case 4: // Betreuer
      if(pd.getZeltId() == null) {
        value = "-";
      } else {
        value = database.getZelt(pd.getZeltId()).getBetreuerList();
      }
      break;
    case 5: //Abgereist?
      if(pd.isAbgereist()) {
        value = "Ja";
      } else {
        value = "Nein";
      }
      break;
    case 6: //Abreisedatum
      if(pd.getAbreiseDate() == null) {
        value = "-";
      } else {
        value = pd.getAbreiseDate();
      }
      break;
    }
    return value;
  }

  public boolean removeElement(Object obj) {
    fireTableRowsDeleted(0, 1);
    return rowData.remove(obj);
  }

  public void clear() {
    rowData.clear();
  }

}
