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

// Represents a reader that reads Guest List from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

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

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    private void parseGuestList(JSONObject guestListJson, GuestList guestList) {
        JSONArray guestsJsonArray = guestListJson.getJSONArray("guests");
        for (int i = 0; i < guestsJsonArray.length(); i++) {
            JSONObject guestJson = guestsJsonArray.getJSONObject(i);
            Guest guest = new Guest(guestJson.getString("name"), guestJson.getBoolean("plusOne"),
                    guestJson.getBoolean("rsvpStatus"));
            guestList.addGuest(guest);
        }
    }

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

//    // EFFECTS: reads Guest List from file and returns it;
//    // throws IOException if an error occurs reading data from file
//    public GuestList readGl() throws IOException {
//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return parseGuestList(jsonObject);
//    }

//    // EFFECTS: reads RSVP Status List from file and returns it;
//    // throws IOException if an error occurs reading data from file
//    public RsvpStatus readRsvp() throws IOException {
//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return parseRsvpStatusList(jsonObject);
//    }

//    // EFFECTS: reads source file as string and returns it
//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//
//        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
//            stream.forEach(s -> contentBuilder.append(s));
//        }
//
//        return contentBuilder.toString();
//    }

//    // EFFECTS: parses Guest List from JSON object and returns it
//    private GuestList parseGuestList(JSONObject jsonObject) {
//        GuestList gl = new GuestList();
//        addGuests(gl, jsonObject);
//        return gl;
//    }

//    // EFFECTS: parses Rsvp Status List from JSON object and returns it
//    private RsvpStatus parseRsvpStatusList(JSONObject jsonObject) {
//        RsvpStatus rsvpS = new RsvpStatus();
//        addRsvpGuests(rsvpS, jsonObject);
//        return rsvpS;
//    }

//    // MODIFIES: gl
//    // EFFECTS: parses guests from JSON object and adds them to Guest List
//    private void addGuests(GuestList gl, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("guests");
//        for (Object json : jsonArray) {
//            JSONObject nextGuest = (JSONObject) json;
//            addGuest(gl, nextGuest);
//        }
//    }
//
//    // MODIFIES: gl
//    // EFFECTS: parses guest from JSON object and adds it to Guest List
//    private void addGuest(GuestList gl, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        boolean plusOneStatus = jsonObject.getBoolean("plusOne");
//        boolean rsvpStatus = jsonObject.getBoolean("rsvpStatus");
//        Guest guest = new Guest(name, plusOneStatus, rsvpStatus);
//        gl.addGuest(guest);
//    }

//
//    // MODIFIES: rsvpS
//    // EFFECTS: parses rsvp-ed guests from JSON object and adds them to Confirmed and Declined Guest Lists
//    private void addRsvpGuests(RsvpStatus rsvpS, JSONObject jsonObject) {
//        JSONArray jsonArrayCg = jsonObject.getJSONArray("confirmed");
//        JSONArray jsonArrayDg = jsonObject.getJSONArray("declined");
//        for (Object json : jsonArrayCg) {
//            JSONObject nextConfirmedGuest = (JSONObject) json;
//            addConfirmedGuests(rsvpS, nextConfirmedGuest);
//        }
//        for (Object json : jsonArrayDg) {
//            JSONObject nextDeclinedGuest = (JSONObject) json;
//            addDeclinedGuests(rsvpS, nextDeclinedGuest);
//        }
//    }
//
//    // MODIFIES: rsvpS
//    // EFFECTS: parses rsvp-ed confirmed guests from JSON object and adds it to Confirmed Guest List
//    private void addConfirmedGuests(RsvpStatus rsvpS, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        boolean plusOneStatus = jsonObject.getBoolean("plusOne");
//        boolean rsvpStatus = jsonObject.getBoolean("rsvpStatus");
//        Guest guest = new Guest(name, plusOneStatus, rsvpStatus);
//        rsvpS.addConfirmedGuests(guest);
//    }
//
//    // MODIFIES: rsvpS
//    // EFFECTS: parses rsvp-ed declined guests from JSON object and adds it to Declined Guest List
//    private void addDeclinedGuests(RsvpStatus rsvpS, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        boolean plusOneStatus = jsonObject.getBoolean("plusOne");
//        boolean rsvpStatus = jsonObject.getBoolean("rsvpStatus");
//        Guest guest = new Guest(name, plusOneStatus, rsvpStatus);
//        rsvpS.addDeclinedGuests(guest);
//    }


}
