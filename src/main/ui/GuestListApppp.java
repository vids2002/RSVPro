//package ui;
//
//import java.util.Scanner;
//
//// Guest List Management Application
//public class GuestListApppp {
//    private Scanner input;
//
//    // EFFECTS: runs the guest list application
//    public GuestListApppp() {
//        runGuestListApp();
//    }
//
//    //MODIFIES: this
//    //EFFECTS: processes the user input
//    private void runGuestListApp() {
//        boolean keepGoing = true;
//        String command = null;
//
//        init();
//
//        System.out.println("\nWelcome to RSVPro!");
//
//        while (keepGoing) {
//            displayMainMenu();
//            command = input.nextLine();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//        System.out.println("\nGoodbye!");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: processes user command
//    private void processCommand(String command) {
//        if (command.equals("e")) {
//            displayEditMenu();
//            editMenuFunctions();
//        } else if (command.equals("v")) {
//            displayViewMenu();
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: initializes the input variable
//    private void init() {
//        input = new Scanner(System.in);
//    }
//
//    //EFFECTS: displays the main menu of options to user
//    private void displayMainMenu() {
//        System.out.println("\nWhat do you like to do?");
//        System.out.println("\tE -> Edit GuestList");
//        System.out.println("\tV -> View GuestList");
//        System.out.println("\tQ -> Quit Application");
//    }
//
//    //EFFECTS: displays the sub menu (Edit GuestList) to user
//    private void displayEditMenu() {
//        System.out.println("\nChose an option:");
//        System.out.println("\tA -> Add Guest");
//        System.out.println("\tR -> Remove Guest");
//        System.out.println("\tP -> Change Plus One Status of Guest");
//        System.out.println("\tC -> Change RSVP Status of Guest");
//        System.out.println("\tQ -> Quit Application");
//    }
//
//    //EFFECTS: displays the sub menu (View GuestList) to user
//    private void displayViewMenu() {
//        System.out.println("\nChose an option:");
//        System.out.println("\tI -> View List of Guests Invited");
//        System.out.println("\tC -> View List of Confirmed Guests");
//        System.out.println("\tD -> View List of Declined Guests");
//        System.out.println("\tQ -> Quit Application");
//    }
//
//    //EFFECTS: allows user to access edit menu
//    private void editMenuFunctions() {
//
//    }
//
//
//}
