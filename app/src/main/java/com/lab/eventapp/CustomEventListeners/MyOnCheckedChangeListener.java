package com.lab.eventapp.CustomEventListeners;

import android.util.Log;
import android.widget.CompoundButton;

import com.lab.eventapp.Singleton;

import models.UsersEvents;

/**
 * Created by Adam on 2015-11-25.
 */
public class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int eventId = (int)buttonView.getTag();
        UsersEvents event =  Singleton.getInstance().getUsersEventRepo().getEventById(eventId);
        event.isGoing = !event.isGoing;
//        for (UsersEvents e :
//                Singleton.getInstance().getCurrentUser().usersEvents) {
//            Log.d("isGoing:", e.event.getTitle() + e.isGoing);
//        }
    }
}