package persistence;

import model.Guest;
import model.GuestList;
import model.RsvpStatus;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
            RsvpStatus confirmed = new RsvpStatus();
            RsvpStatus declined = new RsvpStatus();
            reader.readApplicationState(guestList, confirmed, declined);
            assertTrue(guestList.getListOfGuests().isEmpty());
            assertTrue(confirmed.getConfirmedGuests().isEmpty());
            assertTrue(declined.getDeclinedGuests().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralGuestList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGuestList.json");
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmed = new RsvpStatus();
            RsvpStatus declined = new RsvpStatus();
            reader.readApplicationState(guestList, confirmed, declined);
            assertEquals(2, guestList.getListOfGuests().size());
            checkGuest("Alice", true, false, guestList.getListOfGuests().get(0));
            // Add checks for confirmed and declined lists as needed
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}