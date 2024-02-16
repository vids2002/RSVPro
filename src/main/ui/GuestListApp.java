package ui;

import model.Guest;
import model.GuestList;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

// Guest List Management Application
public class GuestListApp {
    private Scanner input;
    private GuestList guestList;

    private boolean keepGoing = true;
    private boolean returnToMain = true;

    // EFFECTS: runs the guest list application
    public GuestListApp() {
        runGuestListApp();
    }

    //MODIFIES: this
    //EFFECTS: processes the user input
    private void runGuestListApp() {
        String command;

        init();

        System.out.println("\nWelcome to RSVPro!");
        while (returnToMain) {
            displayMainMenu();
            command = input.nextLine().trim().toLowerCase();

            if (command.equals("q")) {
                System.out.println("\nGoodbye!");
                returnToMain = false;
            } else if (command.equals("e") || (command.equals("v"))) {
                processCommand(command);
            } else {
                System.out.println("That's an invalid input");
            }

        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("e")) {
            editMenuFunctions();
        } else if (command.equals("v")) {
            editViewFunctions();
        }
    }

    //EFFECTS: allows user to access edit menu
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void editMenuFunctions() {
        String command;

        init();
        keepGoing = true;

        while (keepGoing) {
            displayEditMenu();
            command = input.nextLine().trim().toLowerCase();

            if (command.equals("a")) {
                doAddGuests();
            } else if (command.equals("r")) {
                doRemoveGuests();
            } else if (command.equals("p")) {
                doChangePOStatus();
            } else if (command.equals("c")) {
                doChangeRsvpStatus();
            } else if (command.equals("m")) {
                keepGoing = false;
                returnToMain = true;
                continue;
            } else {
                System.out.println("That's not a valid input");
                continue;
            }
            doAnythingElse();
        }
    }


    //EFFECTS: allows user to access view menu
    private void editViewFunctions() {
        String command;

        init();
        keepGoing = true;

        while (keepGoing) {
            displayViewMenu();
            command = input.nextLine().trim().toLowerCase();

            if (command.equals("i")) {
                doInvitedGuestList();
            } else if (command.equals("c")) {
                doConfirmedGuestList();
            } else if (command.equals("d")) {
                doDeclinedGuestList();
            } else if (command.equals("m")) {
                keepGoing = false;
                returnToMain = true;
                continue;
            } else {
                System.out.println("That's not a valid input");
                continue;
            }
            doAnythingElse();
        }
    }

    private void doDeclinedGuestList() {
    }

    private void doConfirmedGuestList() {
    }

    private void doInvitedGuestList() {
    }

    //MODIFIES: this, keepGoing
    //EFFECTS: asks the user if they want to perform any more actions
    private void doAnythingElse() {
        boolean inValidInput = true;

        while (inValidInput) {
            System.out.println("\nDo you want to do anything else?"
                    + "\nY for yes or N for no");
            String command;
            command = input.nextLine().trim().toLowerCase();

            if (command.equals("y")) {
                keepGoing = true;
                inValidInput = false;
            } else if (command.equals("n")) {
                System.out.println("Goodbye!");
                keepGoing = false;
                returnToMain = false;
                inValidInput = false;
            } else {
                System.out.println("That's an Invalid Input");
            }
        }
    }

    //EFFECTS: adds a guest with given name, given plus one status and false RSVP Status
    private void doAddGuests() {
        boolean plusOne = false;

        //prompt for guests' name
        System.out.println("Please enter guest's name");
        String name = input.nextLine().trim();

        while (keepGoing) {
            //prompt for guests' initial plus one status when sending invite
            System.out.println("What would you like this guest to have a plus one?"
                    + "\ny for yes and n for no");
            String plusOneStatus = input.nextLine().trim().toLowerCase();
            if (plusOneStatus.equals("y")) {
                plusOne = true;
                keepGoing = false;
            } else if (plusOneStatus.equals("n")) {
                keepGoing = false;
            } else {
                System.out.println("that's an invalid input");
            }
        }

        //create new guest with false RSVP status
        Guest newGuest = new Guest(name, plusOne, false);

        //add new gust to guest list
        guestList.addGuest(newGuest);

        //print out the guest that was added
        System.out.println("The following guest was added to the Guest List:");
        System.out.println(newGuest);
    }

    private void doChangeRsvpStatus() {
    }

    private void doChangePOStatus() {
    }

    private void doRemoveGuests() {
    }



    //MODIFIES: this
    //EFFECTS: initializes the input variable
    private void init() {
        guestList = new GuestList();
        input = new Scanner(System.in);
    }

    //EFFECTS: displays the main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tE -> Edit GuestList");
        System.out.println("\tV -> View GuestList");
        System.out.println("\tQ -> Quit Application");
    }

    //EFFECTS: displays the sub menu (Edit GuestList) to user
    private void displayEditMenu() {
        System.out.println("\nChose an option:");
        System.out.println("\tA -> Add Guest");
        System.out.println("\tR -> Remove Guest");
        System.out.println("\tP -> Change Plus One Status of Guest");
        System.out.println("\tC -> Change RSVP Status of Guest");
        System.out.println("\tM -> Return to Main Menu");
    }

    //EFFECTS: displays the sub menu (View GuestList) to user
    private void displayViewMenu() {
        System.out.println("\nChose an option:");
        System.out.println("\tI -> View List of Guests Invited");
        System.out.println("\tC -> View List of Confirmed Guests");
        System.out.println("\tD -> View List of Declined Guests");
        System.out.println("\tM -> Return to Main Menu");
    }


}
