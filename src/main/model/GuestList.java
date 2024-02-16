package model;

import java.util.ArrayList;
import java.util.List;

// Represents the list of guests that are invited to the event
public class GuestList {
    private List<Guest> listOfGuests;

    //EFFECTS: creates an instance for this class with no guests added
    public GuestList() {
        listOfGuests = new ArrayList<>();
    }

    //REQUIRES: !listOfGuests does not contain the guest to be added
    //MODIFIES: this
    //EFFECTS: adds a guest to list of guests
    public void addGuest(Guest guest) {
        listOfGuests.add(guest);
    }

    //REQUIRES: !listOfGuests empty
    //MODIFIES: this
    //EFFECTS: removes a guest from list of guests
    public void removeGuest(Guest guest) {
        listOfGuests.remove(guest);
    }

    //EFFECT: returns number of guests invited to party
    public int getNumOfInvites() {
        return listOfGuests.size();
    }

    //EFFECTS: returns the list of guests invited to the party
    public List<Guest> getListOfGuests() {
        if (listOfGuests.size() > 0) {
            return listOfGuests;
        }
        return null;
    }

    //REQUIRES: !listOfGuests empty
    //MODIFIES: test
    //EFFECTS: clears the entire list of guests
    public void clearGuests() {
        listOfGuests.clear();
    }

    //REQUIRES: !listOfGuests is not empty
    //EFFECTS: checks if a guest has RSVPed and
    //         returns a list of confirmed guests and list of declined guests
    public RsvpStatus rsvpedGuests() {
        RsvpStatus rsvpStatus = new RsvpStatus();

        for (Guest guest : listOfGuests) {
            if (guest.getRsvpStatus()) {
                rsvpStatus.addConfirmedGuests(guest);
            } else {
                rsvpStatus.addDeclinedGuests(guest);
            }
        }
        return rsvpStatus;
    }


    //EFFECTS: template for finding a guest
    public Guest findGuest(List<Guest> guests, String name) {
        for (Guest guest : guests) {
            if (guest.getName().equals(name)) {
                return guest;
            }
        }
        return null;
    }

    //EFFECTS: displays the guest list with appropriate information
    //         as of now, program can view invited, confirmed and declined guests
    public void displayGuestList(List<Guest> guests, String emptyListMessage) {
        if (guests != null && !guests.isEmpty()) {
            for (Guest guest : guests) {
                System.out.println(guest);
            }
        } else {
            System.out.println(emptyListMessage);
        }
    }
}
