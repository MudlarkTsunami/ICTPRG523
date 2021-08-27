package SurveyManager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;



/*

Mark's Comments (21/8/2021):

 - SurveyDataGlobal - is an array of SurveyQuestionData objects
 - readFromFile method - reads the data from the data file and adds it to SurveyDataGlobal array.
 - The Bubble Sort methods work with SurveyQuestionData objects arrays
 - dataValues is a 2D Array and loads 3 columns of data from the SurveyDataGlobal array
 - dataValues has been declared locally in the loadTable method.
 - the model and JTable (surveyModel and questionsTable) are based on dataValues, not SurveyDataGlobal
 - SurveyDataGlobal is being sorted, dataValues is NOT being sorted, nor updated.
 - Therefore the table (questionsTable) is not displaying the newly sorted data

 - You may be aware already, but I also noted that your current Bubble Sort methods
       only sort the Question Number and not the other 7 fields.


 - Interim updates made:
   - dataValues has been made global.
   - In the 'run' method the bubble sort line (added for testing purposes) has been commented out
   - In the ActionPerformed method, code has been added to the section:  if (e.getSource() == btnSortQuestion)

*/

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
    JButton btnSortNumber, btnSortTopic, btnSortQuestion, btnExit, btnSend, btnDisplayBinaryTree,
            btnPreOrderDisplay, btnPreOrderSave, btnInOrderDisplay, btnInOrderSave, btnPostOrderDisplay,
            btnPostOrderSave;
    JLabel lblSortBy, lblLinkedList, lblBinaryTree, lblPreOrder, lblInOrder, lblPostOrder,
    lblDetailQuestion, lblDetailTopic, lblDetailA, lblDetailB, lblDetailC, lblDetailD, lblDetailE;
    JTextArea txtLinkedList, txtLBinaryTree;
    JTextField txtDetailTopic, txtDetailQuestion, txtDetailA, txtDetailB, txtDetailC, txtDetailD, txtDetailE;
    JTable tblQuestions;
    File loadFile = new File("SampleData.txt");
    SurveyQuestionData[] SurveyDataGlobal;

    JTable questionsTable;
    MyModel wordModel;
    SurveyModel surveyModel;
    SpringLayout springLayout;
    Object[][] dataValues;

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
        readFromFile(loadFile);
        //SurveyDataGlobal = CustomSort.BubbleSortAsc(SurveyDataGlobal);
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
        springLayout = new SpringLayout();
        setLayout(springLayout);
        loadTableRowButtons(springLayout, 450);
        loadLinkedListSection(springLayout, 500);
        loadBinaryTreeSection(springLayout, 650);
        loadOrderDisplaySection(springLayout, 800);
        loadTable(springLayout);
        loadDetailView(springLayout, 30);
    }

    private void loadDetailView(SpringLayout layout, int loadHeight)
    {
        int loadWidthLabel = 380, loadWidthText = 440;
        lblDetailTopic = LibraryComponents.LocateAJLabel(this,layout,"Topic:", loadWidthLabel, loadHeight);
        txtDetailTopic = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);
        loadHeight += 25;
        lblDetailQuestion = LibraryComponents.LocateAJLabel(this,layout,"Question:", loadWidthLabel, loadHeight);
        txtDetailQuestion = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);
        loadHeight += 25;
        lblDetailA = LibraryComponents.LocateAJLabel(this,layout,"A:", loadWidthLabel, loadHeight);
        txtDetailA = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);
        loadHeight += 25;
        lblDetailB = LibraryComponents.LocateAJLabel(this,layout,"B:", loadWidthLabel, loadHeight);
        txtDetailB = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);
        loadHeight += 25;
        lblDetailC = LibraryComponents.LocateAJLabel(this,layout,"C:", loadWidthLabel, loadHeight);
        txtDetailC = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);
        loadHeight += 25;
        lblDetailD = LibraryComponents.LocateAJLabel(this,layout,"D:", loadWidthLabel, loadHeight);
        txtDetailD = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);
        loadHeight += 25;
        lblDetailE = LibraryComponents.LocateAJLabel(this,layout,"E:", loadWidthLabel, loadHeight);
        txtDetailE = LibraryComponents.LocateAJTextField(this, this, layout, 30, loadWidthText, loadHeight);

    }


    //Temporary Method for updating dataValues for testing purposes
    public void updateModel ()
    {
        SurveyModel temp;
        String columnNames[] =
                { "Number", "Topic", "Question"};

        dataValues = new Object[SurveyDataGlobal.length][columnNames.length];
        for (int i = 0; i<SurveyDataGlobal.length ; i++)
        {
            dataValues[i][0] = "TEST";
            dataValues[i][1] = "TEST";
            dataValues[i][2] = "TEST";
        }
        temp = new SurveyModel(columnNames,dataValues);
        surveyModel = temp;
    }


    /**
     * Method for showing the table the application on the UI
     * @param myPanelLayout
     */
    public void loadTable(SpringLayout myPanelLayout)
    {
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] =
                { "Number", "Topic", "Question"};

        // Create some data
        dataValues = new Object[SurveyDataGlobal.length][columnNames.length];
//        dataValues = new Object[SurveyDataGlobal.length][columnNames.length];
        for (int i = 0; i<SurveyDataGlobal.length ; i++)
        {
            dataValues[i][0] = Integer.toString(SurveyDataGlobal[i].getQuestionInt());
            dataValues[i][1] = SurveyDataGlobal[i].getTopic();
            dataValues[i][2] = SurveyDataGlobal[i].getQuestionBody();
        }


        // constructor of JTable model
        surveyModel = new SurveyModel(columnNames, dataValues);

        // Create a new table instance
        questionsTable = new JTable(surveyModel);

        // Configure some of JTable's parameters
        questionsTable.isForegroundSet();
        questionsTable.setShowHorizontalLines(false);
        questionsTable.setRowSelectionAllowed(true);
        questionsTable.setColumnSelectionAllowed(true);
        add(questionsTable);

        // Change the text and background colours
        questionsTable.setSelectionForeground(Color.white);
        questionsTable.setSelectionBackground(Color.red);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = questionsTable.createScrollPaneForTable(questionsTable);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(350, 400));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 10, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 10, SpringLayout.NORTH, this);
        questionsTable.addMouseListener(this);
    }

    /**
     * Method for loading the buttons below the table element
     * @param layout
     * @param loadHeight Desired top of load height at runtime
     */
    private void loadTableRowButtons(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblSortBy = LibraryComponents.LocateAJLabel(this,layout,"Sort By", currentXOffset, loadHeight-commonButtonHeight);
        btnSortNumber = LibraryComponents.LocateAJButton(this, this, layout, "Question", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth/2;
        btnSortTopic = LibraryComponents.LocateAJButton(this, this, layout, "Topic", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth/2;
        btnSortQuestion = LibraryComponents.LocateAJButton(this, this, layout, "Answer", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth;
        btnExit = LibraryComponents.LocateAJButton(this, this, layout, "Exit", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += commonButtonWidth + commonButtonWidth/2;
        btnSend = LibraryComponents.LocateAJButton(this, this, layout, "Send", currentXOffset, loadHeight,commonButtonWidth,commonButtonHeight);
    }

    /**
     * Loading the linked list display area in the UI
     * @param layout Desired top of load height at runtime
     * @param loadHeight
     */
    private void loadLinkedListSection(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblLinkedList = LibraryComponents.LocateAJLabel(this, layout, "Linked List", currentXOffset, loadHeight-15);
        txtLinkedList = LibraryComponents.LocateAJTextArea(this,layout,currentXOffset,loadHeight,5,69);

    }


    /**
     * Loading the binary tree display area in the UI
     * @param layout
     * @param loadHeight Desired top of load height at runtime
     */
    private void loadBinaryTreeSection(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblBinaryTree = LibraryComponents.LocateAJLabel(this, layout, "Binary Tree", currentXOffset, loadHeight - 15);
        txtLBinaryTree = LibraryComponents.LocateAJTextArea(this,layout,currentXOffset,loadHeight,5,69);
        currentXOffset +=650;
        btnDisplayBinaryTree = LibraryComponents.LocateAJButton(this,this,layout, "Display", currentXOffset, loadHeight - commonButtonHeight, commonButtonWidth, commonButtonHeight);
    }

    /**
     * Loading the order display area in the UI
     * @param layout
     * @param loadHeight Desired top of load height at runtime
     */
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


    /**
     * Takes the data from the sentFile and puts it into SurveyDataGlobal
     * @param sentFile File to be read
     */
    private void readFromFile(File sentFile)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(sentFile));

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
                temp.setAnswerE(readData.get(currentLine));
                //i must be adjusted here to account for set of junk lines at top of file
                SurveyDataGlobal[i -1] = temp;
            }
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

        if (e.getSource() == btnSortNumber)
        {
            CustomSort.BubbleSort(SurveyDataGlobal);
            for (int i = 0; i<SurveyDataGlobal.length ; i++)
            {
                dataValues[i][0] = Integer.toString(SurveyDataGlobal[i].getQuestionInt());
                dataValues[i][1] = SurveyDataGlobal[i].getTopic();
                dataValues[i][2] = SurveyDataGlobal[i].getQuestionBody();
            }
            surveyModel.fireTableDataChanged();
        }
        if (e.getSource() == btnSortQuestion)
        {
            CustomSort.SelectionSort(SurveyDataGlobal);
            for (int i = 0; i<SurveyDataGlobal.length ; i++)
            {
                dataValues[i][0] = Integer.toString(SurveyDataGlobal[i].getQuestionInt());
                dataValues[i][1] = SurveyDataGlobal[i].getTopic();
                dataValues[i][2] = SurveyDataGlobal[i].getQuestionBody();
            }
            surveyModel.fireTableDataChanged();

        }

        if (e.getSource() == btnSortTopic)
        {
            CustomSort.InsertionSort(SurveyDataGlobal);
            for (int i = 0; i<SurveyDataGlobal.length ; i++)
            {
                dataValues[i][0] = Integer.toString(SurveyDataGlobal[i].getQuestionInt());
                dataValues[i][1] = SurveyDataGlobal[i].getTopic();
                dataValues[i][2] = SurveyDataGlobal[i].getQuestionBody();
            }
            surveyModel.fireTableDataChanged();

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
        if (e.getSource() == questionsTable)
        {
            int selected = questionsTable.getSelectedRow();
            txtDetailQuestion.setText(SurveyDataGlobal[selected].getQuestionBody());
            txtDetailTopic.setText(SurveyDataGlobal[selected].getTopic());
            txtDetailA.setText(SurveyDataGlobal[selected].getAnswerA());
            txtDetailB.setText(SurveyDataGlobal[selected].getAnswerB());
            txtDetailC.setText(SurveyDataGlobal[selected].getAnswerC());
            txtDetailD.setText(SurveyDataGlobal[selected].getAnswerD());
            txtDetailE.setText(SurveyDataGlobal[selected].getAnswerE());
        }

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
