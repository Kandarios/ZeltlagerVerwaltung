package helper;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

/**
 * A mouse listener for a JTable component.
 * @author www.codejava.neet
 *
 */
public class TableSelectionMouseListener extends MouseAdapter {

  private JTable table;

  public TableSelectionMouseListener(JTable table) {
    this.table = table;
  }

  @Override
  public void mousePressed(MouseEvent event) {
    Point point = event.getPoint();
    try {
      int currentRow = table.rowAtPoint(point);
      table.setRowSelectionInterval(currentRow, currentRow);
    } catch (Exception e) {
      table.clearSelection();
    }
  }
}
