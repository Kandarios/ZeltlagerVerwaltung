package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import bbv.basics.Teilnehmer;

public class TeilnehmerTableModel extends AbstractTableModel {

  protected static final String[] COLUMN_NAMES = {
      "Name",
      "Alter",
      "Wunsch"
  };

  private List<Teilnehmer> rowData;

  public TeilnehmerTableModel() {
    rowData = new ArrayList<Teilnehmer>();
  }

  public void add(Teilnehmer... pd) {
    add(Arrays.asList(pd));
    fireTableRowsInserted(0, 1);
  }

  public void add(List<Teilnehmer> pd) {
    rowData.addAll(pd);
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
    case 0:
      value = pd.getName();
      break;
    case 1:
      value = pd.getAlter();
      break;
    case 2:
      value = pd.getWunsch();
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
