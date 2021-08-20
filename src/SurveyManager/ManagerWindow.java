package SurveyManager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    SurveyQuestionData[] SurveyDataGlobal;

    JTable table;
    MyModel wordModel;

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
        WordAssociationTable(springLayout);
    }

    public void WordAssociationTable(SpringLayout myPanelLayout)
    {
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] =
                { "Word 1", "Word 2", "Sent"};

        // Create some data
        ArrayList<Object[]> dataValues = new ArrayList();
        dataValues.add(new Object[] {"Yes","No",true});
        dataValues.add(new Object[] {"Hi","there",true});
        dataValues.add(new Object[] {"True","False",true});
        dataValues.add(new Object[] {"Cat","Dog",false});

        // constructor of JTable model
        wordModel = new MyModel(dataValues, columnNames);

        // Create a new table instance
        table = new JTable(wordModel);

        // Configure some of JTable's paramters
        table.isForegroundSet();
        table.setShowHorizontalLines(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        add(table);

        // Change the text and background colours
        table.setSelectionForeground(Color.white);
        table.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = table.createScrollPaneForTable(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(172, 115));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 280, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 40, SpringLayout.NORTH, this);
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

            //takes a count of lines in the sample data
            int totalLines = 0;
            String readingLine;
            List<String> readData = new ArrayList<String>();
            while ((readingLine = br.readLine() )!= null)
            {
                totalLines++;
                readData.add(readingLine);
            }
            //correcting for files that aren't multiples of 8
            totalLines = totalLines - (totalLines%8);
            //How many sets of survey questions to create
            int runNumber = (totalLines / 8) - 1;
            //reading data into data class
            SurveyDataGlobal = new SurveyQuestionData[runNumber];
            for (int i  = 1; i <= runNumber; i++)
            {
                int currentLine = 8 * i;
                SurveyQuestionData temp = new SurveyQuestionData();
                temp.setQuestionInt(Integer.parseInt(readData.get(currentLine++)));
                temp.setTopic(readData.get(currentLine++));
                temp.setQuestionBody(readData.get(currentLine++));
                temp.setAnswerA(readData.get(currentLine++));
                temp.setAnswerB(readData.get(currentLine++));
                temp.setAnswerC(readData.get(currentLine++));
                temp.setAnswerD(readData.get(currentLine++));
                temp.setAnswerE(readData.get(currentLine++));
                //i must be adjusted here to account for set of junk lines at top of file
                SurveyDataGlobal[i -1] = temp;
            }
            SurveyQuestionData[] debug = CustomSort.BubbleSort(SurveyDataGlobal);
            int debug1 = 1;

        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnSend)
        {
            DList dList = new DList();
            for (SurveyQuestionData var: SurveyDataGlobal)
            {
                dList.head.append(new Node(var.getTopic().toString() + "  -->  "));
            }
            String Display = dList.toString();
            txtLinkedList.append(Display);
        }

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
