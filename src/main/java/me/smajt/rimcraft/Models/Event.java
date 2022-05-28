package me.smajt.rimcraft.Models;

import me.smajt.rimcraft.Functions.GMFunctions;

public class Event {
    private GMFunctions.Events EventType;

    public GMFunctions.Events getEventType() {
        return EventType;
    }

    public void setEventType(GMFunctions.Events eventType) {
        EventType = eventType;
    }

    public Event(GMFunctions.Events eventType) {
        EventType = eventType;
    }

}
