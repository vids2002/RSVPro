package model;

import java.util.ArrayList;
import java.util.List;

// Represents the guests that have confirmed and the guests that have declined
public class RsvpStatus {
    private List<Guest> confirmedGuests;
    private List<Guest> declinedGuests;

    // EFFECTS: creates an instance of confirmed and declined guests for this class
    public RsvpStatus() {
        this.confirmedGuests = new ArrayList<>();
        this.declinedGuests = new ArrayList<>();
    }

    //REQUIRES: guest had already been identified as confirmed
    //MODIFIES: this
    //EFFECTS: adds a confirmed guest to confirmedGuests list
    public void addConfirmedGuests(Guest guest) {
        confirmedGuests.add(guest);
    }

    //REQUIRES: guest has already been identified as decline
    //MODIFIES: this
    //EFFECTS: adds a declined guest to declinedGuests list
    public void addDeclinedGuests(Guest guest) {
        declinedGuests.add(guest);
    }

    //EFFECTS: returns the list of confirmed guests
    public List<Guest> getConfirmedGuests() {
        if (confirmedGuests.size() > 0) {
            return confirmedGuests;
        }
        return null;
    }

    //EFFECTS: returns the number of confirmed guests
    public int getNumOfConfirmedGuests() {
        return getConfirmedGuests().size();
    }

    //EFFECTS: returns the list of declined guests
    public List<Guest> getDeclinedGuests() {
        if (declinedGuests.size() > 0) {
            return declinedGuests;
        }
        return null;
    }

    //EFFECTS: returns the number of denied guests
    public int getNumOfDeclinedGuests() {
        return getDeclinedGuests().size();
    }

    //EFFECTS: returns a confirmed guest with given name
    public Guest findConfirmedGuest(String name) {
        for (Guest guest: confirmedGuests) {
            if (guest.getName().equals(name)) {
                return guest;
            }
        }
        return null;
    }

    //EFFECTS: returns a declined guest with given name
    public Guest findDeclinedGuest(String name) {
        for (Guest guest: declinedGuests) {
            if (guest.getName().equals(name)) {
                return guest;
            }
        }
        return null;
    }

    //EFFECTS: removes a guest from the two lists
    public void removeCGuests(Guest guest) {
        confirmedGuests.remove(guest);
    }

    //EFFECTS: removes a guest from the two lists
    public void removeDGuests(Guest guest) {
        declinedGuests.remove(guest);
    }


}
