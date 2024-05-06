package com.example.happyuapp;

public class Event {
    private long dateInMillis;
    private String eventName;
    private String description;


    public Event(long dateInMillis, String eventName) {
        this.dateInMillis = dateInMillis;
        this.eventName = eventName;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public String getEventName() {
        return eventName;
    }
}