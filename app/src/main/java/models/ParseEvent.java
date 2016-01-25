package models;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 2016-01-19.
 */
@ParseClassName("Event")
public class ParseEvent extends ParseObject
{
    //GETTERS
    public String getTitle()
    {
        return getString("title");
    }
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

    public List<ParseUser> getUsers() throws ParseException {
//        ParseRelation<ParseUser> relation = getRelation("users");
//        List<ParseUser> users =   relation.getQuery().find();

        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("event", this);
        List<ParseUsersEvent> eventUsers =  query.find();
        List<ParseUser> users = new ArrayList<>();
        for (ParseUsersEvent u : eventUsers) {
            Log.d("getUsers()", "User: " + u.getUser().getObjectId() + ": event: " + getObjectId());
            if(u.getUser().getUsername() != this.getOwner().getUsername())
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
        put("endDate", date.toDate());
    }
    public void setOwner(ParseUser user)
    {
        put("owner", user);
    }
    public void addUserToEvent(ParseUser user) throws ParseException {
        ParseRelation<ParseObject> relation = getRelation("users");
        relation.add(user);
        save();
    }
    public void setPlace(String place)
    {
        put("place", place);
    }


    public void removeUser(ParseUser user) throws ParseException {
        ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
        query.whereEqualTo("event", this);

        List<ParseUsersEvent> usersEvents = query.find();

        for (ParseUsersEvent e: usersEvents)
        {
            if(e.getUser().getObjectId() == user.getObjectId())
            {
                e.delete();
                Log.d("RemoveUser", "finally!");
                return;
            }
        }
    }

}
