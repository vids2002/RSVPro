package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents the list of guests that are invited to the event
public class GuestList implements Writable {
    private List<Guest> listOfGuests;
    private boolean loggingEnabled = true;

    //EFFECTS: creates an instance for this class with no guests added
    public GuestList() {
        listOfGuests = new ArrayList<>();
    }

    //REQUIRES: !listOfGuests does not contain the guest to be added
    //MODIFIES: this
    //EFFECTS: adds a guest to list of guests
    public void addGuest(Guest guest) {
        listOfGuests.add(guest);
        if (loggingEnabled) {
            EventLog.getInstance().logEvent(new Event(guest.getName() + " was added to the Guest List."));
        }

    }

    //REQUIRES: !listOfGuests empty
    //MODIFIES: this
    //EFFECTS: removes a guest from list of guests
    public void removeGuest(Guest guest) {
        listOfGuests.remove(guest);
        if (loggingEnabled) {
            EventLog.getInstance().logEvent(new Event(guest.getName() + " was removed from the Guest List."));
        }
    }

    //EFFECT: returns number of guests invited to party
    public int getNumOfInvites() {
        return listOfGuests.size();
    }

    //EFFECTS: returns the list of guests invited to the party
    public List<Guest> getListOfGuests() {
        if (listOfGuests.size() > 0) {
            if (loggingEnabled) {
                EventLog.getInstance().logEvent(new Event("The Invited Guest List was viewed."));
            }
            return listOfGuests;
        }
        if (loggingEnabled) {
            EventLog.getInstance().logEvent(new Event("The Invited Guest List was viewed."));
        }
        return null;
    }

    //REQUIRES: !listOfGuests empty
    //MODIFIES: test
    //EFFECTS: clears the entire list of guests
    public void clearGuests() {
        listOfGuests.clear();
        if (loggingEnabled) {
            EventLog.getInstance().logEvent(new Event("The Guest List was cleared."));
        }
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
        if (guests != null) {
            for (Guest guest : guests) {
                if (guest.getName().equalsIgnoreCase(name)) {
                    return guest;
                }
            }
            return null;
        }
        return null;
    }

    //EFFECTS: returns Guest List as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guests", guestsToJson());
        return json;
    }

    // EFFECTS: returns guests in this Guest List as a JSON array
    private JSONArray guestsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Guest guest : listOfGuests) {
            jsonArray.put(guest.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: sets the logging status of invited guest list (mostly for loading and saving purposes)
    public void enableLogging(boolean enable) {
        this.loggingEnabled = enable;
    }
}
