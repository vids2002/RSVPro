package persistence;

import model.Guest;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkGuest(String name, boolean plusOne, boolean rsvpStatus, Guest guest) {
        assertEquals(name, guest.getName());
        assertEquals(plusOne, guest.getPlusOne());
        assertEquals(rsvpStatus, guest.getRsvpStatus());
    }
}