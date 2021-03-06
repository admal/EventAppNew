package com.lab.eventapp;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lab.eventapp.ActivityInterfaces.IUserAddable;
import com.lab.eventapp.Dialogs.ChooseFriendsDialog;
import com.lab.eventapp.Dialogs.ClockTimePickerDialog;
import com.lab.eventapp.Dialogs.DatePickerDialog;
import com.lab.eventapp.Factories.ParseEventFactory;
import com.lab.eventapp.Services.InternetConnectionService;
import com.lab.eventapp.Services.ModalService;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import com.lab.eventapp.Validator.StringValidator;
import com.lab.eventapp.models.AppUser;
import com.lab.eventapp.models.ParseEvent;
import com.lab.eventapp.models.ParseUsersEvent;

public class AddEventActivity extends AppCompatActivity implements IUserAddable
{
    /**
     * Loaded event to edit. If it is null then it means that new event is created.
     */
    ParseEvent loadedEvent = null;
    /**
     * Manger to create and show dialog boxes.
     */
    FragmentManager fm = getSupportFragmentManager();
    /**
     * Start date of the event.
     */
    LocalDate startDate;
    /**
     * End date of the event.
     */
    LocalDate endDate;
    /**
     * Start time of the event.
     */
    LocalTime startTime;
    /**
     * End time of the event.
     */
    LocalTime endTime;

    /**
     * List of added users to the event.
     */
    private List<ParseUser> addedUsers;

    //views
    private  TextView tbStartDate ;
    private  TextView tbEndDate;
    private  TextView tbStartTime ;
    private  TextView btnAddFriends;
    private  TextView btnSaveEvent;
    private  TextView tbTitle ;
    private  TextView tbPlace;
    private TextView tbEndTime;
    private TextView tbDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        InternetConnectionService service = new InternetConnectionService(AddEventActivity.this);
        if(!service.isInternetConnection())
        {
            ModalService.ShowNoConnetionError(AddEventActivity.this);
            return;
        }

        tbStartDate = (TextView) findViewById(R.id.tbStartDate);
        tbEndDate = (TextView)findViewById(R.id.tbEndDate);
        tbStartTime = (TextView) findViewById(R.id.tbStartTime);
        tbEndTime = (TextView)findViewById(R.id.tbEndTime);
        btnAddFriends = (TextView) findViewById(R.id.btnFriends);
        btnSaveEvent = (TextView)findViewById(R.id.btnSave);
        tbTitle = (TextView)findViewById(R.id.tbEventTitle);
        tbPlace = (TextView)findViewById(R.id.tbPlace);
        tbDesc =  (TextView)findViewById(R.id.tbDesc);

        addedUsers = new ArrayList<>();
        endDate = new LocalDate();
        startDate = new LocalDate() ;
        startTime = new LocalTime();
        endTime =  new LocalTime();

        String eventId = getIntent().getStringExtra("eventId");
        if(eventId != null)
        {
            final ProgressDialog dlg = new ProgressDialog(AddEventActivity.this);
            dlg.setTitle("Please wait.");
            dlg.setMessage("Searching event.  Please wait.");
            dlg.show();

            ParseQuery<ParseEvent> query = ParseQuery.getQuery("Event");
            query.getInBackground(eventId, new GetCallback<ParseEvent>() {
                @Override
                public void done(ParseEvent object, ParseException e) {
                    if(e==null) {
                        loadedEvent = object;

                        tbTitle.setText(loadedEvent.getTitle());
                        tbPlace.setText(loadedEvent.getPlace());
                        tbDesc.setText(loadedEvent.getDescription());

                        endDate = loadedEvent.getEndDate().toLocalDate();
                        startDate = loadedEvent.getStartDate().toLocalDate();
                        startTime = loadedEvent.getEndDate().toLocalTime();
                        endTime =  loadedEvent.getEndDate().toLocalTime();

                        btnSaveEvent.setText("SAVE EVENT");

                        try {
                            addedUsers = loadedEvent.getUsers();
                        } catch (ParseException e1) {
                            ModalService.ShowErrorModal("Error occured while proceding users!", AddEventActivity.this);
                            finish();
                        }
                        dlg.dismiss();
                    }
                    else
                    {
                        dlg.dismiss();
                        ModalService.ShowErrorModal("There is no such event!", AddEventActivity.this);
                        finish();
                    }
                }
            });
        }

        tbStartDate.setText(startDate.toString("dd/MM/yyyy"));
        tbEndDate.setText(endDate.toString("dd/MM/yyyy"));
        tbStartTime.setText(startTime.toString("HH:mm"));
        tbEndTime.setText(endTime.toString("HH:mm"));

        setOnClickListeners();
    }

    /**
     * Sets onClickListeners for each clickable view in the activity.
     */
    private void setOnClickListeners()
    {
        tbStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dc = new DatePickerDialog();
                Bundle args = new Bundle();
                args.putBoolean("start", true);
                dc.setArguments(args);
                dc.show(fm, "DatePicker");
            }
        });

        tbEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dc = new DatePickerDialog();
                Bundle args = new Bundle();
                args.putBoolean("start", false);
                dc.setArguments(args);
                dc.show(fm, "DatePicker");
            }

        });

        tbStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClockTimePickerDialog tp = new ClockTimePickerDialog();
                Bundle args = new Bundle();
                args.putBoolean("start", true);
                tp.setArguments(args);
                tp.show(fm, "ClockPicker");
            }
        });

        tbEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClockTimePickerDialog tp = new ClockTimePickerDialog();
                Bundle args = new Bundle();
                args.putBoolean("start", false);
                tp.setArguments(args);
                tp.show(fm, "ClockPicker");
            }
        });

        btnAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFriendsDialog fd = new ChooseFriendsDialog();
                Bundle args = new Bundle();
                args.putBoolean("isAdmin", true);
                fd.setArguments(args);
                fd.show(fm, "FriendsPicker");
            }
        });

        btnSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveEventClick(v);
            }
        });
    }

    public List<ParseUser> getAddedUsers()
    {
        return addedUsers;
    }

    /**
     * Validate input. Only description can be empty. Start date can not be after end date.
     * Method shows proper error message.
     * @return true if input is proper, false otherwise
     */
    public boolean ValidateForm()
    {
        String errorMsg = "";
        StringValidator validator = new StringValidator();
        errorMsg += validator.isEmpty("Title", tbTitle.getText().toString());
        errorMsg += validator.isEmpty("Place", tbPlace.getText().toString());
        errorMsg += validator.isEmpty("Start date", tbStartDate.getText().toString());
        errorMsg += validator.isEmpty("End date", tbEndDate.getText().toString());
        errorMsg += validator.isEmpty("Start time", tbStartTime.getText().toString());
        errorMsg += validator.isEmpty("End time", tbEndTime.getText().toString());

        LocalDateTime startFinalDate = startDate.toLocalDateTime(startTime);
        LocalDateTime endFinalDate = endDate.toLocalDateTime(endTime);

        if(startFinalDate.isAfter(endFinalDate))
            errorMsg += "End date can not be before start date!\n";

        if(errorMsg.length() == 0)
            return true;
        else
        {
            ModalService.ShowErrorModal(errorMsg, AddEventActivity.this);
            return  false;
        }
    }

    /**
     * Button click event handler. Whether New event is created or event is edited it starts proper
     * method.
     * @param view
     */
    public void SaveEventClick(View view)
    {
        if(!ValidateForm())//there were errors
        {
            ModalService.ShowErrorModal("Unknown error occured!", AddEventActivity.this);
            return;
        }
        if(loadedEvent != null) //editing event
        {
            EditEvent();
        }
        else //creating new one
        {
            CreateEvent();
        }
    }

    /**
     * Edits loaded event and saves changes.
     */
    private void EditEvent() {
        LocalDateTime startFinalDate = startDate.toLocalDateTime(startTime);
        LocalDateTime endFinalDate = endDate.toLocalDateTime(endTime);

        loadedEvent.EditEvent(tbTitle.getText().toString(),tbDesc.getText().toString(),startFinalDate,endFinalDate,
                tbPlace.getText().toString());

        final ProgressDialog dlg = new ProgressDialog(AddEventActivity.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Creating event.  Please wait.");
        dlg.show();

        loadedEvent.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                AppUser user = new AppUser(ParseUser.getCurrentUser());
                try {
                    List<ParseUser> oldUsers = loadedEvent.getUsers();
                    //to every user the event is added
                    for (ParseUser friend : addedUsers) {
                        if(!oldUsers.contains(friend))
                        {
                            loadedEvent.AddUser(friend);

                            ParsePush push = new ParsePush();
                            push.setChannel(friend.getUsername());
                            push.setMessage("You were added to event: " + tbTitle.getText().toString());
                            push.sendInBackground();
                        }
                    }
                    for (ParseUser friend : oldUsers) {
                        if (!addedUsers.contains(friend)) {
                            loadedEvent.removeUser(friend);
                        }
                    }

                } catch (ParseException e1) {
                    ModalService.ShowErrorModal("Could not edit event!", AddEventActivity.this);
                    e1.printStackTrace();
                }
                dlg.dismiss();
                finish();
            }
        });
    }

    /**
     * Gets data from the form and creates the event.
     */
    public void CreateEvent()
    {
        LocalDateTime startFinalDate = startDate.toLocalDateTime(startTime);
        LocalDateTime endFinalDate = endDate.toLocalDateTime(endTime);

        final ParseEvent newEvent = ParseEventFactory.Create(tbTitle.getText().toString(), tbDesc.getText().toString(),
                startFinalDate, endFinalDate, tbPlace.getText().toString());

        final ProgressDialog dlg = new ProgressDialog(AddEventActivity.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Creating event.  Please wait.");
        dlg.show();

        newEvent.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null)//there was error
                {
                    dlg.dismiss();
                    ModalService.ShowErrorModal("Could not create event!", AddEventActivity.this);
                    return;
                }
                try {
                    for (ParseUser friend :
                            addedUsers) {
                        newEvent.AddUser(friend);

                        ParsePush push = new ParsePush();
                        push.setChannel(friend.getUsername());
                        push.setMessage("You were added to event: " + tbTitle.getText().toString());
                        push.sendInBackground();
                    }
                    newEvent.AddUser(ParseUser.getCurrentUser());
                    dlg.dismiss();
                    finish();

                } catch (Exception e1) {
                    dlg.dismiss();
                    e1.printStackTrace();
                    ModalService.ShowErrorModal("Could not create event!", AddEventActivity.this);
                }
            }
        });
    }

    /**
     * Gets data from date picker and assigns it to proper date.
     * @param date date to be assigned
     * @param start indicates which date we edits
     */
    public void getDateFromDialog(LocalDate date, boolean start)
    {
        if(start) {
            this.startDate = new LocalDate(date);
            tbStartDate.setText(date.toString("dd/MM/yyyy"));
        }
        else {
            this.endDate = new LocalDate(date);
            tbEndDate.setText(date.toString("dd/MM/yyyy"));
        }

    }
    /**
     * Gets data from time picker and assigns it to proper time.
     * @param time time to be assigned
     * @param start indicates which time we edits
     */
    public void getTimeFromDialog(LocalTime time, boolean start)
    {
        //TextView startTime = (TextView) findViewById(R.id.tbTimeStart);
        if (start) {
            startTime = time;
            tbStartTime.setText(time.toString("HH:mm"));
        }
        else {
            endTime = time;
            tbEndTime.setText(time.toString("HH:mm"));
        }
    }

    /**
     * Adds chosen users to the list.
     * @param users
     */
    @Override
    public void AddUsers(List<ParseUser> users)
    {
        addedUsers = users;
    }
}
