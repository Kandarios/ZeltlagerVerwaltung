package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import bbv.basics.Zelt;

public class ZeltSearchTableModel extends AbstractTableModel {
  
  protected static final String[] COLUMN_NAMES = {
      "Name",
      "Betreuer",
      "Teilnehmer"
  };

  private List<Zelt> rowData;
  
  public ZeltSearchTableModel() {
    rowData = new ArrayList<Zelt>();
  }

  public void add(Zelt... pd) {
    add(Arrays.asList(pd));
  }

  public void add(List<Zelt> pd) {
    rowData.addAll(pd);
    if(pd.size() != 0) {
      fireTableRowsInserted(0, 1);      
    }
  }
  
  public void insert(Zelt z, int rowIndex) {
    if(rowIndex == -1) {
      rowData.add(z);
      fireTableRowsInserted(rowData.size() -1, rowData.size() -1);
    } else {
      rowData.add(rowIndex, z);
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

  public Zelt getZeltAt(int row) {
    return rowData.get(row);
  }

  public Zelt removeZeltAt(int row) {
    fireTableRowsDeleted(0, 1);
    return rowData.remove(row);
  }
  
  
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Zelt pd = getZeltAt(rowIndex);
    Object value = null;
    switch (columnIndex) {
    case 0: //Name
      value = pd.getName();
      break;
    case 1: //Betreuer
      value = pd.getBetreuerList();
      break;
    case 2: //Teilnehmer
      value = pd.getTeilnehmerList();
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
