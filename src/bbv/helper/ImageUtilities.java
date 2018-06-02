package bbv.helper;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;

public class ImageUtilities {
  public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

    int original_width = imgSize.width;
    int original_height = imgSize.height;
    int bound_width = boundary.width;
    int bound_height = boundary.height;
    int new_width = original_width;
    int new_height = original_height;

    if (original_width > bound_width) {
      new_width = bound_width;
      new_height = (new_width * original_height) / original_width;
    }

    if (new_height > bound_height) {
      new_height = bound_height;
      new_width = (new_height * original_width) / original_height;
    }

    return new Dimension(new_width, new_height);
  }
  
  public static boolean imageExists(String path) {
    ImageIcon icon = new ImageIcon(path);
    if(icon.getIconHeight() == -1) {
      return false;
    }
    return true;
  }
  
  public static void storeImage(String path) {
    File imageFile = new File(path);
    try {
      Files.copy(imageFile.toPath(),
          (new File("./data/" + imageFile.getName())).toPath(),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
