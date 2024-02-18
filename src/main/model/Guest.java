package model;

// Represents a guest with a name, plusOne status and rsvpStatus that will be invited to the event
public class Guest {
    private String name;
    private boolean plusOne;
    private boolean rsvpStatus;


    // EFFECTS: creates a Guest with a name, whether their invitation allows a plus one and their RSVP status
    //          plusOne is true in the beginning
    //          RSVP status is false in the beginning
    public Guest(String name, boolean plusOne, boolean rsvpStatus) {
        this.name = name;
        this.plusOne = plusOne;
        this.rsvpStatus = rsvpStatus;
    }

    //EFFECTS: returns to name of Guest
    public String getName() {
        return name;
    }

    //EFFECTS: returns the plus one status of Guest
    public boolean getPlusOne() {
        return plusOne;
    }

    //EFFECTS: returns to the RSVP status of Guest
    public boolean getRsvpStatus() {
        return rsvpStatus;
    }

    //EFFECTS: sets the RSVP status of Guest
    public void setRsvpStatus(boolean updatedRsvp) {
        this.rsvpStatus = updatedRsvp;
    }

    //EFFECTS: sets the pLus one status of Guest
    public void setPlusOne(boolean updatedPlusOne) {
        this.plusOne = updatedPlusOne;
    }

    //EFFECTS: displays the guest in given format
    @Override
    public String toString() {
        return "Guest: " + name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase()
                + "   " + "Plus One Status = "
                + plusOne + "   " + "RSVP Status = " + rsvpStatus;
    }

    //EFFECTS: returns a given string as a boolean value
    public boolean setStatusToBoolean(String status) {

        if (status.equals("y")) {
            return true;
        }
        return false;
    }


}