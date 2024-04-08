# My Personal Project: RSVPro

## Guest List Management

The proposed application aims to assist hosts or event managers
in efficiently managing the guest list for a specific event, 
including monitoring RSVP statuses. 

My inspiration for this
project comes from my upbringing in a large family where my 
mother frequently hosted various events. Recognizing the challenges
of organizing and tracking guests for these gatherings, I see the 
potential for this application to be a valuable tool not only for my 
family but also for others facing similar scenarios. The personal
connection to the project motivates me to create a user-friendly 
and effective solution that can streamline the guest management
process for a wide range of events.

## User Stories:
- As a user, I want to be able to add a guest to my party
- As a user, I want to be able to remove a guest from my party
- As a user, I want to be able to update the RSVP status and Plus One status of a guest
- As a user, I want to be able to clear my whole Guest List
- As a user, I want to be able to view the list of guests invited to my party
- As a user, I want to be able to view the list of guests that have confirmed my invite
  and a list of guests that have declined my invite. 
- As a user, I want to be able to view details of a specific guest
- As a user, I want to be given the option in main menu to save the current status of my application to file
- As a user, I want to be given the option in main menu to load my Guest List from file

## Instructions for Grader:
![GIF](data/GIF_CPSC210_PROEJCT_DEMO.gif)
- You can find the first required action related to the user story "adding multiple Xs to a Y"
  by pressing the "Edit Guest List" button on the Main Page and then pressing the "Add a Guest" button
- You can find the second required action related to the user story "adding multiple Xs to a Y"
  by pressing the "Edit Guest List" button on the Main Page and then pressing the "Remove a Guest" button
- You can find a third action related to the user story "adding multiple Xs to a Y" by pressing the
  "Edit Guest List" button on the Main Page and then pressing the "Change Plus-One Status" button
- You can view all Xs added or changed to Y by pressing the "View Guest List" on the main page and then
  pressing the "View Invited Guests" button
- You can locate my visual component by starting up the application
- You can save the state of my application by pressing the "Save Guest List" button on the main page
- You can load the state of my application by pressing the "Load Guest List" button on the main page

## Phase 4: Task 2
- Refer to the attached demo video above: 
    - The events that occur when the program runs as shown in the
      demo video is as follows: 
  

            Sun Apr 07 16:10:31 PDT 2024
            Guest List was loaded from file.
    
            Sun Apr 07 16:10:34 PDT 2024
            The Invited Guest List was viewed.
    
            Sun Apr 07 16:10:42 PDT 2024
            Max was added to the Guest List.
    
            Sun Apr 07 16:10:46 PDT 2024
            Perez was added to the Guest List.
            
            Sun Apr 07 16:10:51 PDT 2024
            Ricciardo was added to the Guest List.
            
            Sun Apr 07 16:10:58 PDT 2024
            The Invited Guest List was viewed.

            Sun Apr 07 16:11:02 PDT 2024
            Confirmed Guest List was viewed.
    
            Sun Apr 07 16:11:04 PDT 2024
            Declined Guest List was viewed.
            
            Sun Apr 07 16:11:14 PDT 2024
            Max was removed from the Guest List.
    
            Sun Apr 07 16:11:18 PDT 2024
            Perez was removed from the Guest List.
    
            Sun Apr 07 16:11:28 PDT 2024
            Ricciardo's RSVP status was updated to Declined.
    
            Sun Apr 07 16:11:36 PDT 2024
            Guest's Plus One Status was updated.
    
            Sun Apr 07 16:11:41 PDT 2024
            Confirmed Guest List was viewed.
            
            Sun Apr 07 16:11:43 PDT 2024
            Declined Guest List was viewed.
            
            Sun Apr 07 16:11:53 PDT 2024
            Ricciardo's RSVP status was updated to Confirmed.
    
            Sun Apr 07 16:11:58 PDT 2024
            The Invited Guest List was viewed.
            
            Sun Apr 07 16:12:01 PDT 2024
            Confirmed Guest List was viewed.
    
            Sun Apr 07 16:12:03 PDT 2024
            Declined Guest List was viewed.
            
            Sun Apr 07 16:12:07 PDT 2024
            Guest List was saved to file.

## Phase 4: Task 3
If I had the time, I would choose to refactor my program by implementing the Observer Design
Pattern:
- Subject: GuestList and RSVPStatus
- Observer: Any classes that need to reflect changes in the subject classes (e.g: My UI classes
  and the EventLogging class.)


- Implementation: 
  - I would need to create a Subject interface and an Observer interface
  - I would get my Subject classes (GuestList and RSVP Status) to implement the Subject
    interface and all Observer classes to implement the Observer interface

This design pattern will lead to reduced coupling between my classes because changes in 
one part of the application can be independently handled by interested parties. In other
words, with this pattern, the Subject classes don't need to know specifics about its observers,
only that they implement the observable interface.