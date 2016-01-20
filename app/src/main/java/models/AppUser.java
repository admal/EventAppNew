package models;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Adam on 2016-01-20.
 */
public class AppUser {
    private ParseUser user;

    public AppUser(ParseUser user) {
        this.user = user;
    }

    //GETTERS
    public List<ParseEvent> getUsersEvents() throws ParseException {
        ParseRelation<ParseEvent> relation = user.getRelation("events");
        List<ParseEvent> events = relation.getQuery().find();
        return events;
    }

    public List<ParseEvent> getCreatedEvents() throws ParseException {
        ParseRelation<ParseEvent> relation = user.getRelation("events");
        ParseQuery<ParseEvent> query = relation.getQuery().whereEqualTo("owner", user);
        List<ParseEvent> events = query.find();
        return events;
    }

    public ParseEvent getUserEventById(String eventId) throws Exception {
        ParseRelation<ParseEvent> relation = user.getRelation("events");
        ParseEvent event = relation.getQuery().get(eventId);
        if(event == null)
            throw new Exception("There is no event with the given id! (id: " + eventId + ")");
        return event;
//        List<ParseEvent> events = query.find();
//        if(events.size() > 1) //it means there is some error
//            throw new Exception("There is more than 1 event with the given id!");
//        if(events.size() == 1) //it means there is some error
//            throw new Exception("There is no event with the given id! (id: " + eventId + ")");
//        return events.get(0);
    }

    //SETTERS
    public void AddEvent(ParseEvent event) throws ParseException {
        ParseRelation<ParseEvent> relation = user.getRelation("events");
        relation.add(event);
        user.save();
    }
}
