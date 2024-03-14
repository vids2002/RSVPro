package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents the guests that have confirmed and the guests that have declined
public class RsvpStatus implements Writable {
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

    //EFFECTS: removes a Confirmed Guest from the list of Confirmed Guests
    public void removeCGuests(Guest guestToRemove) {
        Iterator<Guest> iterator = confirmedGuests.iterator();
        while (iterator.hasNext()) {
            Guest guest = iterator.next();
            if (guest.getName().equalsIgnoreCase(guestToRemove.getName())) {
                iterator.remove();
                break;
            }
        }
    }

    //EFFECTS: removes a Declined Guest from the list of Declined Guests
    public void removeDGuests(Guest guestToRemove) {
        Iterator<Guest> iterator = declinedGuests.iterator();
        while (iterator.hasNext()) {
            Guest guest = iterator.next();
            if (guest.getName().equalsIgnoreCase(guestToRemove.getName())) {
                iterator.remove();
                break;
            }
        }
    }

    //EFFECTS: returns RSVP Status List as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("confirmedGuests", guestsToJson(confirmedGuests));
        json.put("declinedGuests", guestsToJson(declinedGuests));
        return json;
    }

    // EFFECTS: returns guests in this Guest List as a JSON array
    private JSONArray guestsToJson(List<Guest> guests) {
        JSONArray jsonArray = new JSONArray();
        for (Guest guest : guests) {
            jsonArray.put(guest.toJson());
        }
        return jsonArray;
    }
}
