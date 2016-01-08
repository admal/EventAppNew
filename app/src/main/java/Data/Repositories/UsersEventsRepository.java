package Data.Repositories;

import java.util.ArrayList;

import models.DataEntity;
import models.Event;
import models.UsersEvents;

/**
 * Created by Adam on 2015-11-24.
 * Repository of all events
 */
public class UsersEventsRepository
{
    private ArrayList<UsersEvents> events;

    public UsersEventsRepository(ArrayList<UsersEvents> events) {
        this.events = events;
    }

    /**
     * Get all events that user was invited to.
     * @param userId id of the user
     * @return ArrayList of events that user was invited to; if there are no such events returns null
     */
    public ArrayList<UsersEvents> getAllUsersEvents(int userId) //TODO: Load data from database and put into arraylist
    {
        ArrayList<UsersEvents> retEvents = new ArrayList<UsersEvents>();
        for (UsersEvents event : this.events)
        {
            if(userId == event.user.getId())
                retEvents.add(event);
        }
        return retEvents.size() == 0 ? null: retEvents;
    }

    /**
     * Get event by event id
     * @param id id of event
     * @return event; if there is no event with such id then return null
     */
    public UsersEvents getEventById(final int id)
    {
        for (UsersEvents entity :events) {
            if (entity.event.getId() == id)
                return entity;
        }
        return null;
    }
}
