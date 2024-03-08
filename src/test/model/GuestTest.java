package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {
    private Guest testGuest;

    @BeforeEach
    public void runBefore() {
        testGuest = new Guest("Mimi", true,false);
    }

    @Test
    public void testConstructor() {
        assertEquals("Mimi", testGuest.getName());
        assertTrue( testGuest.getPlusOne());
        assertFalse(testGuest.getRsvpStatus());
    }

    @Test
    public void testSetRsvpStatusInvalid() {
        testGuest.setRsvpStatus(false);
        assertFalse(testGuest.getRsvpStatus());
    }

    @Test
    public void testSetRsvpStatusValid() {
        testGuest.setRsvpStatus(true);
        assertTrue(testGuest.getRsvpStatus());
    }

    @Test
    public void testSetPlusOneInvalid() {
        testGuest.setPlusOne(false);
        assertFalse(testGuest.getPlusOne());
    }

    @Test
    public void testSetPlusOneValid() {
        testGuest.setPlusOne(true);
        assertTrue(testGuest.getPlusOne());
    }

    @Test
    public void testToString() {
        String expected = "Guest: Mimi   Plus One Status = true   RSVP Status = false";
        assertEquals(expected, testGuest.toString());
    }

    @Test
    public void testStatusToBoolean() {
        assertTrue(testGuest.setStatusToBoolean("Y".toLowerCase()));
        assertTrue(testGuest.setStatusToBoolean("y"));

        assertFalse(testGuest.setStatusToBoolean("n"));
        assertFalse(testGuest.setStatusToBoolean("N"));

    }
}