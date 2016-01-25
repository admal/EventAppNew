package com.lab.eventapp;

import com.parse.ParseObject;

import models.ParseEvent;
import models.ParseMessage;
import models.ParseUsersEvent;


public class Parse extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        com.parse.Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(ParseEvent.class);
        ParseObject.registerSubclass(ParseUsersEvent.class);
        ParseObject.registerSubclass(ParseMessage.class);
        com.parse.Parse.initialize(this);

        //TODO: Remove
        //Adam: I removed it
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
}
