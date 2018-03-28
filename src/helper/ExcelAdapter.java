package helper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import bbv.basics.Teilnehmer;
/**
 * ExcelAdapter enables Copy-Paste Clipboard functionality on JTables.
 * The clipboard data format used by the adapter is compatible with
 * the clipboard format used by Excel. This provides for clipboard
 * interoperability between enabled JTables and Excel.
 */
public class ExcelAdapter implements ActionListener {
  private String rowstring,value;
  private Clipboard systemClipboard;
  private JTable jTable1 ;


  /**
   * The Excel Adapter is constructed with a
   * JTable on which it enables Copy-Paste and acts
   * as a Clipboard listener.
   */
  public ExcelAdapter(JTable myJTable) {
    jTable1  =  myJTable;
    KeyStroke copy  =  KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);

    KeyStroke paste  =  KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);

    jTable1.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);
    jTable1.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
    systemClipboard  =  Toolkit.getDefaultToolkit().getSystemClipboard();
  }
  /**
   * Public Accessor methods for the Table on which this adapter acts.
   */
  public JTable getJTable() {
    return jTable1;
  }

  public void setJTable(JTable jTable1) {
    this.jTable1 = jTable1;
  }

  /**
   * This method is activated on the Keystrokes we are listening to
   * in this implementation. Here it listens for Copy and Paste ActionCommands.
   * Selections comprising non-adjacent cells result in invalid selection and
   * then copy action cannot be performed.
   * Paste is done by aligning the upper left corner of the selection with the
   * 1st element in the current selection of the JTable.
   */
  public void actionPerformed(ActionEvent e) {
    //    if (e.getActionCommand().compareTo("Copy") == 0) {
    //      StringBuffer sbf = new StringBuffer();
    //      // Check to ensure we have selected only a contiguous block of
    //      // cells
    //      int numcols = jTable1.getSelectedColumnCount();
    //      int numrows = jTable1.getSelectedRowCount();
    //      int[] rowsselected = jTable1.getSelectedRows();
    //      int[] colsselected = jTable1.getSelectedColumns();
    //      if (!((numrows-1 == rowsselected[rowsselected.length-1]-rowsselected[0] &&
    //          numrows == rowsselected.length) &&
    //          (numcols-1 == colsselected[colsselected.length-1]-colsselected[0] &&
    //          numcols == colsselected.length)))
    //      {
    //        JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
    //            "Invalid Copy Selection",
    //            JOptionPane.ERROR_MESSAGE);
    //        return;
    //      }
    //      for (int i = 0;i<numrows;i++) {
    //        for (int j = 0;j<numcols;j++) {
    //          sbf.append(jTable1.getValueAt(rowsselected[i],colsselected[j]));
    //          if (j<numcols-1) sbf.append("\t");
    //        }
    //        sbf.append("\n");
    //      }
    //      stsel   =  new StringSelection(sbf.toString());
    //      systemClipboard  =  Toolkit.getDefaultToolkit().getSystemClipboard();
    //      systemClipboard.setContents(stsel,stsel);
    //    }
    if (e.getActionCommand().compareTo("Paste") == 0) {
      try {
        List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
        String inputData =  (String)(systemClipboard.getContents(this).getTransferData(DataFlavor.stringFlavor));
        StringTokenizer rowSplitter = new StringTokenizer(inputData, "\n");
        while(rowSplitter.hasMoreTokens()) {
          rowstring = rowSplitter.nextToken();
          StringTokenizer valueSplitter = new StringTokenizer(rowstring, "\t");
          String name = "";
          String geschlecht = "";
          String alter = "";
          String wunsch = "";
          for(int j = 0; valueSplitter.hasMoreTokens(); j++) {
            value = (String)valueSplitter.nextToken();
            if(value ==  null) {
              value = "";
            }
            switch(j) {
            case 0:
              name = value;
              break;
            case 1:
              geschlecht = value;
              break;
            case 2:
              alter = value;
              break;
            case 3:
              wunsch = value;
              break;
            }
          }
          teilnehmerList.add(new Teilnehmer(name, geschlecht, Integer.parseInt(alter), wunsch));
        }
        TeilnehmerImportTableModel model = (TeilnehmerImportTableModel)jTable1.getModel();
        model.add(teilnehmerList);
      }
      catch(Exception ex){ex.printStackTrace();
      }
    }
  }
}