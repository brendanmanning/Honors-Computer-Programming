import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 * Write a description of class UserView here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UserView
{
   private JFrame frame;
   private JPanel panel;
   private JLabel titleLabel;
   private JTextField binaryInput;
   private JLabel binaryLabel;
   private JButton convertButton;
   private JLabel convertedLabel;
   private JLabel bitLabel;
   private JLabel decimalLabel;
   private JLabel hexaLabel;
   private JComboBox typeSelector;
   private DefaultComboBoxModel comboModel;
   private JLabel typeSelectorLabel;
   // Convienence
   private int width = 400;
   private int height = 205;
   private int centerX = width / 2;
   private int centerY = height / 2;
   private int maxHeight = height * 2;
   // Customization
   private int convertButtonWidth = 330;
   public UserView()
   {
       setupGUI();
       setupConversionSection();
   }
   private void setupGUI()
   {
       frame = new JFrame("Number converter");
       frame.setSize(width,height);
       frame.setLayout(null);
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       
       panel = new JPanel();
       
       titleLabel = new JLabel("Convert numbers", JLabel.RIGHT);
      
       binaryInput = new JTextField(1);
       binaryLabel = new JLabel("Enter a number:", JLabel.LEFT);
       
       comboModel = new DefaultComboBoxModel();
       comboModel.addElement(NumberType.BIN);
       comboModel.addElement(NumberType.DEC);
       comboModel.addElement(NumberType.HEX);
       comboModel.addElement(NumberType.OCT);
       
       typeSelectorLabel = new JLabel("What type of number is above?:", JLabel.LEFT);
       
       typeSelector = new JComboBox(comboModel);
       typeSelector.setSelectedIndex(0);
       
       
       convertButton = new JButton("Convert");
       convertButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               startConversion();
           }
       });
       // frame.add(titleLabel);
       
       binaryLabel.setBounds(10,30,centerX,30);
       binaryInput.setBounds(centerX,30,centerX - 10,30);
       typeSelector.setBounds(centerX,65,centerX - 10,30);
       typeSelectorLabel.setBounds(10,65,centerX - 10, 30);
       
       convertButton.setBounds((width - convertButtonWidth) / 2,95,convertButtonWidth,40);
       //convertButton.setSize(centerX,20);
       
       frame.add(binaryLabel);
       frame.add(binaryInput);
       frame.add(typeSelector);
       frame.add(typeSelectorLabel);
       frame.add(convertButton);

       //frame.add(panel);
       
       frame.setVisible(true);
       frame.setResizable(false);
   }
   private void setupConversionSection()
   {
       convertedLabel = new JLabel("",JLabel.CENTER);
       bitLabel = new JLabel("", JLabel.LEFT);
       decimalLabel = new JLabel("", JLabel.LEFT);
       hexaLabel = new JLabel("", JLabel.LEFT);
       
       int padding = 5;
       int labelHeight = 30;
       convertedLabel.setBounds(0,height - 20,width,labelHeight);
       bitLabel.setBounds(0,height + labelHeight + padding,width,labelHeight);
       decimalLabel.setBounds(0, height + (2 * (labelHeight + padding)), width,labelHeight);
       hexaLabel.setBounds(0, height +  (3 * (labelHeight + padding)), width,labelHeight);
       
       frame.add(convertedLabel);
       frame.add(bitLabel);
       frame.add(decimalLabel);
       frame.add(hexaLabel);
   }
   private void showConversion(String bits, String decimal, String hexa)
   {
       convertedLabel.setText("Conversion Finished!!");
       bitLabel.setText(bits);
       decimalLabel.setText(decimal);
       hexaLabel.setText(hexa);
       
       frame.setSize(width, maxHeight);
   }
   private void hideConversion()
   {
       frame.setSize(width,height);
   }
   // For Command Line Args
   public void setInputText(String t)
   {
       binaryInput.setText(t);
   }
   public boolean setInputType(int type)
   {
       if(type <= 3 && type >= 0) {
           typeSelector.setSelectedIndex(type);
           return true;
       }
       return false;
   }
   public void startConversion() {
        // Convert
        Converter c = new Converter();
        c.doConversion(binaryInput.getText(), "" + typeSelector.getItemAt(typeSelector.getSelectedIndex()));
        if(!c.hasError)
        {
            showConversion("" + c.bits, "" + c.decimalValue, c.hexadecimalValue);
        } else {
             hideConversion();
             JOptionPane.showMessageDialog(frame,"<html><h2>An Error Occured</h2><i>" + c.errorMessage + "</i></html>", "Conversion Error", JOptionPane.ERROR_MESSAGE);
        }
   }
}
