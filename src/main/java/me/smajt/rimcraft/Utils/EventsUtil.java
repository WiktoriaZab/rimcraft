package me.smajt.rimcraft.Utils;

import me.smajt.rimcraft.Functions.GMFunctions;
import me.smajt.rimcraft.Models.Event;
import me.smajt.rimcraft.Models.Plant;
import me.smajt.rimcraft.Models.TempUser;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class EventsUtil {
    private static final ArrayList<Event> EventsActive = new ArrayList<>();

    public static Event createEvent(GMFunctions.Events event){
        Event event1 = new Event(event);
        EventsActive.add(event1);

        return event1;
    }

    public static Event findEvent(GMFunctions.Events event){
        for (Event event1 : EventsActive){
            if (event1.getEventType().equals(event)) {
                return event1;
            }
        }
        return null;
    }

    public static ArrayList<Event> getAllEvents(){
        return EventsActive;
    }

    public static void deleteEvent(GMFunctions.Events event){
        for (Event event1 : EventsActive){
            if (event1.getEventType().equals(event)) {
                EventsActive.remove(event1);
                break;
            }
        }
    }

    public static void deleteAllEvents(){
        for (Event event1 : EventsActive){
            EventsActive.remove(event1);
        }
    }
}
