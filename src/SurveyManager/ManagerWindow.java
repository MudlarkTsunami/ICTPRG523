package SurveyManager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import static org.apache.commons.io.FileUtils.byteCountToDisplaySize;
import static org.apache.commons.io.FileUtils.sizeOf;



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
 * send survey questions to the sister ClientWindow program via the
 * @author Hayden Baker
 * @version 0.01
 */
public class ManagerWindow extends JFrame implements ActionListener, KeyListener, MouseListener
{
    //Global strings
    String version = "v 0.30";
    String linkedTemp = "";
    String globalOutput = "";

    //Global integers
    static final int COUNT = 10;


    //Main window UI elements
    int commonButtonHeight = 30, commonButtonWidth = 100, selectedSurvey = 0, Timer = 5, timerMemory;
    JButton btnSortNumber, btnSortTopic, btnSortQuestion, btnExit, btnSend, btnDisplayBinaryTree,
            btnPreOrderDisplay, btnPreOrderSave, btnInOrderDisplay, btnInOrderSave, btnPostOrderDisplay,
            btnPostOrderSave, btnIncreaseTimer, btnDecreaseTimer;
    JLabel lblSortBy, lblLinkedList, lblBinaryTree, lblPreOrder, lblInOrder, lblPostOrder, lblTimer, lblTimerNumber,
    lblDetailQuestion, lblDetailTopic, lblDetailA, lblDetailB, lblDetailC, lblDetailD, lblDetailE;
    JTextArea txtLinkedList, txtLBinaryTree;
    JTextField txtDetailTopic, txtDetailQuestion, txtDetailA, txtDetailB, txtDetailC, txtDetailD, txtDetailE;
    JTable questionsTable;
    SurveyModel surveyModel;
    SpringLayout springLayout;

    //File management
    File loadFile = new File("SampleData.txt");
    File inOrderOutput = new File("InOrderOutput.txt");
    File preOrderOutput = new File("PreOrderOutput.txt");
    File postOrderOutput = new File("PostOrderOutput.txt");
    SurveyQuestionData[] SurveyDataGlobal;
    Object[][] dataValues;


    //Linked list and binary tree
    DList mainLinkedList;
    BinaryTree theTree;

    //Communication elements
    private Socket socket = null;
    private DataOutputStream streamOut = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    float responseCount = 0, responseTotal = 0;

    //Timer Elements
    Long startTime, elapsedTime, elapsedSeconds, secondsDisplay;
    boolean timerLocked = false;


    /**
     * Entry point for application
     * @param args Required
     */
    public static void main(String[] args)
    {
        ManagerWindow managerWindow = new ManagerWindow();
        managerWindow.run();
    }


    /**
     * Opens a new ManagerWindow with the default file
     */
    public void run ()
    {
        //Window setup
        setBounds(50,50, 800, 1000);
        setTitle("Flawless Feedback    " + version);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
        getParameters();


        //Reading from the default file
        readFromFile(loadFile);
        //Instantiating the binary tree
        theTree = new BinaryTree();


        //Display elements
        displayMainGUI();
        DisplayTimerView();

        //Connecting to message server
        connect(serverName, serverPort);
    }


    /**
     * Loads the elements for the survey timer
     */
    private void DisplayTimerView()
    {
        //Setting up the different elements of the timer for display and use
        lblTimer = LibraryComponents.LocateAJLabel(this,springLayout,"Time Allowed:", 500, 370);
        lblTimerNumber = LibraryComponents.LocateAJLabel(this,springLayout,String.valueOf(Timer), 600, 350);
        lblTimerNumber.setFont(lblTimer.getFont().deriveFont(40f));
        btnIncreaseTimer = LibraryComponents.LocateAJButton(this, this, springLayout, "Increase", 670, 350,commonButtonWidth,commonButtonHeight);
        btnDecreaseTimer = LibraryComponents.LocateAJButton(this, this, springLayout, "Decrease", 670, 350 + commonButtonHeight ,commonButtonWidth,commonButtonHeight);
    }

    /**
     * Top level command for displaying main window elements
     */
    private void displayMainGUI()
    {
        //Setting up the different elements for the GUI
        springLayout = new SpringLayout();
        setLayout(springLayout);
        loadTableRowButtons(springLayout, 450);
        loadLinkedListSection(springLayout, 500);
        loadBinaryTreeSection(springLayout, 650);
        loadOrderDisplaySection(springLayout, 800);
        loadTable(springLayout);
        loadDetailView(springLayout, 30);
        UpdateTableDetailView();
    }

    /**
     * To be opened as a new window that will graphically display a binary tree
     * @param sentTree Binary tree to be displayed
     */
    public void displayBinaryTree (BinaryTree sentTree)
    {
        //Setting up a new window
        setBounds(50, 50, 950, 750);
        setTitle("Now Viewing the binary tree");
        addWindowListener(new WindowAdapter()
        {
        });
        SpringLayout subSpring = new SpringLayout();
        setLayout(subSpring);
        setResizable(false);
        setVisible(true);
        //Text area for displaying binary tree
        JTextArea txtBinaryTreeDisplay;
        txtBinaryTreeDisplay = LibraryComponents.LocateAJTextArea(this, subSpring, 10,10,45,85);
        //Generating and displaying the binary tree output
        print2D(sentTree.root);
        txtBinaryTreeDisplay.setText(globalOutput);
    }

    /**
     * Loads labels and text fields for the question details area
     * @param layout SpringLayout
     * @param loadHeight Desired top of load height at runtime
     */
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

    /**
     * Method for showing the table the application on the UI
     * @param myPanelLayout SpringLayout
     */
    public void loadTable(SpringLayout myPanelLayout)
    {
        // Create a panel to hold all components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String[] columnNames =
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
        JScrollPane scrollPane = JTable.createScrollPaneForTable(questionsTable);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(350, 400));
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 10, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 10, SpringLayout.NORTH, this);
        questionsTable.addMouseListener(this);
        questionsTable.setSelectionBackground(Color.BLUE);
        questionsTable.setSelectionForeground(Color.WHITE);

    }

    /**
     * Method for loading the buttons below the table element
     * @param layout SpringLayout
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
     * @param layout SpringLayout
     * @param loadHeight Desired top of load height at runtime
     */
    private void loadLinkedListSection(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblLinkedList = LibraryComponents.LocateAJLabel(this, layout, "Linked List", currentXOffset, loadHeight-15);
        txtLinkedList = LibraryComponents.LocateAJTextArea(this,layout,currentXOffset,loadHeight,5,69);
        txtLinkedList.setLineWrap(true);
        txtLinkedList.setWrapStyleWord(true);
    }


    /**
     * Loading the binary tree display area in the UI
     * @param layout SpringLayout
     * @param loadHeight Desired top of load height at runtime
     */
    private void loadBinaryTreeSection(SpringLayout layout, int loadHeight)
    {
        int currentXOffset = 10;
        lblBinaryTree = LibraryComponents.LocateAJLabel(this, layout, "Binary Tree", currentXOffset, loadHeight - 15);
        txtLBinaryTree = LibraryComponents.LocateAJTextArea(this,layout,currentXOffset,loadHeight,5,69);
        txtLBinaryTree.setLineWrap(true);
        txtLBinaryTree.setWrapStyleWord(true);
        currentXOffset +=650;
        btnDisplayBinaryTree = LibraryComponents.LocateAJButton(this,this,layout, "Display", currentXOffset, loadHeight - commonButtonHeight, commonButtonWidth, commonButtonHeight);
    }

    /**
     * Loading the order display area in the UI
     * @param layout SpringLayout
     * @param loadHeight Desired top of load height at runtime
     */
    private void loadOrderDisplaySection (SpringLayout layout, int loadHeight)
    {
        int currentXOffset= 10;
        //Pre-order section
        lblPreOrder = LibraryComponents.LocateAJLabel(this, layout, "Pre-Order",currentXOffset+10, loadHeight - commonButtonHeight);
        btnPreOrderDisplay = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset +=commonButtonWidth;
        btnPreOrderSave = LibraryComponents.LocateAJButton(this,this,layout,"Save", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += 190;
        //In order section
        lblInOrder = LibraryComponents.LocateAJLabel(this, layout, "In-Order",currentXOffset+10, loadHeight - commonButtonHeight);
        btnInOrderDisplay = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset +=commonButtonWidth;
        btnInOrderSave = LibraryComponents.LocateAJButton(this,this,layout,"Save", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset += 190;
        //Post order section
        lblPostOrder = LibraryComponents.LocateAJLabel(this, layout, "Post-Order",currentXOffset+10, loadHeight - commonButtonHeight);
        btnPostOrderDisplay = LibraryComponents.LocateAJButton(this,this,layout,"Display", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
        currentXOffset +=commonButtonWidth;
        btnPostOrderSave = LibraryComponents.LocateAJButton(this,this,layout,"Save", currentXOffset,loadHeight,commonButtonWidth,commonButtonHeight);
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
            List<String> readData = new ArrayList<>();
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
                //'i' must be adjusted here to account for set of junk lines at top of file
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
        if (e.getSource() == btnSend && !timerLocked)
        {
            //The timer is locked for countdown when the user sends out
            timerLocked = true;
            send();
        }

        if (e.getSource() == btnSortNumber)
        {
            CustomSort.BubbleSort(SurveyDataGlobal);
            SortingLoop();
        }

        if (e.getSource() == btnSortQuestion)
        {
            CustomSort.SelectionSort(SurveyDataGlobal);
            SortingLoop();
        }

        if (e.getSource() == btnSortTopic)
        {
            CustomSort.InsertionSort(SurveyDataGlobal);
            SortingLoop();
        }

        if (e.getSource() == btnDisplayBinaryTree)
        {
            //Opens a new window to display the binary tree graphically
            ManagerWindow subWindow = new ManagerWindow();
            subWindow.displayBinaryTree(theTree);
        }

        if (e.getSource() == btnInOrderDisplay)
        {
            theTree.output = "In Order: ";
            theTree.inOrderTraverseTree(theTree.root);
            txtLBinaryTree.setText(theTree.output);
        }
        if (e.getSource() == btnPreOrderDisplay)
        {
            theTree.output = "Pre Order: ";
            theTree.preorderTraverseTree(theTree.root);
            txtLBinaryTree.setText(theTree.output);
        }
        if (e.getSource() == btnPostOrderDisplay)
        {
            theTree.output = "Post Order: ";
            theTree.postOrderTraverseTree(theTree.root);
            txtLBinaryTree.setText(theTree.output);
        }

        if (e.getSource() == btnInOrderSave)
        {
            theTree.output = "In Order: ";
            theTree.inOrderTraverseTree(theTree.root);
            SaveFile(inOrderOutput);
        }

        if (e.getSource() == btnPreOrderSave)
        {
            theTree.output = "Pre Order: ";
            theTree.preorderTraverseTree(theTree.root);
            SaveFile(preOrderOutput);
        }

        if (e.getSource() == btnPostOrderSave)
        {
            theTree.output = "Post Order: ";
            theTree.postOrderTraverseTree(theTree.root);
            SaveFile(postOrderOutput);
        }

        if (e.getSource() == btnIncreaseTimer && !timerLocked )
        {
            //Increasing timer if it is not running
            Timer++;
            lblTimerNumber.setText(String.valueOf(Timer));
        }

        if (e.getSource() == btnDecreaseTimer && !timerLocked && Timer > 1)
        {
            //Decreasing timer if it is not running
            Timer--;
            lblTimerNumber.setText(String.valueOf(Timer));
        }
    }

    /**
     * Method for writing file to disk
     * @param anyOrderOutput Which file to write to
     */
    private void SaveFile(File anyOrderOutput)
    {
        try
        {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(anyOrderOutput));
            outFile.write(theTree.outputHash.toString());
            outFile.newLine();
            txtLBinaryTree.setText(theTree.output + " File size: " +  byteCountToDisplaySize(sizeOf(anyOrderOutput)));
            outFile.close();
        } catch (IOException ex)
        {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Loop used to sort SurveyData and update any table changes
     */
    private void SortingLoop()
    {
        for (int i = 0; i<SurveyDataGlobal.length ; i++)
        {
            dataValues[i][0] = Integer.toString(SurveyDataGlobal[i].getQuestionInt());
            dataValues[i][1] = SurveyDataGlobal[i].getTopic();
            dataValues[i][2] = SurveyDataGlobal[i].getQuestionBody();
        }
        surveyModel.fireTableDataChanged();
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
            UpdateTableDetailView();
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

    public void close()
    {
    }


    /**
     * Updates the survey being viewed in the detail pane
     */
    public void UpdateTableDetailView()
    {
        selectedSurvey = questionsTable.getSelectedRow();
        //returning no selected row as negative one causes errors
        if (selectedSurvey < 1)
        {
            selectedSurvey = 1;
        }

        txtDetailQuestion.setText(SurveyDataGlobal[selectedSurvey].getQuestionBody());
        txtDetailTopic.setText(SurveyDataGlobal[selectedSurvey].getTopic());
        txtDetailA.setText(SurveyDataGlobal[selectedSurvey].getAnswerA());
        txtDetailB.setText(SurveyDataGlobal[selectedSurvey].getAnswerB());
        txtDetailC.setText(SurveyDataGlobal[selectedSurvey].getAnswerC());
        txtDetailD.setText(SurveyDataGlobal[selectedSurvey].getAnswerD());
        txtDetailE.setText(SurveyDataGlobal[selectedSurvey].getAnswerE());
    }

    /**
     * Setting server name parameters
     */
    public void getParameters()
    {
        serverName = "localhost";
        serverPort = 4444;
    }

    /**
     * Outputting message to message server
     */
    private void send()
    {
        try
        {
            //0 at beginning of string marks the message as having come from the manager program
            streamOut.writeUTF("0;" + PackageData(SurveyDataGlobal[selectedSurvey]) + Timer);
            streamOut.flush();
            txtDetailTopic.setText("");
            linkedTemp = SurveyDataGlobal[selectedSurvey].getTopic() + ", Qn" + SurveyDataGlobal[selectedSurvey].getQuestionInt();
            theTree.addBtNode(SurveyDataGlobal[selectedSurvey].getQuestionInt(), SurveyDataGlobal[selectedSurvey].getTopic());
            new Thread(() -> DisplayTimer(Timer)).start();
        }
        catch (IOException ioe)
        {
            txtDetailTopic.setText("Sending error: " + ioe.getMessage());
            close();
        }
    }

    /**
     * Input method for messages from server
     * @param sent Received message
     */
    public void handle(String sent)
    {
        System.out.println("Handle: " + sent);
        if (sent.equals(".bye"))
        {
            txtDetailQuestion.setText("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            //Messages marked with 1 are from the client program
            if (sent.startsWith("1"))
            {
                String[] temp = sent.split(";");
                //take answer number
                responseTotal += Integer.parseInt(temp[1]);
                //increment answer count
                responseCount++;
            }
        }
    }


    /**
     * Connecting to message server
     * @param serverName Name of server
     * @param serverPort Port of server
     */
    public void connect(String serverName, int serverPort)
    {
        txtDetailA.setText("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            open();
        }
        catch (UnknownHostException uhe)
        {
            txtDetailA.setText("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            txtDetailA.setText("Unexpected exception: " + ioe.getMessage());
        }
    }

    /**
     * Opening a new connection to message server
     */
    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            new ChatClientThread1(this, socket);
        }
        catch (IOException ioe)
        {
            txtDetailB.setText("Error opening output stream: " + ioe);
        }
    }

    /**
     * Packages data into single string format to be sent with message server
     * @param sentData Data to be packaged
     * @return Returns a string that represents a packaged SurveyQuestionData
     */
    public String PackageData (SurveyQuestionData sentData)
    {
        String toSend = "";
        toSend += sentData.getQuestionInt() + ";";
        toSend += sentData.getTopic() + ";";
        toSend += sentData.getQuestionBody() + ";";
        toSend += sentData.getAnswerA() + ";";
        toSend += sentData.getAnswerB() + ";";
        toSend += sentData.getAnswerC() + ";";
        toSend += sentData.getAnswerD() + ";";
        toSend += sentData.getAnswerE() + ";";

        return toSend;
    }

    /**
     * Sets timer to run based on the time sent in seconds
     * @param sentTime Timer value in seconds
     */
    private void DisplayTimer(int sentTime)
    {
        //Setting timer memory for returning the value after completing the timer
        timerMemory = Timer;
        //The timer is blue when above 10 seconds and running
        lblTimerNumber.setForeground(Color.BLUE);
        startTime = System.currentTimeMillis();
        timerLocked = true;
        while(true)
        {

            elapsedTime = System.currentTimeMillis() - startTime;
            elapsedSeconds = elapsedTime / 1000;
            secondsDisplay = elapsedSeconds % 60;
            lblTimerNumber.setText("" + (sentTime - secondsDisplay));
            //The timer becomes red when it is 10 or below
            if (secondsDisplay > (sentTime - 11))
            {
                lblTimerNumber.setForeground(Color.RED);
            }
            //ending the loop when the timer runs to 0
            if (secondsDisplay > sentTime)
            {
                lblTimerNumber.setForeground(Color.BLACK);
                timerLocked = false;
                //Resetting the timer to it's original state
                Timer = timerMemory;
                lblTimerNumber.setText(Integer.toString(Timer));
                //Triggers building a new linked list entry
                BuildListEntry();
                break;
            }
        }
    }

    /**
     * Builds a new linked list entry and appends it to a list
     */
    private void BuildListEntry ()
    {
        //If there is no list, create one
        if (mainLinkedList == null)
        {
            mainLinkedList = new DList();
        }

        //if there has been no responses
        if (responseCount == 0)
        {
            mainLinkedList.head.append(new Node(linkedTemp+ " No responses <----->  "));
        }

        //divide total by response and display
        else
        {
            responseCount = responseTotal/responseCount;
            mainLinkedList.head.append(new Node(linkedTemp + ",  " + responseCount + " <----->  "));
        }

        //Resetting counts and totals and displaying linked list values
        responseCount = 0;
        responseTotal = 0;
        txtLinkedList.setText(mainLinkedList.toString());

    }


    /**
     * Prints a binary tree using a reverse inorder traversal
     * @param root Root of binary tree
     * @param space Spacing to be used
     */
    public void print2DUtil(BtNode root, int space)
    {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.rightChild, space);

        // Print current node after space
        // count
        globalOutput = globalOutput+  "\n";
        for (int i = COUNT; i < space; i++)
            globalOutput = globalOutput + " ";
        globalOutput = globalOutput + root.key + "\n";

        // Process left child
        print2DUtil(root.leftChild, space);
    }


    /**
     * Wrapper method for print2DUtil
     * @param root root of a binary tree
     */
    public void print2D(BtNode root)
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }

}
