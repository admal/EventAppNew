package models;

import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bolts.Task;

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
    public Date getStartDate()
    {
        return getDate("startDate");
    }
    public Date getEndDate()
    {
        return getDate("endDate");
    }
    public List<ParseUser> getUsers() throws ParseException {
        ParseRelation<ParseUser> relation = getRelation("users");
        List<ParseUser> users =   relation.getQuery().find();
//        List<ParseUser> retUsers = new ArrayList<>();
//        for (ParseObject user :
//                users) {
//            retUsers.add((ParseUser)user);
//        }
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
    public void setStartDate(Date date)
    {
        put("startDate", date);
    }
    public void setEndDate(Date date)
    {
        put("endDate", date);
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

}
