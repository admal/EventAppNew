package models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Adam on 2016-01-19.
 */
@ParseClassName("Event")
public class ParseEvent extends ParseObject
{
    public String getTitle()
    {
        return getString("title");
    }

    public void setTitle(String title)
    {
        put("title", title);
    }
    public ParseUser getOwner() throws ParseException {
        ParseUser owner = this.getParseObject("owner").fetchIfNeeded();
        return  owner;
    }
}
