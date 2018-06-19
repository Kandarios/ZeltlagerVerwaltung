package bbv.gui.teamer;

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

import bbv.basics.Teamer;
import bbv.gui.ResponsiveDialog;
import bbv.helper.ImageUtilities;

public class TeamerDialog extends ResponsiveDialog {

  private JFrame parent;

  private JTextField textField_name;
  private JTextField textField_bild;
  private JLabel lblPreview;
  private JButton button_add;
  private JButton button_cancel;
  private JButton button_loadImage;
  private JTextField textField_telefon;
  private JTextField textField_position;

  private Dimension pictureDimension = new Dimension(290, 259); 

  public TeamerDialog(JFrame frame) {
    this.parent = frame;
    setupGUI(false); 
    registerListeners();
  }

  public TeamerDialog(JFrame frame, Teamer selectedTeamer) {
    this.parent = frame;
    setupGUI(true); 
    registerListeners();
    textField_name.setText(selectedTeamer.getName());
    textField_bild.setText(selectedTeamer.getPicture());
    showImage(selectedTeamer.getPicture());
  }

  private void setupGUI(boolean existingTeamer) {
    setResizable(false);
    setBounds(100, 100, 750, 500);
    setVisible(true);

    JLabel lblName = new JLabel("Name:");
    JLabel lblBild = new JLabel("Bild:");

    if(existingTeamer) {
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
    
    JLabel lblTelefon = new JLabel("Telefon:");
    
    textField_telefon = new JTextField();
    textField_telefon.setColumns(10);
    
    JLabel lblPosition = new JLabel("Position:");
    
    textField_position = new JTextField();
    textField_position.setColumns(10);

    GroupLayout groupLayout = new GroupLayout(getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
            .addGroup(groupLayout.createSequentialGroup()
              .addContainerGap()
              .addComponent(lblPreview, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE))
            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
              .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_add)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(button_cancel))
              .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                  .addComponent(lblName)
                  .addComponent(lblBild)
                  .addComponent(lblTelefon))
                .addGap(12)
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                  .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addComponent(textField_bild, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(button_loadImage))
                  .addComponent(textField_name, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                  .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(textField_telefon, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblPosition)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_position, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))))))
          .addGap(38))
    );
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(Alignment.TRAILING)
        .addGroup(groupLayout.createSequentialGroup()
          .addGap(29)
          .addComponent(lblPreview, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
          .addGap(28)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblName)
            .addComponent(textField_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(lblTelefon)
            .addComponent(textField_telefon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblPosition)
            .addComponent(textField_position, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
              .addComponent(button_loadImage)
              .addComponent(textField_bild, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addComponent(lblBild))
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
            .addComponent(button_add)
            .addComponent(button_cancel))
          .addGap(367))
    );
    getContentPane().setLayout(groupLayout);
  }

  private void registerListeners() {
    button_add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(textField_name.getText().isEmpty()) { 
          JOptionPane.showMessageDialog(parent,
              "Bitte gib den Namen des Teamers an.",
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
        int rVal = c.showOpenDialog(TeamerDialog.this);
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

  public Teamer getTeamer() {
    Teamer teamer = new Teamer(textField_name.getText(), textField_bild.getText());
    teamer.setPosition(textField_position.getText());
    teamer.setTelefon(textField_telefon.getText());
    return teamer;
  }

  public String getTeamerName() {
    return textField_name.getText();
  }

  public String getTeamerPicture() {
    return textField_bild.getText();
  }
}
