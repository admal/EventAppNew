package com.lab.eventapp;

import com.parse.ParseObject;

import models.ParseEvent;

/**
 * Created by Alpha on 1/19/2016.
 */
public class Parse extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        com.parse.Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(ParseEvent.class);
        com.parse.Parse.initialize(this);

        //TODO: Remove
        //Adam: I removed it
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
}
