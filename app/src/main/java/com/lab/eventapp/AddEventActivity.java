package com.lab.eventapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lab.eventapp.Dialogs.ChooseFriendsDialog;
import com.lab.eventapp.Dialogs.ClockTimePickerDialog;
import com.lab.eventapp.Dialogs.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Event;

public class AddEventActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    Date startDate;
    Date endDate;

    private Event event = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        final TextView startDateTb = (TextView) findViewById(R.id.tbDateStart);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        startDateTb.setText(df.format(new Date()));
        startDateTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dc = new DatePickerDialog(true);
                dc.show(fm, "DatePicker");
            }
        });

        TextView endDateTb = (TextView)findViewById(R.id.tbDateEnd);
        endDateTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dc = new DatePickerDialog(false);
                dc.show(fm, "DatePicker");
            }

        });


        TextView startTime = (TextView) findViewById(R.id.tbTimeStart);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClockTimePickerDialog tp = new ClockTimePickerDialog();
                tp.show(fm,"ClockPicker");
            }
        });

        TextView addFriends = (TextView) findViewById(R.id.btnFriends);
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFriendsDialog fd = new ChooseFriendsDialog();

                fd.show(fm,"FriendsPicker");
            }
        });

        TextView saveEvent = (TextView)findViewById(R.id.btnSave);
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event newEvent = new Event(33); //tmp

                newEvent.setStart(startDate);
                newEvent.setStart(endDate);
                TextView title = (TextView)v.findViewById(R.id.lblEventTitle);
                if(title != null) {
                    newEvent.setTitle(title.getText().toString());
                    TextView desc = (TextView) v.findViewById(R.id.lblDesc);
                    newEvent.setDescription(desc.getText().toString());
                }
                newEvent.setOwnerObject(Singleton.getInstance().getCurrentUser());
//                EventsRepository repo = new EventsRepository();
//                repo.addEvent(users);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
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

    public void getDateFromDialog(Date date, boolean start)
    {
        TextView startDate = (TextView) findViewById(R.id.tbDateStart);
        TextView endDate = (TextView) findViewById(R.id.tbDateEnd);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if(start) {
            this.startDate = date;
            startDate.setText(df.format(date));
        }
        else {
            this.endDate = date;
            endDate.setText(df.format(date));
        }

    }
    public void getTimeFromDialog(int hours, int minutes)
    {
        TextView startTime = (TextView) findViewById(R.id.tbTimeStart);
        startTime.setText(hours + " : " + minutes);
    }
}
