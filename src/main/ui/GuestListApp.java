package ui;

import model.Guest;
import model.GuestList;
import model.RsvpStatus;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

// Guest List Management Application
public class GuestListApp {
    private Scanner input;
    private GuestList guestList;
    private RsvpStatus confirmedGuestList;
    private RsvpStatus declinedGuestList;
    private Guest guest;

    private boolean keepGoing = true;
    private boolean returnToMain = true;

    // EFFECTS: runs the guest list application
    public GuestListApp() {
        runGuestListApp();
    }

    //MODIFIES: this
    //EFFECTS: processes the user input
    public void runGuestListApp() {
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
    public void processCommand(String command) {
        if (command.equals("e")) {
            editMenuFunctions();
        } else if (command.equals("v")) {
            editViewFunctions();
        }
    }

    //EFFECTS: allows user to access edit menu
    public void editMenuFunctions() {
        String command;

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
    public void editViewFunctions() {
        String command;
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

    //MODIFIES: this, keepGoing
    //EFFECTS: asks the user if they want to perform any more actions
    public void doAnythingElse() {
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


    //MODIFIES: this
    //EFFECTS: adds a guest with given name, given plus one status and false RSVP Status
    private void doAddGuests() {
        boolean plusOne = false;

        //prompt for guests' name
        System.out.println("Please enter guest's name");
        String name = input.nextLine().trim().toLowerCase();

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

        System.out.println(guestList.getListOfGuests());
    }

    //MODIFIES: this
    //EFFECTS: removes a guest with given name
    private void doRemoveGuests() {

        //prompt for guest to remove's name
        System.out.println("Who would you like to remove from the Guest List?");
        String nameToRemove = input.nextLine().trim().toLowerCase();

        //finding the guest with given name
        List<Guest> invitedGuests = guestList.getListOfGuests();
        Guest guestToRemove = guestList.findGuest(invitedGuests, nameToRemove);
        //finding the guest with given name in Confirmed Guest List
        Guest confirmedGToRemove = confirmedGuestList.findConfirmedGuest(nameToRemove);
        //finding the guest with given name in Confirmed Guest List
        Guest declinedGToRemove = declinedGuestList.findDeclinedGuest(nameToRemove);

        //removing the guest with given name
        if (guestToRemove != null) {
            guestList.removeGuest(guestToRemove);
            System.out.println(nameToRemove + " was successfully removed from the Guest List");
            if (confirmedGToRemove != null) {
                confirmedGuestList.removeCGuests(guestToRemove);
            } else if (declinedGToRemove != null) {
                declinedGuestList.removeDGuests(guestToRemove);
            }
        } else {
            System.out.println("That guest is not on the list. Please verify spellings.");
        }
    }

    private void doChangePOStatus() {

        //prompt for guest to change PO status
        System.out.println("Who's Plus One Status would you like to change?");
        String nameToChange = input.nextLine().trim().toLowerCase();
        //prompt for status of Plus One
        System.out.println("What do you want to change the plus one status to?"
                + "\nY to allow a plus one and n to deny a plus one");
        String statusToChange = input.nextLine().trim().toLowerCase();
        Boolean updatedStatus = guest.setStatusToBoolean(statusToChange);

        //finding the guest with given name
        List<Guest> invitedGuests = guestList.getListOfGuests();
        Guest guestToChange = guestList.findGuest(invitedGuests, nameToChange);

        //changing the PO Status of given guest
        if (guestToChange != null) {
            guestToChange.setPlusOne(updatedStatus);
            System.out.println("PlusOneStatus was update successfully");
        } else {
            System.out.println("That guest is not on the list. Please verify spellings.");
        }
        System.out.println(guestList.getListOfGuests());
    }


    private void doChangeRsvpStatus() {
        //prompt for guest to change RSVP status
        System.out.println("Who's RSVP Status would you like to change?");
        String nameToChange = input.nextLine().trim().toLowerCase();
        //prompt for status of RSVP
        System.out.println("What do you want to change the RSVP status to?"
                + "\nY for accepted invitation and N for declined invitation");
        String statusToChange = input.nextLine().trim().toLowerCase();
        Boolean updatedStatus = guest.setStatusToBoolean(statusToChange);

        //finding the guest with given name
        List<Guest> invitedGuests = guestList.getListOfGuests();
        Guest guestToChange = guestList.findGuest(invitedGuests, nameToChange);

        //changing the RSVP Status of given guest
        if (guestToChange != null) {
            guestToChange.setRsvpStatus(updatedStatus);
            if (updatedStatus) {
                confirmedGuestList.addConfirmedGuests(guestToChange);
                System.out.println(confirmedGuestList.getConfirmedGuests());
            } else {
                declinedGuestList.addDeclinedGuests(guestToChange);
                System.out.println(declinedGuestList.getDeclinedGuests());
            }
            System.out.println("RSVP status was updated successfully");
        } else {
            System.out.println("That guest is not on the list. Please verify spellings.");
        }
        System.out.println(guestList.getListOfGuests());
    }

    public void doInvitedGuestList() {
        System.out.println(guestList.getListOfGuests());
        List<Guest> invitedGuests = guestList.getListOfGuests();
        guestList.displayGuestList(invitedGuests, "The Guest List is empty right now");
    }

    public void doConfirmedGuestList() {
        List<Guest> confirmedGuests = confirmedGuestList.getConfirmedGuests();
        guestList.displayGuestList(confirmedGuests, "No one has confirmed an invite yet.");
    }

    public void doDeclinedGuestList() {
        List<Guest> declinedGuests = declinedGuestList.getDeclinedGuests();
        guestList.displayGuestList(declinedGuests, "No one has declined an invite yet.");
    }

    //MODIFIES: this
    //EFFECTS: initializes the input variable
    private void init() {
        guestList = new GuestList();
        confirmedGuestList = new RsvpStatus();
        declinedGuestList = new RsvpStatus();
        guest = new Guest("Mimi", false, false);
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
