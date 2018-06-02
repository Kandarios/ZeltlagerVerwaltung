package bbv.helper;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import bbv.basics.Betreuer;

public class BetreuerTransferable implements Transferable {

  public static final DataFlavor BETREUER_DATA_FLAVOR = new DataFlavor(Betreuer.class, "Betreuer");
  private Betreuer betreuer;

  public BetreuerTransferable(Betreuer betreuer) {
      this.betreuer = betreuer;
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{BETREUER_DATA_FLAVOR};
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
      return flavor.equals(BETREUER_DATA_FLAVOR);
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
      return betreuer;
  }
}
