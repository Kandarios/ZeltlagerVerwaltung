package helper;

import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import bbv.basics.Teilnehmer;

@SuppressWarnings("serial")
public class TableTeilnehmerTransferHandler extends TransferHandler {

  @Override
  public boolean canImport(TransferSupport support) {
    return support.isDataFlavorSupported(TeilnehmerTransferable.TEILNEHMER_DATA_FLAVOR);
  }

  @Override
  public boolean importData(TransferSupport support) {
    if (!support.isDrop()) {
      return false;
    }
    boolean accept = false; 

    if (canImport(support)) {
      try {
        Transferable transferable = support.getTransferable();
        Object value = transferable.getTransferData(TeilnehmerTransferable.TEILNEHMER_DATA_FLAVOR);
        if (value instanceof Teilnehmer) {
          Component component = support.getComponent();
          if(component instanceof JTable) {
            JTable targetTable = (JTable)component;
            TeilnehmerTableModel tableModel = (TeilnehmerTableModel)targetTable.getModel();
            JTable.DropLocation dl = (JTable.DropLocation)support.getDropLocation();
            int index = dl.getRow();
            tableModel.add((Teilnehmer) value);
          }
          accept = true;
        }
      } catch (Exception exp) {
        exp.printStackTrace();
      }
    }
    return accept;
  }

  @Override
  public int getSourceActions(JComponent c) {
    return TransferHandler.COPY_OR_MOVE;
  }


  @Override
  protected Transferable createTransferable(JComponent c) {
    Transferable t = null;
    if (c instanceof JTable) {
      JTable table = (JTable)c;
      TeilnehmerTableModel tableModel = (TeilnehmerTableModel)table.getModel();
      Object value = tableModel.getTeilnehmerAt(table.getSelectedRow());
      if (value instanceof Teilnehmer) {
        Teilnehmer li = (Teilnehmer) value;
        t = new TeilnehmerTransferable(li);
      }
    }
    return t;
  }


  @Override
  protected void exportDone(JComponent c, Transferable data, int action) {
    if (c instanceof JTable) {
      JTable source = (JTable) c;
      TeilnehmerTableModel tableModel = (TeilnehmerTableModel)source.getModel();
      if (action == TransferHandler.MOVE) {
        try {
          Object exportedData = (Object) data.getTransferData(TeilnehmerTransferable.TEILNEHMER_DATA_FLAVOR);
          tableModel.removeElement(exportedData);
        }
        catch (UnsupportedFlavorException | IOException e) {

        }
      }
    }
  }
}
