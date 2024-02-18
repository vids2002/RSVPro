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
    public void testContructor() {
        assertEquals("Mimi", testGuest.getName());
        assertEquals(true, testGuest.getPlusOne());
        assertEquals(false, testGuest.getRsvpStatus());
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
    public void testStatusToBoolean() {
        assertTrue(testGuest.setStatusToBoolean("Y".toLowerCase()));
        assertTrue(testGuest.setStatusToBoolean("y"));

        assertFalse(testGuest.setStatusToBoolean("n"));
        assertFalse(testGuest.setStatusToBoolean("N"));

    }
}