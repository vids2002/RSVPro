package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestListTest {
    private GuestList testGuestList;

    private Guest guest1;
    private Guest guest2;
    private Guest guest3;
    private Guest guest4;
    private Guest guest5;

    @BeforeEach
    public void runBefore() {
        testGuestList = new GuestList();

        guest1 = new Guest("David", false, false);
        guest2 = new Guest("Angel", false, true);
        guest3 = new Guest("Fred", true, false);
        guest4 = new Guest("Jessy", true, true);
        guest5 = new Guest("Katy", true, true);
    }

    @Test
    public void testAddOneGuest() {
        testGuestList.addGuest(guest1);

        assertEquals(guest1, testGuestList.getListOfGuests().get(0));
    }

    @Test
    public void testMultipleGuests() {
        testGuestList.addGuest(guest1);
        testGuestList.addGuest(guest2);
        testGuestList.addGuest(guest4);

        assertEquals(guest1, testGuestList.getListOfGuests().get(0));
        assertEquals(guest2, testGuestList.getListOfGuests().get(1));
        assertEquals(guest4, testGuestList.getListOfGuests().get(2));
    }

    @Test
    public void testRemoveOnlyGuest() {
        testGuestList.addGuest(guest1);
        testGuestList.removeGuest(guest1);

        assertNull(testGuestList.getListOfGuests());
    }

    @Test
    public void testRemoveMultipleGuest() {
        testGuestList.addGuest(guest1);
        testGuestList.addGuest(guest2);
        testGuestList.addGuest(guest4);
        testGuestList.addGuest(guest5);

        testGuestList.removeGuest(guest2);
        assertEquals(guest4, testGuestList.getListOfGuests().get(1));

        testGuestList.removeGuest(guest5);
        assertEquals(2, testGuestList.getNumOfInvites());
        assertEquals(guest4, testGuestList.getListOfGuests().get(1));
    }

    @Test
    public void testGetListOfGuests() {
        //TODO: how do i do this?? how to return a list yk?
    }

    @Test
    public void testClearGuests() {
        testGuestList.addGuest(guest1);
        testGuestList.addGuest(guest2);
        testGuestList.addGuest(guest4);
        testGuestList.addGuest(guest5);

        testGuestList.clearGuests();
        assertNull(testGuestList.getListOfGuests());
    }

    @Test
    public void testRsvpedGuests() {
        testGuestList.addGuest(guest1);
        testGuestList.addGuest(guest2);
        testGuestList.addGuest(guest3);
        testGuestList.addGuest(guest4);
        testGuestList.addGuest(guest5);

        //Testing whether guest1 is only in the confirmed guests list
        assertTrue(testGuestList.rsvpedGuests().getConfirmedGuests().contains(guest2));
        assertFalse(testGuestList.rsvpedGuests().getDeclinedGuests().contains(guest2));

        //Testing the size of confirmed guest list
        assertEquals(3, testGuestList.rsvpedGuests().getNumOfConfirmedGuests());

        //Testing whether guest1 is only in declined guest list
        assertTrue(testGuestList.rsvpedGuests().getDeclinedGuests().contains(guest1));
        assertFalse(testGuestList.rsvpedGuests().getConfirmedGuests().contains(guest1));

        //Testing the size of the decline guest list
        assertEquals(2, testGuestList.rsvpedGuests().getNumOfDeclinedGuests());
    }

}
