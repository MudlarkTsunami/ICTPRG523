package SurveyManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class LibraryComponents
{
    /** --------------------------------------------------------
     * Purpose: Locate a single JLabel within a JFrame.
     * param   JFrame, Layout_manager, JLabel_Caption, Width, X_position, Y_Position
     * returns The JLabel.
     * ----------------------------------------------------------
     */
    public static JLabel LocateAJLabel(JFrame myJFrame, SpringLayout myJLabelLayout, String JLabelCaption, int x, int y)
    {
        // Declare and Instantiate the JLabel
        JLabel myJLabel = new JLabel(JLabelCaption);
        // Add the JLabel to the screen
        myJFrame.add(myJLabel);
        // Set the position of the JLabel (From left hand side of the JFrame (West), and from top of JFrame (North))
        myJLabelLayout.putConstraint(SpringLayout.WEST, myJLabel, x, SpringLayout.WEST, myJFrame);
        myJLabelLayout.putConstraint(SpringLayout.NORTH, myJLabel, y, SpringLayout.NORTH, myJFrame);
        // Return the label to the calling method
        return myJLabel;
    }

    /** --------------------------------------------------------
     * Purpose: Locate a single JLabel within a JFrame.
     * param   JFrame, Layout_manager, JLabel_Caption, Width, X_position, Y_Position
     * returns The JLabel.
     * ----------------------------------------------------------
     */
    public static JTable LocateAJTable (JFrame myJFrame, SpringLayout myJLabelLayout, int x, int y)
    {
        // Declare and Instantiate the JLabel
        JTable myJTable = new JTable();
        // Add the JLabel to the screen
        myJFrame.add(myJTable);
        // Set the position of the JLabel (From left hand side of the JFrame (West), and from top of JFrame (North))
        myJLabelLayout.putConstraint(SpringLayout.WEST, myJTable, x, SpringLayout.WEST, myJFrame);
        myJLabelLayout.putConstraint(SpringLayout.NORTH, myJTable, y, SpringLayout.NORTH, myJFrame);
        // Return the table to the calling method
        return myJTable;
    }



    /** --------------------------------------------------------
     * Purpose: Locate a single JTextField within a JFrame.
     * param   JFrame, KeyListener, Layout_manager, Width, X_position, Y_Position
     * returns The JTextField.
     * ----------------------------------------------------------
     */
    public static JTextField LocateAJTextField(JFrame myJFrame, KeyListener myKeyLstnr, SpringLayout myJTextFieldLayout, int width, int x, int y)
    {
        JTextField myJTextField = new JTextField(width);
        myJFrame.add(myJTextField);
        myJTextField.addKeyListener(myKeyLstnr);
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJTextField, x, SpringLayout.WEST, myJFrame);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJTextField, y, SpringLayout.NORTH, myJFrame);
        return myJTextField;
    }


    /** --------------------------------------------------------
     * Purpose: Locate a single JButton within a JFrame.
     * param   JFrame, ActionListener, Layout_manager, JButton_name, JButton_caption, X_position, Y_Position, Width, Height
     * returns The JButton.
     * ----------------------------------------------------------
     */
    public static JButton LocateAJButton(JFrame myJFrame, ActionListener myActnLstnr, SpringLayout myJButtonLayout, String  JButtonCaption, int x, int y, int w, int h)
    {
        JButton myJButton = new JButton(JButtonCaption);
        myJFrame.add(myJButton);
        myJButton.addActionListener(myActnLstnr);
        myJButtonLayout.putConstraint(SpringLayout.WEST, myJButton, x, SpringLayout.WEST, myJFrame);
        myJButtonLayout.putConstraint(SpringLayout.NORTH, myJButton, y, SpringLayout.NORTH, myJFrame);
        myJButton.setPreferredSize(new Dimension(w,h));
        return myJButton;
    }

    public static JButton LocateAJButton(JFrame myJFrame, ActionListener myActnLstnr, SpringLayout myJButtonLayout, String  JButtonCaption, int x, int y, int w, int h, Color chosenColor)
    {
        JButton myJButton = new JButton(JButtonCaption);
        myJFrame.add(myJButton);
        myJButton.setBackground(chosenColor);
        myJButton.addActionListener(myActnLstnr);
        myJButtonLayout.putConstraint(SpringLayout.WEST, myJButton, x, SpringLayout.WEST, myJFrame);
        myJButtonLayout.putConstraint(SpringLayout.NORTH, myJButton, y, SpringLayout.NORTH, myJFrame);
        myJButton.setPreferredSize(new Dimension(w,h));
        return myJButton;
    }


    /** --------------------------------------------------------
     * Purpose: Locate a single JTextArea within a JFrame.
     * param   JFrame, Layout_manager, X_position, Y_Position, Width, Height
     * returns The JTextArea.
     * ----------------------------------------------------------
     */
    public static JTextArea LocateAJTextArea(JFrame myJFrame, SpringLayout myLayout, int x, int y, int w, int h)
    {
        JTextArea myJTextArea = new JTextArea(w,h);
        myJFrame.add(myJTextArea);
        myLayout.putConstraint(SpringLayout.WEST, myJTextArea, x, SpringLayout.WEST, myJFrame);
        myLayout.putConstraint(SpringLayout.NORTH, myJTextArea, y, SpringLayout.NORTH, myJFrame);
        return myJTextArea;
    }


}
