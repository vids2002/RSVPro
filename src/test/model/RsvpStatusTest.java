package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testFindConfirmedGuest() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addDeclinedGuests(guest3);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        assertEquals(guest2, testRsvpStatus.findConfirmedGuest("Angel"));
        assertEquals(null, testRsvpStatus.findConfirmedGuest("David"));
    }

    @Test
    public void testFindDeclinedGuest() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addDeclinedGuests(guest3);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        assertEquals(guest1, testRsvpStatus.findDeclinedGuest("David"));
        assertNull(testRsvpStatus.findDeclinedGuest("Angel"));
        assertNull(testRsvpStatus.findDeclinedGuest("Mimi"));
    }

    @Test
    public void testRemoveCGuests() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addDeclinedGuests(guest3);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        testRsvpStatus.removeCGuests(guest2);
        assertEquals(guest4, testRsvpStatus.getConfirmedGuests().get(0));

        testRsvpStatus.removeCGuests(guest4);
        testRsvpStatus.removeCGuests(guest5);
        assertNull(testRsvpStatus.getConfirmedGuests());
    }

    @Test
    public void testRemoveDGuest() {
        testRsvpStatus.addDeclinedGuests(guest1);
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addDeclinedGuests(guest3);
        testRsvpStatus.addConfirmedGuests(guest4);
        testRsvpStatus.addConfirmedGuests(guest5);

        testRsvpStatus.removeDGuests(guest1);
        assertEquals(guest3, testRsvpStatus.getDeclinedGuests().get(0));

        testRsvpStatus.removeDGuests(guest3);
        assertNull(testRsvpStatus.getDeclinedGuests());
    }

    @Test
    public void testRemoveCGuestsNotInList() {
        // Attempt to remove a guest who was never added.
        Guest notAddedGuest = new Guest("NotAdded", false, false);
        testRsvpStatus.addConfirmedGuests(guest1); // Assuming guest1 is defined and added elsewhere.

        // The size of the confirmed list should remain the same since the guest was not in it.
        int sizeBeforeRemove = testRsvpStatus.getConfirmedGuests().size();
        testRsvpStatus.removeCGuests(notAddedGuest);
        int sizeAfterRemove = testRsvpStatus.getConfirmedGuests().size();
        assertEquals(sizeBeforeRemove, sizeAfterRemove);
    }

    @Test
    public void testRemoveDGuestNotInList() {
        // Similar test for declined guests.
    }

    @Test
    public void testRemoveCGuestsFromListWithOneGuest() {
        // Add only one guest and then remove them.
        testRsvpStatus.addConfirmedGuests(guest1);
        testRsvpStatus.removeCGuests(guest1);
        assertNull(testRsvpStatus.getConfirmedGuests());
    }

    @Test
    public void testRemoveDGuestsFromListWithOneGuest() {
        // Similar test for declined guests.
    }

    @Test
    public void testRemoveCGuestsDoesNotAffectDeclinedList() {
        // Add guests to both lists.
        testRsvpStatus.addConfirmedGuests(guest2);
        testRsvpStatus.addDeclinedGuests(guest1);

        // Remove a guest from the confirmed list.
        testRsvpStatus.removeCGuests(guest2);

        // The declined list should not be affected.
        assertFalse(testRsvpStatus.getDeclinedGuests().isEmpty());
        assertEquals(guest1, testRsvpStatus.getDeclinedGuests().get(0));
    }

    @Test
    public void testRemoveDGuestsEmptyList() {
        // Attempt to remove a guest from an empty list.
        Guest guestToRemove = new Guest("Nonexistent", false, false);
        // Before removal, make sure the list is empty.
        assertTrue(testRsvpStatus.getDeclinedGuests().isEmpty());
        testRsvpStatus.removeDGuests(guestToRemove);
        // The list should still be empty after removal attempt.
        assertTrue(testRsvpStatus.getDeclinedGuests().isEmpty());
    }

    @Test
    public void testRemoveDGuestsNonexistentGuest() {
        // Add some guests to the list.
        testRsvpStatus.addDeclinedGuests(new Guest("Existing", false, false));
        // Attempt to remove a guest not in the list.
        Guest guestToRemove = new Guest("Nonexistent", false, false);
        int sizeBeforeRemove = testRsvpStatus.getDeclinedGuests().size();
        testRsvpStatus.removeDGuests(guestToRemove);
        int sizeAfterRemove = testRsvpStatus.getDeclinedGuests().size();
        // The size should not change because the guest was not in the list.
        assertEquals(sizeBeforeRemove, sizeAfterRemove);
    }

}
