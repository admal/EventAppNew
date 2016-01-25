package models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adam on 2016-01-19.
 * Parse event subclass, representing Event object in the database.
 */
@ParseClassName("Event")
public class ParseEvent extends ParseObject
{
    //GETTERS

    public String getTitle()
    {
        return getString("title");
    }

    /**
     * Get organizer of the event.
     * @return ParseUser object
     * @throws ParseException if user was not found
     */
    public ParseUser getOwner() throws ParseException {
        ParseUser owner = this.getParseObject("owner").fetchIfNeeded();
        return  owner;
    }
    public String getDescription()
    {
        return getString("description");
    }
    public LocalDateTime getStartDate()
    {
        return new LocalDateTime(getDate("startDate"));
    }
    public LocalDateTime getEndDate()
    {
        return new LocalDateTime(getDate("endDate"));
    }

    /**
     * Gets all users invited to the event.
     * @return List interface filled with users
     * @throws ParseException if any of UsersEvent was not found
     */
    public List<ParseUser> getUsers() throws ParseException {
        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("event", this);
        List<ParseUsersEvent> eventUsers =  query.find();
        List<ParseUser> users = new ArrayList<>();
        for (ParseUsersEvent u : eventUsers) {
            if(u.getUser().getUsername() != this.getOwner().getUsername())
                users.add(u.getUser());
        }
        return users;
    }
    /**
     * Gets all users invited to the event (also owner).
     * @return List interface filled with users
     * @throws ParseException if any of UsersEvent was not found
     */
    public List<ParseUser> getAllUsers() throws ParseException {
        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("event", this);
        List<ParseUsersEvent> eventUsers =  query.find();
        List<ParseUser> users = new ArrayList<>();
        for (ParseUsersEvent u : eventUsers) {
            users.add(u.getUser());
        }
        return users;
    }

    public String getPlace()
    {
        return getString("place");
    }
    //SETTERS
    public void setTitle(String title)
    {
        put("title", title);
    }
    public void setDescription(String desc)
    {
        put("description", desc);
    }
    public void setStartDate(LocalDateTime date)
    {
        put("startDate", date.toDate());
    }
    public void setEndDate(LocalDateTime date)
    {
        Date d = date.toDate();
        put("endDate", date.toDate());
    }
    public void setOwner(ParseUser user)
    {
        put("owner", user);
    }

    public void setPlace(String place)
    {
        put("place", place);
    }

    /**
     * Remove user from the event.
     * @param user ParseUser object that is needed to be removed from the event.
     * @throws ParseException if user was not found in the event
     */
    public void removeUser(ParseUser user) throws ParseException {
        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("event", this);

        List<ParseUsersEvent> usersEvents = query.find();

        for (ParseUsersEvent e: usersEvents)
        {
            if(e.getUser().getObjectId() == user.getObjectId())
            {
                e.delete();
                return;
            }
        }
    }

    public List<ParseUsersEvent> getUsersEvents() throws ParseException {
        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("event", this);
         return query.find();
    }

}
