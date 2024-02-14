package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RsvpStatusTest {
    private RsvpStatus testRsvpStatus;

    private Guest guest1;
    private Guest guest2;
    private Guest guest3;
    private Guest guest4;
    private Guest guest5;

    @BeforeEach
    public void runBefore() {
        testRsvpStatus = new RsvpStatus();

        guest1 = new Guest("David", false, false);
        guest2 = new Guest("Angel", false, true);
        guest3 = new Guest("Fred", true, false);
        guest4 = new Guest("Jessy", true, true);
        guest5 = new Guest("Katy", true, true);
    }

    @Test
    public void testAddOneConfirmedGuests() {
        testRsvpStatus.addConfirmedGuests(guest2);
        assertEquals(guest2, testRsvpStatus.getConfirmedGuests().get(0));
    }

    @Test
    public void testAddMultipleConfirmedGuests() {
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        assertEquals(guest2, testRsvpStatus.getConfirmedGuests().get(0));
        assertEquals(guest4, testRsvpStatus.getConfirmedGuests().get(1));
        assertEquals(guest5, testRsvpStatus.getConfirmedGuests().get(2));
    }

    @Test
    public void testGetNumOfConfirmedGuests() {
        assertEquals(0, testRsvpStatus.getNumOfConfirmedGuests());

        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        assertEquals(3, testRsvpStatus.getNumOfConfirmedGuests());
    }

    @Test
    public void testAddOneDeclinedGuests() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addDeclinedGuests(guest3);

        assertEquals(guest1, testRsvpStatus.getDeclinedGuests().get(0));
    }

    @Test
    public void testAddMultipleDeclinedGuests() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addDeclinedGuests(guest3);

        assertEquals(guest1, testRsvpStatus.getDeclinedGuests().get(0));
        assertEquals(guest3, testRsvpStatus.getDeclinedGuests().get(1));
    }

    @Test
    public void testMultipleMixedGuests() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addDeclinedGuests(guest3);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        assertEquals(3, testRsvpStatus.getNumOfConfirmedGuests());
        assertEquals(2, testRsvpStatus.getNumOfDeclinedGuests());
    }
}
