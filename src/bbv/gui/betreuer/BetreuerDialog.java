package bbv.gui.betreuer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bbv.basics.Betreuer;
import bbv.gui.ResponsiveDialog;
import helper.ImageUtilities;


public class BetreuerDialog extends ResponsiveDialog {

  private JFrame parent;

  private JTextField textField_name;
  private JTextField textField_bild;
  private JLabel lblPreview;
  private JButton button_add;
  private JButton button_cancel;
  private JButton button_loadImage;

  private Dimension pictureDimension = new Dimension(290, 259); 

  public BetreuerDialog(JFrame frame) {
    this.parent = frame;
    setupGUI(false); 
    registerListeners();
  }

  public BetreuerDialog(JFrame frame, Betreuer selectedBetreuer) {
    this.parent = frame;
    setupGUI(true); 
    registerListeners();
    textField_name.setText(selectedBetreuer.getName());
    textField_bild.setText(selectedBetreuer.getPicture());
    showImage(selectedBetreuer.getPicture());
  }

  private void setupGUI(boolean existingBetreuer) {
    setResizable(false);
    setBounds(100, 100, 750, 500);
    setVisible(true);

    JLabel lblName = new JLabel("Name:");
    JLabel lblBild = new JLabel("Bild:");

    if(existingBetreuer) {
      button_add = new JButton("Speichern");
    } else {
      button_add = new JButton("Hinzufügen");
    }

    button_cancel = new JButton("Abbrechen");
    button_loadImage = new JButton("Laden");

    textField_name = new JTextField();
    textField_name.setColumns(10);

    lblPreview = new JLabel("");
    lblPreview.setIcon(new ImageIcon(new ImageIcon("./data/default_person.png").getImage().getScaledInstance(290, 259, Image.SCALE_DEFAULT)));

    textField_bild = new JTextField();
    textField_bild.setColumns(10);

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

  private void registerListeners() {
    button_add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(textField_name.getText().isEmpty()) { 
          JOptionPane.showMessageDialog(parent,
              "Bitte gib den Namen des Betreuers an.",
              "Fehlende Angabe",
              JOptionPane.WARNING_MESSAGE); 
        } else {
          informListeners();
          dispose();
        }
      }
    });

    button_cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    button_loadImage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser c = new JFileChooser();
        int rVal = c.showOpenDialog(BetreuerDialog.this);
        if(rVal == JFileChooser.APPROVE_OPTION) {
          textField_bild.setText(c.getSelectedFile().getAbsolutePath());
          showImage(c.getSelectedFile().getAbsolutePath());
        }
      }
    });

  }

  private void showImage(String path) {
    if(ImageUtilities.imageExists(path)) {
      ImageIcon icon = new ImageIcon(path);
      Dimension scaledDimension = ImageUtilities.getScaledDimension(new Dimension(icon.getIconWidth(), icon.getIconHeight()),  pictureDimension);
      lblPreview.setIcon(new ImageIcon(icon.getImage().getScaledInstance((int) scaledDimension.getWidth(), (int) scaledDimension.getHeight(), Image.SCALE_DEFAULT)));
      textField_bild.setForeground(Color.black);
    } else {
      lblPreview.setIcon(new ImageIcon(new ImageIcon("./data/default_person.png").getImage().getScaledInstance(290, 259, Image.SCALE_DEFAULT)));
      textField_bild.setForeground(Color.red);
    }
  }

  public Betreuer getBetreuer() {
    Betreuer betreuer = new Betreuer(textField_name.getText(), textField_bild.getText());
    return betreuer;
  }

  public String getBetreuerName() {
    return textField_name.getText();
  }

  public String getBetreuerPicture() {
    return textField_bild.getText();
  }
}
