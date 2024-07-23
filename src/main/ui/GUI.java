package ui;

import model.Event;
import model.EventLog;
import model.Guest;
import model.GuestList;
import model.RsvpStatus;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//EFFECTS: Runs the GUI Application
public class GUI extends JFrame implements ActionListener {

    //JSON Reader/Writer declarations
    private static final String JSON_STORE = "./data/guestlist.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private RsvpStatus confirmedGuestList;
    private RsvpStatus declinedGuestList;

    //Guest List Declarations
    GuestList guestList;

    //Main Frame Declaration
    JFrame mainFrame;

    //JPanel Declarations
    JPanel mainMenu;
    JPanel editMenu;
    JPanel viewMenu;
    JPanel addGuestPanel;
    JPanel removeGuestPanel;
    JPanel plusOnePanel;
    JPanel rsvpPanel;
    JPanel guestInfoPanel;

    //Inside Panel Declarations
    JLabel functionLabel;
    JLabel nameLabel;
    JTextField nameField;
    JLabel questionLabel;
    JCheckBox answerLabel;

    //JButton Declarations
    JButton editGL;
    JButton viewGL;
    JButton saveGL;
    JButton loadGL;
    JButton quitApp;

    JButton addG;
    JButton removeG;
    JButton changePlusOne;
    JButton changeRsvp;
    JButton clearGL;
    JButton returnToMain;

    JButton viewIG;
    JButton viewCG;
    JButton viewDG;
    JButton viewGInfo;

    JButton done;
    JButton changeMore;

    //EFFECTS: constructs a JFrame with appearance of a Splashscreen followed by Welcome Panel
    //         initializes what's necessary for Json Reader and Writer operations
    public GUI() {
        super("RSVPro");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        mainFrame = new JFrame();
        showSplashScreen();
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(300, 350));

        guestList = new GuestList();
        confirmedGuestList = new RsvpStatus();
        declinedGuestList = new RsvpStatus();

        initializeWelcomeButtons();
        saveGL.addActionListener(e -> saveGuestListAction());
        loadGL.addActionListener(e -> loadGuestListAction());

        initializePanels("Main");
    }

    //EFFECTS: depending on button action, initializes required JPanel and resets mainFrame
    private void initializePanels(String menu) {
        mainFrame.getContentPane().removeAll();
        if (menu.equals("Main")) {
            initializeMainPanel();
        }
        if (menu.equals("Edit") || menu.equals("Add") || menu.equals("Remove")
                || menu.equals("Plus One") || menu.equals("RSVP") || menu.equals("Clear")) {
            initializeEditPanels(menu);
        }
        if (menu.equals("View") || menu.equals("Invited") || menu.equals("Confirmed")
                || menu.equals("Declined") || menu.equals("Information")) {
            initializeViewPanels(menu);
        }
        if (menu.equals("Save")) {
            initializeMainPanel();
        }
        if (menu.equals("Load")) {
            initializeMainPanel();
        }
        if (menu.equals("Quit")) {
            printLog();
            System.exit(0);
        }
        resetMainFrame();
    }

    //EFFECTS: resets mainFrame
    private void resetMainFrame() {
        mainFrame.validate();
        mainFrame.repaint();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    //EFFECTS: initializes the main panel
    private void initializeMainPanel() {
        mainMenu = new JPanel();
        mainMenu.setBackground(Color.pink);
        mainMenu.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel welcomeLabel = new JLabel("Welcome to RSVPro!");
        JLabel welcomeMessage = new JLabel("What would you like to do today?");
        mainMenu.add(welcomeLabel);
        mainMenu.add(welcomeMessage);
        addWelcomeButtons();
        addActionToWelcomeButtons();

        mainFrame.add(mainMenu, BorderLayout.CENTER);
    }

    // EFFECTS: initializes the required edit panel depending on chosen button
    private void initializeEditPanels(String menu) {
        if (menu.equals("Edit")) {
            initializeEditPanel();
        }
        if (menu.equals("Add")) {
            initializeAddGuestPanel();
        }
        if (menu.equals("Remove")) {
            initializeRemoveGuestPanel();
        }
        if (menu.equals("Plus One")) {
            initializePlusOnePanel();
        }
        if (menu.equals("RSVP")) {
            initializeRsvPanel();
        }
    }

    //EFFECTS: initializes the edit panel
    private void initializeEditPanel() {
        editMenu = new JPanel();

        JLabel editLabel = new JLabel("Edit Guest List ");
        editMenu.add(editLabel);

        initializeEditButtons();

        addEditButtons();
        addActionToEditButtons();

        formatPanel(editMenu, 50, 150, 250, 150);

        mainFrame.add(editMenu);
    }

    //EFFECTS: initializes the required view panel depending on chosen button
    private void initializeViewPanels(String menu) {
        if (menu.equals("View")) {
            initializeViewPanel();
        }
        if (menu.equals("Invited")) {
            viewInvitedGuests();
        }
        if (menu.equals("Confirmed")) {
            viewConfirmedGuests();
        }
        if (menu.equals("Declined")) {
            viewDeclinedGuests();
        }
        if (menu.equals("Information")) {
            initializeGuestInfoPanel();
        }
    }

    //EFFECTS: initializes the view panel
    private void initializeViewPanel() {
        viewMenu = new JPanel();

        JLabel viewLabel = new JLabel("View Guest List ");
        viewMenu.add(viewLabel);

        initializeViewButtons();

        addViewButtons();
        addActionToViewButtons();

        formatPanel(viewMenu, 50, 150, 250, 150);

        mainFrame.add(viewMenu);
    }

    //EFFECTS: initializes the add guest panel
    private void initializeAddGuestPanel() {
        addGuestPanel = new JPanel();
        addGuestPanel.setLayout(new BoxLayout(addGuestPanel, BoxLayout.Y_AXIS));

        addToPanelContent("Add a new guest", "Guest's Name:", "Plus One?");

        changeMore = new JButton("Add Guest");
        done = new JButton("Done");
        changeMore.addActionListener(e -> {
            addGuestToList(nameField.getText(), answerLabel.isSelected());
            nameField.setText("");
            answerLabel.setSelected(false);
        });
        changeMore.setActionCommand("Add Guest");
        done.setActionCommand("Edit Guest List");
        done.addActionListener(this);
        addToEditPanel(addGuestPanel, functionLabel, nameLabel, nameField, questionLabel, answerLabel);
        addGuestPanel.add(changeMore);
        addGuestPanel.add(done);

        formatPanel(addGuestPanel, 30, 30, 30, 30);

        mainFrame.add(addGuestPanel, BorderLayout.CENTER);
    }

    //EFFECTS: initializes the Remove Guest Panel
    private  void initializeRemoveGuestPanel() {
        removeGuestPanel = new JPanel();
        removeGuestPanel.setLayout(new BoxLayout(removeGuestPanel, BoxLayout.Y_AXIS));

        removeFromPanelContent("Remove a guest", "Guest's Name");

        changeMore = new JButton("Remove Guest");
        done = new JButton("Done");
        changeMore.addActionListener(e -> {
            removeGuestFromList(nameField.getText());
            nameField.setText("");
        });
        changeMore.setActionCommand("Remove Guest");
        done.setActionCommand("Edit Guest List");
        done.addActionListener(this);
        addToRemovePanel(removeGuestPanel, functionLabel, nameLabel, nameField);
        removeGuestPanel.add(changeMore);
        removeGuestPanel.add(done);

        formatPanel(removeGuestPanel, 30, 30, 30, 30);

        mainFrame.add(removeGuestPanel, BorderLayout.CENTER);
    }

    //EFFECTS: initializes the Plus One Panel
    private void initializePlusOnePanel() {
        plusOnePanel = new JPanel();
        plusOnePanel.setLayout(new BoxLayout(plusOnePanel, BoxLayout.Y_AXIS));

        addToPanelContent("Change Plus-One Status", "Guest's Name:", "Plus One?");

        changeMore = new JButton("Change Plus-One Status");
        done = new JButton("Done");
        changeMore.addActionListener(e -> {
            changePlusOneStatus(nameField.getText(), answerLabel.isSelected());
            nameField.setText("");
            answerLabel.setSelected(false);
        });
        changeMore.setActionCommand("Change Plus-One");
        done.setActionCommand("Edit Guest List");
        done.addActionListener(this);
        addToEditPanel(plusOnePanel, functionLabel, nameLabel, nameField, questionLabel, answerLabel);
        plusOnePanel.add(changeMore);
        plusOnePanel.add(done);

        formatPanel(plusOnePanel, 30, 30, 30, 30);

        mainFrame.add(plusOnePanel, BorderLayout.CENTER);
    }

    //EFFECTS: initializes the RSVP Panel
    private void initializeRsvPanel() {
        rsvpPanel = new JPanel();
        rsvpPanel.setLayout(new BoxLayout(rsvpPanel, BoxLayout.Y_AXIS));

        addToPanelContent("Change RSVP Status", "Guest's Name:", "RSVP?");

        changeMore = new JButton("Change RSVP Status");
        done = new JButton("Done");
        changeMore.addActionListener(e -> {
            changeRsvpStatus(nameField.getText(), answerLabel.isSelected());
            nameField.setText("");
            answerLabel.setSelected(false);
        });
        changeMore.setActionCommand("Change RSVP");
        done.setActionCommand("Edit Guest List");
        done.addActionListener(this);
        addToEditPanel(rsvpPanel, functionLabel, nameLabel, nameField, questionLabel, answerLabel);
        rsvpPanel.add(changeMore);
        rsvpPanel.add(done);

        formatPanel(rsvpPanel, 30, 30, 30, 30);

        mainFrame.add(rsvpPanel, BorderLayout.CENTER);
    }

    //EFFECTS: initializes labels, text field and checkbox that needs to be added to edit panels
    private void addToPanelContent(String function, String name, String question) {
        functionLabel = new JLabel(function);
        nameLabel = new JLabel(name);
        nameField = new JTextField(1);
        questionLabel = new JLabel(question);
        answerLabel = new JCheckBox();
    }

    //EFFECTS: initializes labels and test field that needs to be added to remove panel
    private void removeFromPanelContent(String function, String name) {
        functionLabel = new JLabel(function);
        nameLabel = new JLabel(name);
        nameField = new JTextField(1);
    }

    //EFFECTS: initializes labels and text field that needs to be added to guest info panel
    private void guestInfoPanelContent(String function, String name) {
        functionLabel = new JLabel(function);
        nameLabel = new JLabel(name);
        nameField = new JTextField(1);
    }


    //EFFECTS: adds labels, text field and checkbox to edit panel
    private void addToEditPanel(JPanel panel, JLabel functionLabel, JLabel nameLabel, JTextField nameField,
                                JLabel questionLabel, JCheckBox answerLabel) {
        panel.add(functionLabel);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(questionLabel);
        panel.add(answerLabel);
    }

    //EFFECTS: adds labels adn text field to edit panel
    private void addToRemovePanel(JPanel panel, JLabel functionLabel,
                                  JLabel nameLabel, JTextField nameField) {
        panel.add(functionLabel);
        panel.add(nameLabel);
        panel.add(nameField);
    }

    //EFFECTS: adds labels and text field to guest info panel
    private void addToGuestInfoPanel(JPanel panel, JLabel functionLabel,
                                     JLabel nameLabel, JTextField nameField) {
        panel.add(functionLabel);
        panel.add(nameLabel);
        panel.add(nameField);
    }

    //EFFECTS: formats the given panel
    private void formatPanel(JPanel panel, int borderT, int borderL, int borderB, int borderR) {
        panel.setBackground(Color.pink);
        panel.setBorder(BorderFactory.createEmptyBorder(borderT, borderL, borderB, borderR));
        mainMenu.setVisible(false);
        panel.setVisible(true);
    }

    //EFFECTS: adds a new guest to the Guest List
    private void addGuestToList(String name, boolean plusOne) {
        Guest newGuest = new Guest(name, plusOne, false);
        guestList.addGuest(newGuest);
        JOptionPane.showMessageDialog(mainFrame, "Following guest was added successfully:\n" + newGuest);
    }

    //EFFECTS: removes a given guest from the Guest List
    private void removeGuestFromList(String name) {
        guestList.enableLogging(false);
        confirmedGuestList.enableLogging(false);
        declinedGuestList.enableLogging(false);

        List<Guest> invitedGuests = guestList.getListOfGuests();

        Guest guestToRemove = guestList.findGuest(invitedGuests, name);
        Guest conGuestToRemove = confirmedGuestList.findConfirmedGuest(name);
        Guest decGuestToRemove = declinedGuestList.findDeclinedGuest(name);

        guestList.enableLogging(true);
        confirmedGuestList.enableLogging(true);
        declinedGuestList.enableLogging(true);

        if (guestToRemove != null) {
            guestList.removeGuest(guestToRemove);
            JOptionPane.showMessageDialog(mainFrame, name + " was successfully removed from Guest List.");
            if (conGuestToRemove != null) {
                confirmedGuestList.removeCGuests(guestToRemove);
            } else if (decGuestToRemove != null) {
                declinedGuestList.removeDGuests(guestToRemove);
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "That guest is not on the list."
                    + "Please verify spellings.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //EFFECTS: changes the Plus-One Status of given guest
    private void changePlusOneStatus(String name, Boolean plusOneStatus) {
        guestList.enableLogging(false);
        List<Guest> invitedGuests = guestList.getListOfGuests();
        guestList.enableLogging(true);

        Guest guestToChange = guestList.findGuest(invitedGuests, name);

        if (guestToChange != null) {
            guestToChange.setPlusOne((plusOneStatus));
            JOptionPane.showMessageDialog(mainFrame, "The Plus-One Status of "
                    + name + " was successfully updated");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "That guest is not on the list."
                    + "Please verify spellings.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //EFFECTS: changes the RSVP Status of given guest
    private void changeRsvpStatus(String name, Boolean rsvpStatus) {
        guestList.enableLogging(false);
        List<Guest> invitedGuests = guestList.getListOfGuests();
        guestList.enableLogging(true);

        Guest guestToChange = guestList.findGuest(invitedGuests, name);

        if (guestToChange != null) {
            removeAndChangeRsvp(rsvpStatus, guestToChange);
            JOptionPane.showMessageDialog(mainFrame, "The RSVP Status of "
                    + name + " was successfully updated");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "That guest is not on the list."
                    + "Please verify spellings.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    //MODIFIES: this
    //EFFECTS: removes guest from opposite list before changing RSVP Status
    public void removeAndChangeRsvp(boolean updatedStatus, Guest guestToChange) {
        if (updatedStatus) {
            declinedGuestList.removeDGuests(guestToChange);
        } else {
            confirmedGuestList.removeCGuests(guestToChange);
        }

        guestToChange.setRsvpStatus(updatedStatus);
        if (updatedStatus) {
            confirmedGuestList.addConfirmedGuests(guestToChange);
        } else {
            declinedGuestList.addDeclinedGuests(guestToChange);
        }
    }

    //EFFECTS: view the list of invited guests
    private void viewInvitedGuests() {
        List<Guest> invitedGuests = guestList.getListOfGuests();
        if (guestList == null || invitedGuests == null || invitedGuests.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "The Guest List is empty right now");
            initializeViewPanel();
        } else {
            StringBuilder guestListString = new StringBuilder("Invited Guests:\n");
            for (Guest invitedGuest : invitedGuests) {
                guestListString.append(invitedGuest).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, guestListString.toString());
            initializeViewPanel();
        }
    }

    //EFFECTS: view the list of confirmed guests
    private void viewConfirmedGuests() {
        List<Guest> confirmedGuests = confirmedGuestList.getConfirmedGuests();
        if (confirmedGuests == null || confirmedGuests == null || confirmedGuests.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No one has confirmed yet");
            initializeViewPanel();
        } else {
            StringBuilder guestListString = new StringBuilder("Confirmed Guests: \n");
            for (Guest confirmedGuest : confirmedGuests) {
                guestListString.append(confirmedGuest).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, guestListString.toString());
            initializeViewPanel();
        }
    }

    //EFFECTS: view the list of declined guests
    private void viewDeclinedGuests() {
        List<Guest> declinedGuests = declinedGuestList.getDeclinedGuests();
        if (declinedGuests == null || declinedGuests == null || declinedGuests.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No one has declined yet");
            initializeViewPanel();
        } else {
            StringBuilder guestListString = new StringBuilder("Declined Guests: \n");
            for (Guest declinedGuest : declinedGuests) {
                guestListString.append(declinedGuest).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, guestListString.toString());
            initializeViewPanel();
        }
    }

    //EFFECTS: initializes Specific Guest Panel
    private void initializeGuestInfoPanel() {
        guestInfoPanel = new JPanel();
        guestInfoPanel.setLayout(new BoxLayout(guestInfoPanel, BoxLayout.Y_AXIS));

        guestInfoPanelContent("A Guest's Information", "Guest's Name");

        changeMore = new JButton("View Information");
        done = new JButton("Done");
        changeMore.addActionListener(e -> {
            viewSpecificGuest(nameField.getText());
            nameField.setText("");
        });
        changeMore.setActionCommand("View Information");
        done.setActionCommand("View Guest List");
        done.addActionListener(this);
        addToGuestInfoPanel(guestInfoPanel, functionLabel, nameLabel, nameField);
        guestInfoPanel.add(changeMore);
        guestInfoPanel.add(done);

        formatPanel(guestInfoPanel, 30, 30, 30, 30);

        mainFrame.add(guestInfoPanel, BorderLayout.CENTER);
    }

    //EFFECTS: views information of a specific (given) guest
    private void viewSpecificGuest(String name) {
        guestList.enableLogging(false);
        List<Guest> invitedGuests = guestList.getListOfGuests();

        Guest guestToFind = guestList.findGuest(invitedGuests, name);

        guestList.enableLogging(true);

        if (guestToFind != null) {
            JOptionPane.showMessageDialog(mainFrame, "Guest Information:\n" + guestToFind);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Guest not found. Please verify spellings and try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //EFFECTS: initializes the JButtons for use in the main menu
    private void initializeWelcomeButtons() {
        editGL = new JButton("Edit Guest List");
        viewGL = new JButton("View Guest List");
        saveGL = new JButton("Save Guest List");
        loadGL = new JButton("Load Guest List");
        quitApp = new JButton("Quit Application");
    }

    //EFFECTS: adds buttons to the main menu
    private void addWelcomeButtons() {
        addFiveButtons(mainMenu, editGL, viewGL, saveGL, loadGL, quitApp);
    }

    //EFFECTS: sets an action command it edit, view and quit buttons in main menu
    private void addActionToWelcomeButtons() {
        editGL.setActionCommand("Edit Guest List");
        editGL.addActionListener(this);
        viewGL.setActionCommand("View Guest List");
        viewGL.addActionListener(this);
        quitApp.setActionCommand("Quit Guest List");
        quitApp.addActionListener(this);
    }

    //EFFECTS: initializes the edit buttons
    private void initializeEditButtons() {
        addG = new JButton("Add Guest");
        removeG = new JButton("Remove Guest");
        changePlusOne = new JButton("Change Plus-One Status");
        changeRsvp = new JButton("Change RSVP Status");
        clearGL = new JButton("Clear Guest List");
        returnToMain = new JButton("Return To Main Menu");
    }

    //EFFECTS: adds edit buttons to edit panel
    private void addEditButtons() {
        addSixButtons(editMenu, addG, removeG, changePlusOne, changeRsvp, clearGL, returnToMain);
    }

    //EFFECTS: adds action to the edit buttons
    private void addActionToEditButtons() {
        addG.setActionCommand("Add Guest");
        addG.addActionListener(this);
        removeG.setActionCommand("Remove Guest");
        removeG.addActionListener(this);
        changePlusOne.setActionCommand("Change Plus-One Status");
        changePlusOne.addActionListener(this);
        changeRsvp.setActionCommand("Change RSVP Status");
        changeRsvp.addActionListener(this);
//        clearGL.setActionCommand("Clear Guest List");
//        clearGL.addActionListener(this);
        returnToMain.setActionCommand("Return To Main Menu");
        returnToMain.addActionListener(this);
    }

    //EFFECTS: initializes view buttons
    private void initializeViewButtons() {
        viewIG = new JButton("View Invited Guests");
        viewCG = new JButton("View Confirmed Guests");
        viewDG = new JButton("View Declined Guests");
        viewGInfo = new JButton("View Specific Guest's Information");
        returnToMain = new JButton("Return To Main Menu");
    }

    //EFFECTS: adds view buttons to the view panel
    private void addViewButtons() {
        addFiveButtons(viewMenu, viewIG, viewCG, viewDG, viewGInfo, returnToMain);
    }

    //EFFECTS: adds action to the view buttons
    private void addActionToViewButtons() {
        viewIG.setActionCommand("View Invited Guests");
        viewIG.addActionListener(this);
        viewCG.setActionCommand("View Confirmed Guests");
        viewCG.addActionListener(this);
        viewDG.setActionCommand("View Declined Guests");
        viewDG.addActionListener(this);
        viewGInfo.setActionCommand("View Specific Guest's Information");
        viewGInfo.addActionListener(this);
        returnToMain.setActionCommand("Return To Main Menu");
        returnToMain.addActionListener(this);
    }

    //EFFECTS: adds five buttons to given panel
    private void addFiveButtons(JPanel panel, JButton button1, JButton button2,
                            JButton button3, JButton button4, JButton button5) {
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
    }

    //EFFECTS: adds six buttons to given panel
    private void addSixButtons(JPanel panel, JButton button1, JButton button2, JButton button3,
                               JButton button4, JButton button5, JButton button6) {
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
    }

    //EFFECTS: indicates what action to perform when a button is pressed
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Edit Guest List") || command.equals("Add Guest")
                || command.equals("Remove Guest") || command.equals("Change Plus-One Status")
                || command.equals("Change RSVP Status") || command.equals("Clear Guest List")) {
            doThisEditAction(command);
        }
        if (command.equals("View Guest List") || command.equals("View Invited Guests")
                || command.equals("View Confirmed Guests") || command.equals("View Declined Guests")
                || command.equals("View Specific Guest's Information")) {
            doThisViewAction(command);
        }
        if (command.equals("Return To Main Menu")) {
            initializePanels("Main");
        }
        if (command.equals("Quit Guest List")) {
            initializePanels("Quit");
        }
    }

    //EFFECTS: chooses which edit panel to open depending on which button is pressed
    public void doThisEditAction(String command) {
        if (command.equals("Edit Guest List")) {
            initializePanels("Edit");
        }
        if (command.equals("Add Guest")) {
            initializePanels("Add");
        }
        if (command.equals("Remove Guest")) {
            initializePanels("Remove");
        }
        if (command.equals("Change Plus-One Status")) {
            initializePanels("Plus One");
        }
        if (command.equals("Change RSVP Status")) {
            initializePanels("RSVP");
        }
        if (command.equals("Clear Guest List")) {
            initializePanels("Clear");
        }
    }

    //EFFECTS: choose which view panel to open depending on which button is pressed
    private void doThisViewAction(String command) {
        if (command.equals("View Guest List")) {
            initializePanels("View");
        }
        if (command.equals("View Invited Guests")) {
            initializePanels("Invited");
        }
        if (command.equals("View Confirmed Guests")) {
            initializePanels("Confirmed");
        }
        if (command.equals("View Declined Guests")) {
            initializePanels("Declined");
        }
        if (command.equals("View Specific Guest's Information")) {
            initializePanels("Information");
        }
    }

    //EFFECTS: saves Guest List to file
    private void saveGuestListAction() {
        try {
            jsonWriter.open();
            jsonWriter.write(guestList, confirmedGuestList, declinedGuestList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(mainFrame, "Guest List has successfully been saved.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(mainFrame, "Unable to write to file: "
                    + JSON_STORE, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //EFFECTS: loads Guest List from file
    private void loadGuestListAction() {
        try {
            guestList.enableLogging(false);
            confirmedGuestList.enableLogging(false);
            declinedGuestList.enableLogging(false);
            jsonReader.readApplicationState(guestList, confirmedGuestList, declinedGuestList);
            JOptionPane.showMessageDialog(mainFrame, "Guest List has successfully been loaded.");
            guestList.enableLogging(true);
            confirmedGuestList.enableLogging(true);
            declinedGuestList.enableLogging(true);
            // Here you might want to refresh or update your GUI components to reflect the loaded data
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Unable to read from file: "
                    + JSON_STORE, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //EFFECTS: ensures save and load buttons has only one action listener
    //         then adds action to save and load buttons
    private void setupSaveAndLoadListeners() {
        ActionListener[] saveListeners = saveGL.getActionListeners();
        if (saveListeners.length > 0) {
            for (ActionListener listener : saveListeners) {
                saveGL.removeActionListener(listener);
            }
        }
        saveGL.addActionListener(e -> saveGuestListAction());

        ActionListener[] loadListeners = loadGL.getActionListeners();
        if (loadListeners.length > 0) {
            for (ActionListener listener : loadListeners) {
                loadGL.removeActionListener(listener);
            }
        }
        loadGL.addActionListener(e -> loadGuestListAction());
    }

    //EFFECTS: sets up splash screen
    public void showSplashScreen() {
        JWindow splashScreen = new JWindow();
        ImageIcon splashImage = new ImageIcon("./data/RSVProLogo.png");

        splashScreen.getContentPane().add(new JLabel(splashImage));
        splashScreen.pack();

        splashScreen.setLocation(0, 0);

        splashScreen.setVisible(true);

        try {
            Thread.sleep(5000); // Display the splash screen for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashScreen.setVisible(false);
        splashScreen.dispose();
    }

    //EFFECTS: prints the event lof onto the screen
    public void printLog() {
        EventLog log = EventLog.getInstance();
        for (Event e : log) {
            System.out.println(e);
            System.out.println("\n");
        }
    }


}
