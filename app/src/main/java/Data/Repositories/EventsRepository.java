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
     * Get all events that user has created
     * @param userId id of the user
     * @return ArrayList of events; null if user has not any event
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
