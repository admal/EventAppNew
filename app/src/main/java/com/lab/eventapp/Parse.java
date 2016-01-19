package com.lab.eventapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import models.TmpUser;
import models.User;
import com.parse.ParseObject;

/**
 * Created by Alpha on 1/19/2016.
 */
public class Parse extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        com.parse.Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(TmpUser.class);

        com.parse.Parse.initialize(this);

        //TODO: Remove
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
