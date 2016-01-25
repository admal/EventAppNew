package models;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2016-01-20.
 * Custom user of the application.
 */
public class AppUser {
    /**
     * ParseUser object to edit.
     */
    private ParseUser user;

    /**
     * Creates instance of the AppUser, and assigning chosen ParseUser to user field.
     * @param user
     */
    public AppUser(ParseUser user) {
        this.user = user;
    }

    //GETTERS

    /**
     * Get all events that user is invited to.
     * @return List interface containing ParseEvents objects that user is invited to
     * @throws ParseException if no event was found
     */
    public List<ParseEvent> getUsersEvents() throws ParseException {
        List<ParseUsersEvent> userEvents;
        List<ParseEvent> events = new ArrayList<>();
        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("user",user);
        userEvents = query.find();
        for (ParseUsersEvent userEvent : userEvents) {
            events.add(userEvent.getEvent());
        }
        return events;
    }

    /**
     * Gets all events that user is organizer.
     * @return List interface containing ParseEvents objects that user is invited to
     * @throws ParseException if no event was found
     */
    public List<ParseEvent> getCreatedEvents() throws ParseException {
        List<ParseEvent> allEvents  = getUsersEvents();
        List<ParseEvent> createdEvents = new ArrayList<>();
        for (ParseEvent event : allEvents) {
            if (event.getOwner().getObjectId() == user.getObjectId())
                createdEvents.add(event);
        }
        return createdEvents;
    }
}
