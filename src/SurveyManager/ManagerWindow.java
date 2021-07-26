package SurveyManager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * <H1>ManagerWindow Program</H1>
 * The ManagerWindow program allows the user to open a survey file and
 * send survey questions to the sister ClientWindow program
 * @author Hayden Baker
 * @version 0.01
 */
public class ManagerWindow extends JFrame implements ActionListener, KeyListener, MouseListener
{
    String version = "v 0.01";
    int commonButtonHeight = 30, commonButtonWidth = 100;
    JButton btnSortQuestion, btnSortTopic, btnSortAnswer, btnExit, btnSend, btnDisplayBinaryTree,
            btnPreOrderDisplay, btnPreOrderSave, btnInOrderDisplay, btnInOrderSave, btnPostOrderDisplay,
            btnPostOrderSave;
    JLabel lblSortBy, lblLinkedList, lblBinaryTree, lblPreOrder, lblInOrder, lblPostOrder;
    JTextArea txtLinkedList, txtLBinaryTree;
    JTable tblQuestions;
    File loadFile = new File("src/SampleData.txt");

    /**
     * Entry point for application
     * @param args
     */
    public static void main(String[] args)
    {
        ManagerWindow managerWindow = new ManagerWindow();
        managerWindow.run();
    }


    /**
     * Opens a new ManagerWindow, with no file specified
     */
    public void run ()
    {
        setBounds(50,50, 800, 1000);
        setTitle("Flawless Feedback    " + version);
        readFromFile();
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        displayMainGUI();
        setResizable(false);
        setVisible(true);
    }

    /**
     * Top level command for displaying main window elements
     */
    private void displayMainGUI()
    {
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        loadTable(springLayout,10);
        loadTableRowButtons(springLayout, 450);
        loadLinkedListSection(springLayout, 500);
        loadBinaryTreeSection(springLayout, 650);
        loadOrderDisplaySection(springLayout, 800);
    }



    private void loadTable(SpringLayout layout, int loadHeight)
    {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        add(tablePanel);

    }

    private void loadTableRowButtons(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblSortBy = LibraryComponents.LocateAJLabel(this,layout,"Sort By", currentXOffset, loadHeight-commonButtonHeight);
        btnSortAnswer = LibraryComponents.LocateAJButton(this, this, layout, "Question", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth/2;
        btnSortTopic = LibraryComponents.LocateAJButton(this, this, layout, "Topic", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth/2;
        btnSortAnswer = LibraryComponents.LocateAJButton(this, this, layout, "Answer", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth;
        btnExit = LibraryComponents.LocateAJButton(this, this, layout, "Exit", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth/2;
        btnSend = LibraryComponents.LocateAJButton(this, this, layout, "Send", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
    }

    private void loadLinkedListSection(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblLinkedList = LibraryComponents.LocateAJLabel(this, layout, "Linked List", currentXOffset, loadHeight-15);
        txtLinkedList = LibraryComponents.LocateAJTextArea(this,layout,currentXOffset,loadHeight,5,69);

    }

    private void loadBinaryTreeSection(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblBinaryTree = LibraryComponents.LocateAJLabel(this, layout, "Binary Tree", currentXOffset, loadHeight - 15);
        txtLBinaryTree = LibraryComponents.LocateAJTextArea(this,layout,currentXOffset,loadHeight,5,69);
        currentXOffset +=650;
        btnDisplayBinaryTree = LibraryComponents.LocateAJButton(this,this,layout, "Display", currentXOffset, loadHeight - commonButtonHeight, commonButtonWidth, commonButtonHeight);
    }

    private void loadOrderDisplaySection (SpringLayout layout, int loadHeight)
    {
        int currentXOffset= 10;
        //Pre order section
        lblPreOrder = LibraryComponents.LocateAJLabel(this, layout, "Pre-Order",currentXOffset+10, loadHeight - commonButtonHeight);
        btnPreOrderDisplay = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset +=commonButtonWidth;
        btnPreOrderSave = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += 190;
        //In order section
        lblInOrder = LibraryComponents.LocateAJLabel(this, layout, "In-Order",currentXOffset+10, loadHeight - commonButtonHeight);
        btnInOrderDisplay = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset +=commonButtonWidth;
        btnInOrderSave = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += 190;
        //Post order section
        lblPostOrder = LibraryComponents.LocateAJLabel(this, layout, "Post-Order",currentXOffset+10, loadHeight - commonButtonHeight);
        btnPostOrderDisplay = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset +=commonButtonWidth;
        btnPostOrderSave = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
    }

    private void readFromFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(loadFile));
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
