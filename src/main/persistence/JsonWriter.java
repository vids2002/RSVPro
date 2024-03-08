package persistence;

import model.GuestList;
import model.RsvpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of Guest List to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Guest List to file
    public void write(GuestList guestList, RsvpStatus confirmedGuests, RsvpStatus declinedGuests) {
        JSONObject json = new JSONObject();
        // Assuming toJson of GuestList and RsvpStatus returns a JSONObject with the respective data under specific keys
        json.put("guestList", guestList.toJson()); // This assumes your guestList.toJson() method returns a structure
        // where the guest list is already under a key like "guests".
        json.put("confirmedGuests", confirmedGuests.toJson()); // Adjust if your structure is different.
        json.put("declinedGuests", declinedGuests.toJson()); // Adjust if your structure is different.

        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
