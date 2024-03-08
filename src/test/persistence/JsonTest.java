package persistence;

import model.Guest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGuest(String name, boolean plusOne, boolean rsvpStatus, Guest guest) {
        assertEquals(name, guest.getName());
        assertEquals(plusOne, guest.getPlusOne());
        assertEquals(rsvpStatus, guest.getRsvpStatus());
    }
}