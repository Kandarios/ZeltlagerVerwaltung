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
import bbv.helper.ImageUtilities;


public class BetreuerDialog extends ResponsiveDialog {

  private JFrame parent;

  private JTextField textField_name;
  private JTextField textField_bild;
  private JLabel lblPreview;
  private JButton button_add;
  private JButton button_cancel;
  private JButton button_loadImage;

  private Dimension pictureDimension = new Dimension(290, 259); 
  private JTextField textField_telefon;
  private JLabel lblTelefon;

  /**
   * @wbp.parser.constructor
   */
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

    textField_telefon = new JTextField();
    textField_telefon.setColumns(10);

    lblTelefon = new JLabel("Telefon:");

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(button_add)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(button_cancel))
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(lblBild)
                                .addGap(31))
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(lblName)
                                .addGap(18)))
                        .addComponent(lblTelefon, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(textField_telefon, GroupLayout.PREFERRED_SIZE, 642, GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                            .addComponent(textField_name, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(textField_bild, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(button_loadImage))))))
            .addGap(40))
        .addGroup(groupLayout.createSequentialGroup()
            .addGap(393)
            .addComponent(lblPreview, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addGap(53))
        );
    groupLayout.setVerticalGroup(
        groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
            .addContainerGap(28, Short.MAX_VALUE)
            .addComponent(lblPreview, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.UNRELATED)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lblName)
                .addComponent(textField_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lblBild)
                .addComponent(textField_bild, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(button_loadImage))
            .addPreferredGap(ComponentPlacement.RELATED)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(textField_telefon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblTelefon))
            .addGap(11)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                .addComponent(button_cancel)
                .addComponent(button_add))
            .addContainerGap())
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
    betreuer.setTelefon(textField_telefon.getText());
    return betreuer;
  }

  public String getBetreuerName() {
    return textField_name.getText();
  }

  public String getBetreuerPicture() {
    return textField_bild.getText();
  }
}
