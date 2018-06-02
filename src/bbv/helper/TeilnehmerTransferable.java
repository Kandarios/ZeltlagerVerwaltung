package bbv.helper;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import bbv.basics.Teilnehmer;

public class TeilnehmerTransferable implements Transferable {

  public static final DataFlavor TEILNEHMER_DATA_FLAVOR = new DataFlavor(Teilnehmer.class, "Teilnehmer");
  private Teilnehmer teilnehmer;

  public TeilnehmerTransferable(Teilnehmer teilnehmer) {
      this.teilnehmer = teilnehmer;
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{TEILNEHMER_DATA_FLAVOR};
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
      return flavor.equals(TEILNEHMER_DATA_FLAVOR);
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
      return teilnehmer;
  }
}
