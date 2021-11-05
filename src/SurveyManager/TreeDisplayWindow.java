package SurveyManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;

public class TreeDisplayWindow extends JFrame
{
    JTextArea txtBinaryTreeDisplay;

    public void run(BinaryTree sentTree, ManagerWindow managerWindow)
    {
        setBounds(50, 50, 1000, 800);
        setTitle("Now Viewing the binary tree");
        addWindowListener(new WindowAdapter()
        {
        });
        SpringLayout subSpring = new SpringLayout();
        setLayout(subSpring);
        setResizable(false);
        setVisible(true);
        //txtBinaryTreeDisplay = LibraryComponents.LocateAJTextArea()
    }
}
