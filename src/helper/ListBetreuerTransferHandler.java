package helper;

import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import bbv.basics.Betreuer;

@SuppressWarnings("serial")
public class ListBetreuerTransferHandler extends TransferHandler {

  @Override
  public boolean canImport(TransferSupport support) {
    return support.isDataFlavorSupported(BetreuerTransferable.BETREUER_DATA_FLAVOR);
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
        Object value = transferable.getTransferData(BetreuerTransferable.BETREUER_DATA_FLAVOR);
        if (value instanceof Betreuer) {
          Component component = support.getComponent();
          if(component instanceof JList) {
            JList<Betreuer> targetList = (JList<Betreuer>)component;
            DefaultListModel<Betreuer> listModel = (DefaultListModel<Betreuer>)targetList.getModel();
            JList.DropLocation dl = (JList.DropLocation)support.getDropLocation();
            int index = dl.getIndex();
            boolean insert = dl.isInsert();

            if (insert) {
              listModel.add(index++, (Betreuer) value);
            } else {
              if (index < listModel.getSize()) {
                listModel.set(index++, (Betreuer) value);
              } else {
                listModel.add(index++, (Betreuer) value);
              }
            }
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
    if (c instanceof JList) {
      @SuppressWarnings("unchecked")
      JList<Betreuer> list = (JList<Betreuer>)c;
      Object value = list.getSelectedValue();
      if (value instanceof Betreuer) {
        Betreuer li = (Betreuer) value;
        t = new BetreuerTransferable(li);
      }
    }
    return t;
  }


  @Override
  protected void exportDone(JComponent c, Transferable data, int action) {

    if (c instanceof JList) {
      @SuppressWarnings("unchecked")
      JList<Betreuer> source = (JList<Betreuer>) c;
      DefaultListModel<Betreuer> listModel  = (DefaultListModel<Betreuer>)source.getModel();
      if (action == TransferHandler.MOVE) {
        try {
          Object exportedData = (Object) data.getTransferData(BetreuerTransferable.BETREUER_DATA_FLAVOR);
          removeFromListModel(listModel, exportedData);
        }
        catch (UnsupportedFlavorException | IOException e) {

        }

      }
    }
  }

  private void removeFromListModel( DefaultListModel<Betreuer>listModel, Object dataToRemove) {
      boolean removedSuccessfully = listModel.removeElement(dataToRemove);
      if (!removedSuccessfully) {
        System.err.println("Source model did not contain exported data");
      }
    
  }
}
