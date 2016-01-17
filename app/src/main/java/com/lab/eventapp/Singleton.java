package com.lab.eventapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import Data.Repositories.EventsRepository;
import Data.Repositories.UsersEventsRepository;
import com.lab.eventapp.Parsers.JsonParser;
import http.DbHandler;
import models.Event;
import models.User;
import models.UsersEvents;

/**
 * Created by Adam on 2015-11-25.
 */
public class Singleton
{
    private static Singleton instance = null;


    private ArrayList<Event> tmpEvents = new ArrayList<>();
    private User CurrentUser;
    private UsersEventsRepository usersEventRepo;
    private EventsRepository eventRepo;

    protected Singleton()
    {
        // Exists only to defeat instantiation.

        //get logged users
        String jsonUser = "";
        try {
            jsonUser = DbHandler.getUserData(2);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        CurrentUser = parser.parseUsers(jsonUser)[0];
        //get his events
        String jsonEvents = "";
        try {
            jsonEvents = DbHandler.getEventsOwner(CurrentUser.getId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CurrentUser.usersEvents = new ArrayList<>();
        CurrentUser.createdEvents = new ArrayList<>();
        CurrentUser.createdEvents = new ArrayList<>(Arrays.asList(parser.parseEvents(jsonEvents)));



        //TMP initialization staff (until there is working external database)
       // CurrentUser = new User(1,"user1");

        User tmpUser2 = new User(2,"user2");

        //user2's events
        tmpEvents.add(new Event(1,"event1",tmpUser2, new Date()));
        tmpEvents.add(new Event(2,"event2",tmpUser2,new Date()));
        tmpEvents.add(new Event(3, "event3",tmpUser2, new Date()));
        tmpEvents.add(new Event(4, "event4",tmpUser2, new Date()));
        //user1's events
        tmpEvents.add(new Event(5,"Users1 Event 1", CurrentUser, new Date()));
        tmpEvents.add(new Event(6,"Users1 Event 2", CurrentUser, new Date()));

        //put into users events that he is invited to
        CurrentUser.usersEvents.add(new UsersEvents(CurrentUser,tmpEvents.get(0)));
        CurrentUser.usersEvents.get(0).event.setDescription("Description \n of the best \n users!!!!");
        CurrentUser.usersEvents.get(0).isGoing = true;
        CurrentUser.usersEvents.add(new UsersEvents(CurrentUser,tmpEvents.get(1)));
        CurrentUser.usersEvents.add(new UsersEvents(CurrentUser,tmpEvents.get(2)));
        CurrentUser.usersEvents.add(new UsersEvents(CurrentUser,tmpEvents.get(3)));
        //put into users events that he has created
//        CurrentUser.createdEvents = new ArrayList<>();
//        CurrentUser.createdEvents.add(tmpEvents.get(4));
//        CurrentUser.createdEvents.add(tmpEvents.get(5));

        //create repository with users' events
        usersEventRepo = new UsersEventsRepository(CurrentUser.usersEvents);
        //create repository with all events
        eventRepo = new EventsRepository(tmpEvents);

        //////////////////////////////////////////////////////////////////////////
    }


    public static Singleton getInstance()
    {
        if(instance == null)
            instance = new Singleton();

        return instance;
    }

    public ArrayList<Event> getTmpEvents() {
        return tmpEvents;
    }

    public User getCurrentUser() {
        return CurrentUser;
    }

    public UsersEventsRepository getUsersEventRepo() {
        return usersEventRepo;
    }

    public EventsRepository getEventRepo() {
        return eventRepo;
    }
}
