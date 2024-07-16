package com.example.assignment_1;

import java.util.concurrent.ThreadLocalRandom;

public class Event {

    private final String EVENT_ID;
    private String eventName;
    private String categoryID;
    private int ticketsAvailable;
    private boolean isActive;

    public Event(String eventName, String categoryID, int ticketsAvailable, boolean isActive) {
        this.eventName = eventName;
        this.categoryID = categoryID;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;
        this.EVENT_ID = generateEventID();
    }

    public String getEVENT_ID() {
        return EVENT_ID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private String generateEventID()
    {
        String categoryID;
        categoryID = "E";

        categoryID += Utility.getRandomLetter();
        categoryID += Utility.getRandomLetter();
        categoryID += "-";
        categoryID += ThreadLocalRandom.current().nextInt(10000,99999);

        return categoryID;

    }

}
