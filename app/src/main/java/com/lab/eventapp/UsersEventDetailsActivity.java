package com.lab.eventapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lab.eventapp.ActivityInterfaces.IUserAddable;
import com.lab.eventapp.Dialogs.ChooseFriendsDialog;
import com.lab.eventapp.ListAdapters.ChooseFriendsListAdapter;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.joda.time.LocalDateTime;

import java.util.List;

import Validator.StringValidator;
import models.AppUser;
import models.ParseEvent;


public class UsersEventDetailsActivity extends AppCompatActivity implements IUserAddable
{

    ParseEvent event;

    private EditText tbTitle;
    private EditText tbOrganizer;
    private EditText tbPlace;
    private EditText tbStartDate;
    private EditText tbStartTime;
    private EditText tbEndDate;
    private EditText tbEndTime;
    private EditText tbDesc;
    private Button btnAddUser;

    private ListView listUsers;

    private List<ParseUser> users;
    private boolean isAdmin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_event_details);
        Intent intent = getIntent();
        String eventId = intent.getStringExtra("eventid");

        AppUser user = new AppUser(ParseUser.getCurrentUser());
        try {
            event = user.getUserEventById(eventId);
            //isAdmin = event.getOwner().getUsername() == ParseUser.getCurrentUser().getUsername();

        } catch (Exception e) {
            e.printStackTrace();
        }

        tbTitle = (EditText)findViewById(R.id.tbTitle);
        tbOrganizer = (EditText)findViewById(R.id.tbOrganizer);
        tbPlace = (EditText)findViewById(R.id.tbPlace);
        tbStartDate = (EditText)findViewById(R.id.tbStartDate);
        tbStartTime = (EditText)findViewById(R.id.tbStartTime);
        tbEndDate = (EditText)findViewById(R.id.tbEndDate);
        tbEndTime = (EditText)findViewById(R.id.tbEndTime);
        tbDesc = (EditText)findViewById(R.id.tbDesc);


        listUsers = (ListView)findViewById(R.id.listUsers);

        try {
            users = event.getUsers();
            listUsers.setAdapter(new ChooseFriendsListAdapter(getBaseContext(), users, isAdmin));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(event != null)
        {
            tbTitle.setText(event.getTitle());
            tbPlace.setText(event.getPlace());
            tbDesc.setText(event.getDescription());

            LocalDateTime startDateTime = new LocalDateTime(event.getStartDate());
            LocalDateTime endDateTime = new LocalDateTime(event.getEndDate());

            tbStartDate.setText(startDateTime.toString("dd/MM/yyyy"));
            tbStartTime.setText(startDateTime.toString("HH:mm"));

            tbEndDate.setText(endDateTime.toString("dd/MM/yyyy"));
            tbEndTime.setText(endDateTime.toString("HH:mm"));

            try {
                tbOrganizer.setText(event.getOwner().getUsername());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tbPlace.setText(event.getPlace());

            tbTitle.setEnabled(isAdmin);
            tbPlace.setEnabled(isAdmin);
            tbStartDate.setEnabled(isAdmin);
            tbStartTime.setEnabled(isAdmin);
            tbEndDate.setEnabled(isAdmin);
            tbEndTime.setEnabled(isAdmin);
            tbDesc.setEnabled(isAdmin);
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

    @Override
    public void AddUsers(List<ParseUser> users) {
        this.users = users;
    }
}
