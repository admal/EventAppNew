package Data.Repositories;

import java.util.ArrayList;

import models.Event;

/**
 * Created by Adam on 2015-11-24.
 */
public class EventsRepository
{
    public ArrayList<Event> events;

    public EventsRepository(ArrayList<Event> events) {
        this.events = events;
    }

    public EventsRepository() {
        //db staff
    }

    /**
     * Get all events that users has created
     * @param userId id of the users
     * @return ArrayList of events; null if users has not any users
     */
    public ArrayList<Event> getUsersEvents(int userId)
    {
        ArrayList<Event> retEvents = new ArrayList<Event>();
        for (Event event : this.events)
        {
            if(userId == event.getOwnerId())
                retEvents.add(event);
        }
        return retEvents.size() == 0 ? null: retEvents;
    }

    public Event getEventById(int eventId) {
        for (Event ev : events) {
            if (ev.getId() == eventId)
                return ev;
        }
        return null;
    }
}
