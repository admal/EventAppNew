package models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Message")
public class ParseMessage extends ParseObject
{
    public void setSender(ParseUser sender){put("sender", sender);}

    public void setEvent(ParseEvent event){put("event", event);}

    public void setContent(String content) {put("content", content);}

    public ParseUser getSender() throws ParseException {
        ParseUser sender = this.getParseObject("event").fetchIfNeeded();
        return  sender;
    }

    public ParseEvent getEvent() throws ParseException {
        ParseEvent event = this.getParseObject("event").fetchIfNeeded();
        return  event;
    }

    public String getContent() {return getString("content");}
}
