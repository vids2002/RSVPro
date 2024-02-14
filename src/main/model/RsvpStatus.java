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
        return confirmedGuests;
    }

    //EFFECTS: returns the number of confirmed guests
    public int getNumOfConfirmedGuests() {
        return getConfirmedGuests().size();
    }

    //EFFECTS: returns the list of declined guests
    public List<Guest> getDeclinedGuests() {
        return declinedGuests;
    }

    //EFFECTS: returns the number of denied guests
    public int getNumOfDeclinedGuests() {
        return getDeclinedGuests().size();
    }


}
