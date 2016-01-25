package models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Adam on 2016-01-23.
 * Parse subclass of UsersEvent.
 */
@ParseClassName("UsersEvent")
public class ParseUsersEvent extends ParseObject {
    public void setUser(ParseUser user)
    {
        put("user", user);
    }
    public void setEvent(ParseEvent event)
    {
        put("event",event);
    }

    public ParseEvent getEvent() throws ParseException {
        ParseEvent event = (ParseEvent) getParseObject("event").fetchIfNeeded();
        return event;
    }
    public ParseUser getUser() throws ParseException {
        ParseUser user = (ParseUser) getParseObject("user").fetchIfNeeded();
        return user;
    }
}
