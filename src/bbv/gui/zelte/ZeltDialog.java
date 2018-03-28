package bbv.gui.zelte;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bbv.basics.Zelt;

public class ZeltDialog extends JDialog {

//  private JFrame parent;

  private JTextField textField_name;
  private JTextField textField_bild;
  private JTextField textField_zelt;
  private JLabel lblPreview;
  private JButton button_add;
  private JButton button_cancel;
  private JButton button_loadImage;

  private Dimension pictureDimension = new Dimension(290, 259); 
  private List<ActionListener> listeners = new ArrayList<ActionListener>();


  /**
   * @wbp.parser.constructor
   */
  public ZeltDialog(//JFrame frame
      ) {
//    this.parent = frame;
    setupGUI(false); 
//    registerListeners();
  }

  public ZeltDialog(JFrame frame, Zelt selectedZelt) {
//    this.parent = frame;
    setupGUI(true); 
//    registerListeners();
    textField_name.setText(selectedZelt.getName());
//    textField_zelt.setText(selectedZelt.getZelt());
//    textField_bild.setText(selectedZelt.getPicture());
//    showImage(selectedZelt.getPicture());
  }

  private void setupGUI(boolean existingZelt) {
    setResizable(false);
    setBounds(100, 100, 750, 500);
    setVisible(true);

    JLabel lblName = new JLabel("Name:");
    JLabel lblBild = new JLabel("Bild:");
    JLabel lblZelt = new JLabel("Zelt:");

    if(existingZelt) {
      button_add = new JButton("Speichern");
    } else {
      button_add = new JButton("Hinzuf�gen");
    }

    button_cancel = new JButton("Abbrechen");
    button_loadImage = new JButton("Laden");

    textField_name = new JTextField();
    textField_name.setColumns(10);

    lblPreview = new JLabel("");
    lblPreview.setIcon(new ImageIcon(new ImageIcon("./data/default_person.png").getImage().getScaledInstance(290, 259, Image.SCALE_DEFAULT)));

    textField_bild = new JTextField();
    textField_bild.setColumns(10);

    textField_zelt = new JTextField();
    textField_zelt.setColumns(10);

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
            .addGap(0)
            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(button_add)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(button_cancel)
                    .addGap(23))
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                            .addGap(43)
                            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(lblBild)
                                    .addGap(31))
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(lblName)
                                    .addGap(18)))
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(textField_bild, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(button_loadImage)
                                    .addGap(6))
                                .addComponent(textField_name, GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(419)
                            .addComponent(lblPreview, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))
                    .addGap(27))))
        .addGroup(groupLayout.createSequentialGroup()
            .addContainerGap(43, Short.MAX_VALUE)
            .addComponent(lblZelt, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
            .addGap(18)
            .addComponent(textField_zelt, GroupLayout.PREFERRED_SIZE, 618, GroupLayout.PREFERRED_SIZE)
            .addGap(27))
        );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
            .addGap(24)
            .addComponent(lblPreview, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(textField_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblName))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(lblZelt)
                .addComponent(textField_zelt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(9)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lblBild)
                .addComponent(textField_bild, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(button_loadImage))
            .addGap(28)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(button_cancel)
                .addComponent(button_add))
            .addGap(14))
        );
    getContentPane().setLayout(groupLayout);
  }

//  private void registerListeners() {
////    button_add.addActionListener(new ActionListener() {
////      public void actionPerformed(ActionEvent e) {
////        if(textField_name.getText().isEmpty()) { 
////          JOptionPane.showMessageDialog(parent,
////              "Bitte gib den Namen des Zelts an.",
////              "Fehlende Angabe",
////              JOptionPane.WARNING_MESSAGE); 
////        } else {
////          informListeners();
////          dispose();
////        }
////      }
////    });
////
////    button_cancel.addActionListener(new ActionListener() {
////      public void actionPerformed(ActionEvent e) {
////        dispose();
////      }
////    });
////
////    button_loadImage.addActionListener(new ActionListener() {
////      public void actionPerformed(ActionEvent e) {
////        JFileChooser c = new JFileChooser();
////        int rVal = c.showOpenDialog(ZeltDialog.this);
////        if(rVal == JFileChooser.APPROVE_OPTION) {
////          textField_bild.setText(c.getSelectedFile().getAbsolutePath());
////          showImage(c.getSelectedFile().getAbsolutePath());
////        }
////      }
////    });
//
//  }
//
//  private void showImage(String path) {
////    if(ImageUtilities.imageExists(path)) {
////      ImageIcon icon = new ImageIcon(path);
////      Dimension scaledDimension = ImageUtilities.getScaledDimension(new Dimension(icon.getIconWidth(), icon.getIconHeight()),  pictureDimension);
////      lblPreview.setIcon(new ImageIcon(icon.getImage().getScaledInstance((int) scaledDimension.getWidth(), (int) scaledDimension.getHeight(), Image.SCALE_DEFAULT)));
////      textField_bild.setForeground(Color.black);
////    } else {
////      lblPreview.setIcon(new ImageIcon(new ImageIcon("./data/default_person.png").getImage().getScaledInstance(290, 259, Image.SCALE_DEFAULT)));
////      textField_bild.setForeground(Color.red);
////    }
//  }
//
//  public Zelt getZelt() {
////    Zelt zelt = new Zelt(textField_name.getText(), textField_zelt.getText(), textField_bild.getText());
////    zelt.setZeltId(null);
//    return new Zelt();
//  }
//
//  public String getZeltName() {
//    return textField_name.getText();
//  }
//
//  public String getZeltZelt() {
//    return textField_zelt.getText();
//  }
//
//  public String getZeltPicture() {
//    return  textField_bild.getText();
//  }
//
//  public void addActionListener(ActionListener listener) {
//    listeners.add(listener);
//  }
//
//  private void informListeners() {
////    for(ActionListener listener : listeners) {
////      ActionEvent event = new ActionEvent(this, 0, "Aproved");
////      listener.actionPerformed(event);;
////    }
//  }
}