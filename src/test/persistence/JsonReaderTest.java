package persistence;

import model.Guest;
import model.GuestList;
import model.RsvpStatus;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.readApplicationState(new GuestList(), new RsvpStatus(), new RsvpStatus());
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGuestList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGuestList.json");
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuests = new RsvpStatus();
            RsvpStatus declinedGuests = new RsvpStatus();
            reader.readApplicationState(guestList, confirmedGuests, declinedGuests);
            assertNull(guestList.getListOfGuests());
            assertNull(confirmedGuests.getConfirmedGuests());
            assertNull(declinedGuests.getDeclinedGuests());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralGuestListWithoutConfirmedOrDeclined() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGuestList.json");
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuests = new RsvpStatus();
            RsvpStatus declinedGuests = new RsvpStatus();
            reader.readApplicationState(guestList, confirmedGuests, declinedGuests);
            assertEquals(2, guestList.getListOfGuests().size());
            checkGuest("alice", true, false, guestList.getListOfGuests().get(0));
            // Add checks for confirmed and declined lists as needed
            assertNull(confirmedGuests.getConfirmedGuests());
            assertNull(declinedGuests.getDeclinedGuests());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGuestListWithConfirmed() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGuestListWithConfirmed.json");
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuests = new RsvpStatus();
            RsvpStatus declinedGuests = new RsvpStatus();
            reader.readApplicationState(guestList, confirmedGuests, declinedGuests);
            assertEquals(2, guestList.getListOfGuests().size());
            checkGuest("alice", true, false, guestList.getListOfGuests().get(0));
            checkGuest("vids", false, true, guestList.getListOfGuests().get(1));
            checkGuest("vids", false, true, confirmedGuests.getConfirmedGuests().get(0));
            assertNull(declinedGuests.getDeclinedGuests());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGuestListWithDeclined() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGuestListWithDeclined.json");
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuests = new RsvpStatus();
            RsvpStatus declinedGuests = new RsvpStatus();
            reader.readApplicationState(guestList, confirmedGuests, declinedGuests);
            assertEquals(2, guestList.getListOfGuests().size());
            checkGuest("alice", true, false, guestList.getListOfGuests().get(0));
            checkGuest("vids", false, false, guestList.getListOfGuests().get(1));
            assertNull(confirmedGuests.getConfirmedGuests());
            checkGuest("vids", false, false, declinedGuests.getDeclinedGuests().get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGuestListWithConfirmedAndDeclined() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGuestListWithConfirmedAndDeclined.json");
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuests = new RsvpStatus();
            RsvpStatus declinedGuests = new RsvpStatus();
            reader.readApplicationState(guestList, confirmedGuests, declinedGuests);
            assertEquals(2, guestList.getListOfGuests().size());
            checkGuest("alice", true, false, guestList.getListOfGuests().get(0));
            checkGuest("vids", false, true, guestList.getListOfGuests().get(1));
            checkGuest("vids", false, true, confirmedGuests.getConfirmedGuests().get(0));
            checkGuest("alice", true, false, declinedGuests.getDeclinedGuests().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}