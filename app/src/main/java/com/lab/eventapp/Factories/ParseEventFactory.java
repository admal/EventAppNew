package com.lab.eventapp.Factories;

import com.lab.eventapp.models.ParseEvent;
import com.lab.eventapp.models.ParseUsersEvent;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.joda.time.LocalDateTime;

/**
 * Created by Adam on 2016-01-26.
 */
public class ParseEventFactory {
    public static ParseEvent Create(String title, String description, LocalDateTime startTime, LocalDateTime endTime, String place)
    {
        ParseEvent newEvent = new ParseEvent();
        newEvent.setStartDate(startTime);
        newEvent.setEndDate(endTime);
        newEvent.setTitle(title);
        newEvent.setDescription(description);
        newEvent.setPlace(place);
        newEvent.setOwner(ParseUser.getCurrentUser());
        return  newEvent;
    }
}
