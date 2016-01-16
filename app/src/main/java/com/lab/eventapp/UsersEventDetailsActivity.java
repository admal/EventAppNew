package com.lab.eventapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lab.eventapp.CustomEventListeners.MyOnCheckedChangeListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import Data.Repositories.EventsRepository;
import Data.Repositories.UsersEventsRepository;
import models.Event;
import models.UsersEvents;

public class UsersEventDetailsActivity extends AppCompatActivity {

    Event event;
    UsersEvents userEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_event_details);
        Intent intent = getIntent();
        int eventId = intent.getIntExtra("eventid", -1) + 1; //we give here index in table but ids start from 1

        EventsRepository eventRepo = Singleton.getInstance().getEventRepo();


        event = eventRepo.getEventById(eventId);
        userEvent = Singleton.getInstance().getUsersEventRepo().getEventById(event.getId());

        if(event != null && userEvent != null)
        {
            TextView title = (TextView)findViewById(R.id.lblEventTitle);
            TextView place = (TextView)findViewById(R.id.lblPlace);
            TextView start = (TextView)findViewById(R.id.lblStart);
            TextView end = (TextView)findViewById(R.id.lblEnd);
            TextView creator = (TextView)findViewById(R.id.lblCreator);
            TextView desc = (TextView)findViewById(R.id.lblDesc);

            title.setText(event.getTitle());
            place.setText(event.getPlace());
            desc.setText(event.getDescription());

            DateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            start.setText(f.format(event.getStartDate()));
            if(event.getEndDate() != null) //is not set
                end.setText(f.format(event.getEndDate()));
            else
            {
                TextView endLabel = (TextView)findViewById(R.id.lblEndDateFix);
                endLabel.setVisibility(View.INVISIBLE);
                end.setVisibility(View.INVISIBLE);
            }

            ToggleButton btn = (ToggleButton)findViewById(R.id.btnIsGoing);
            btn.setChecked(userEvent.isGoing);
            btn.setTag(eventId);
            btn.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

            creator.setText("Creator: " + event.getOwnerId());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}