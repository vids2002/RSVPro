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

    public String getName() {
        return name;
    }

    public boolean getPlusOne() {
        return plusOne;
    }

    public boolean getRsvpStatus() {
        return rsvpStatus;
    }

    public void setRsvpStatus(boolean updatedRsvp) {
        this.rsvpStatus = updatedRsvp;
    }

    public void setPlusOne(boolean updatedPlusOne) {
        this.plusOne = updatedPlusOne;
    }

    @Override
    public String toString() {
        return "Guest: " + name + "   " + "Plus One Status = "
                + plusOne + "   " + "RSVP Status = " + rsvpStatus;
    }


}