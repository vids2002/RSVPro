package persistence;

import model.Guest;
import model.GuestList;
import model.RsvpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads Guest List from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads guestlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public void readApplicationState(GuestList guestList, RsvpStatus confirmedGuestList, RsvpStatus declinedGuestList)
            throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        // Parse Guest List
        parseGuestList(jsonObject.getJSONObject("guestList"), guestList);

        // Parse Confirmed Guests List
        parseRsvpStatusList(jsonObject.getJSONObject("confirmedGuests"), confirmedGuestList, true);

        // Parse Declined Guests List
        parseRsvpStatusList(jsonObject.getJSONObject("declinedGuests"), declinedGuestList, false);
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Guest List from JSON object
    private void parseGuestList(JSONObject guestListJson, GuestList guestList) {
        JSONArray guestsJsonArray = guestListJson.getJSONArray("guests");
        for (int i = 0; i < guestsJsonArray.length(); i++) {
            JSONObject guestJson = guestsJsonArray.getJSONObject(i);
            Guest guest = new Guest(guestJson.getString("name"), guestJson.getBoolean("plusOne"),
                    guestJson.getBoolean("rsvpStatus"));
            guestList.addGuest(guest);
        }
    }

    // EFFECTS: parses RSVP Status List from JSON object
    private void parseRsvpStatusList(JSONObject rsvpStatusJson, RsvpStatus rsvpStatus, boolean isConfirmed) {
        JSONArray guestsJsonArray = rsvpStatusJson.getJSONArray(isConfirmed ? "confirmedGuests" : "declinedGuests");
        for (int i = 0; i < guestsJsonArray.length(); i++) {
            JSONObject guestJson = guestsJsonArray.getJSONObject(i);
            Guest guest = new Guest(guestJson.getString("name"), guestJson.getBoolean("plusOne"),
                    guestJson.getBoolean("rsvpStatus"));
            if (isConfirmed) {
                rsvpStatus.addConfirmedGuests(guest);
            } else {
                rsvpStatus.addDeclinedGuests(guest);
            }
        }
    }


}
