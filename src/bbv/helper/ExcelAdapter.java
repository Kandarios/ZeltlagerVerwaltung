package bbv.helper;

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


public class ExcelAdapter implements ActionListener {
  private String rowstring,value;
  private Clipboard systemClipboard;
  private JTable jTable1 ;


  public ExcelAdapter(JTable myJTable) {
    jTable1  =  myJTable;
    KeyStroke copy  =  KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
    KeyStroke paste  =  KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
    jTable1.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);
    jTable1.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
    systemClipboard  =  Toolkit.getDefaultToolkit().getSystemClipboard();
  }

  public JTable getJTable() {
    return jTable1;
  }

  public void setJTable(JTable jTable1) {
    this.jTable1 = jTable1;
  }

  public void actionPerformed(ActionEvent e) {
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