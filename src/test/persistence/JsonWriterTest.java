package persistence;

import model.Guest;
import model.GuestList;
import model.RsvpStatus;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuestList = new RsvpStatus();
            RsvpStatus declinedGuestList = new RsvpStatus();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            writer.write(guestList, confirmedGuestList, declinedGuestList);
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // Expected, do nothing
        }
    }

    @Test
    void testWriterEmptyGuestList() {
        try {
            GuestList guestList = new GuestList();
            RsvpStatus confirmedGuestList = new RsvpStatus();
            RsvpStatus declinedGuestList = new RsvpStatus();

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGuestList.json");

            writer.open();
            writer.write(guestList, confirmedGuestList, declinedGuestList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGuestList.json");

            reader.readApplicationState(guestList, confirmedGuestList, declinedGuestList);

            assertNull(guestList.getListOfGuests());
            assertNull(confirmedGuestList.getConfirmedGuests());
            assertNull(declinedGuestList.getDeclinedGuests());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGuestList() {
        try {
            GuestList guestList = new GuestList();
            guestList.addGuest(new Guest("Alice", true, false));
            guestList.addGuest(new Guest("Bob", false, true));

            RsvpStatus confirmedGuestList = new RsvpStatus();
            confirmedGuestList.addConfirmedGuests(new Guest("Bob", false, true));

            RsvpStatus declinedGuestList = new RsvpStatus();
            declinedGuestList.addDeclinedGuests(new Guest("Alice", true, false));

            String filePath = "./data/testWriterGeneralGuestList.json";
            JsonWriter writer = new JsonWriter(filePath);
            writer.open();
            writer.write(guestList, confirmedGuestList, declinedGuestList);
            writer.close();

            // Re-instantiate to ensure they are empty before reading
            guestList = new GuestList();
            confirmedGuestList = new RsvpStatus();
            declinedGuestList = new RsvpStatus();
            JsonReader reader = new JsonReader(filePath);

            reader.readApplicationState(guestList, confirmedGuestList, declinedGuestList);
            assertEquals(2, guestList.getListOfGuests().size());
            checkGuest("Alice", true, false, guestList.getListOfGuests().get(0));
            checkGuest("Bob", false, true, guestList.getListOfGuests().get(1));
            assertEquals(1, confirmedGuestList.getConfirmedGuests().size());
            assertEquals(1, declinedGuestList.getDeclinedGuests().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}