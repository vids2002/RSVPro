package ui;

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

public class GUI extends JFrame implements ActionListener {
    GuestList guestList;
    RsvpStatus confirmedGuests;
    RsvpStatus declinedGuests;
    JFrame mainFrame;

    JLabel label;
    JTextField field;

    //"ADD TO EDIT PANEL" DECLARATIONS
    JLabel functionLabel;
    JLabel nameLabel;
    JTextField nameField;
    JLabel questionLabel;
    JCheckBox answerLabel;

    JPanel mainMenu;
    JPanel editMenu;
    JPanel viewMenu;

    JPanel addGuest;

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
    JButton addGuestButton;

    private static final String JSON_STORE = "./data/guestlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: constructs a JFrame with Welcome Panel
    public GUI() {
        super("RSVPro");
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(300, 350));

        guestList = new GuestList();

        initializeWelcomeButtons();

        initializePanels("Main");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //EFFECTS: initializes required panel depending on chosen button action and resets Main Frame
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
        addGuest = new JPanel();
        addGuest.setLayout(new BoxLayout(addGuest, BoxLayout.Y_AXIS));

        addToPanelContent("Add a new guest", "Guest's Name:", "Plus One?");

        addGuestButton = new JButton("Add Guest");
        done = new JButton("Done");
        addGuestButton.addActionListener(e -> {
            addGuestToList(nameField.getText(), answerLabel.isSelected());
            nameField.setText("");
            answerLabel.setSelected(false);
        });
        addGuestButton.setActionCommand("Add Guest");
        done.setActionCommand("Edit Guest List");
        done.addActionListener(this);
        addToEditPanel(addGuest, functionLabel, nameLabel, nameField, questionLabel, answerLabel);
        addGuest.add(addGuestButton);
        addGuest.add(done);

        formatPanel(addGuest, 30, 30, 30, 30);

        mainFrame.add(addGuest, BorderLayout.CENTER);
    }

    //EFFECTS: initializes labels, text field and checkbox that needs to be added to edit panels
    private void addToPanelContent(String function, String name, String question) {
        functionLabel = new JLabel(function);
        nameLabel = new JLabel(name);
        nameField = new JTextField(1);
        questionLabel = new JLabel(question);
        answerLabel = new JCheckBox();
    }

    //EFFECTS: adds labels, text field and checkbox that needs to be added to given edit panel
    private void addToEditPanel(JPanel panel, JLabel functionLabel, JLabel nameLabel, JTextField nameField,
                                JLabel questionLabel, JCheckBox answerLabel) {
        panel.add(functionLabel);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(questionLabel);
        panel.add(answerLabel);
    }

    //EFFECTS: formats the panel
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

    //EFFECTS: gives the button a specific action is required to perform
    private void addActionToWelcomeButtons() {
        editGL.setActionCommand("Edit Guest List");
        editGL.addActionListener(this);
        viewGL.setActionCommand("View Guest List");
        viewGL.addActionListener(this);
        saveGL.setActionCommand("Save Guest List");
        saveGL.addActionListener(this);
        loadGL.setActionCommand("Load Guest List");
        loadGL.addActionListener(this);
        quitApp.setActionCommand("Quit Guest List");
        quitApp.addActionListener(this);
    }

    private void initializeEditButtons() {
        addG = new JButton("Add Guest");
        removeG = new JButton("Remove Guest");
        changePlusOne = new JButton("Change Plus-One Status");
        changeRsvp = new JButton("Change RSVP Status");
        clearGL = new JButton("Clear Guest List");
        returnToMain = new JButton("Return To Main Menu");
    }

    private void addEditButtons() {
        addSixButtons(editMenu, addG, removeG, changePlusOne, changeRsvp, clearGL, returnToMain);
    }

    private void addActionToEditButtons() {
        addG.setActionCommand("Add Guest");
        addG.addActionListener(this);
        returnToMain.setActionCommand("Return To Main Menu");
        returnToMain.addActionListener(this);
    }

    private void initializeViewButtons() {
        viewIG = new JButton("View Invited Guests");
        viewCG = new JButton("View Confirmed Guests");
        viewDG = new JButton("View Declined Guests");
        viewGInfo = new JButton("View Specific Guest's Information");
        returnToMain = new JButton("Return To Main Menu");
    }

    private void addViewButtons() {
        addFiveButtons(viewMenu, viewIG, viewCG, viewDG, viewGInfo, returnToMain);
    }

    private void addActionToViewButtons() {
        viewIG.setActionCommand("View Invited Guests");
        viewIG.addActionListener(this);
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


    //EFFECTS: indicates what to do when a button is pressed
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
        if (command.equals("Load Guest List")) {
            initializePanels("Load");
        }
        if (command.equals("Save Guest List")) {
            initializePanels("Save");
        }
        if (command.equals("Return To Main Menu")) {
            initializePanels("Main");
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


}
