package com.lab.eventapp;


import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lab.eventapp.Dialogs.ChooseFriendsDialog;
import com.lab.eventapp.Dialogs.ClockTimePickerDialog;
import com.lab.eventapp.Dialogs.DatePickerDialog;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.AppUser;
import models.ParseEvent;

public class AddEventActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    Date startDate;
    Date endDate;

    private ParseEvent event;


    private  TextView tbStartDate ;
    private  TextView tbEndDate;
    private  TextView tbStartTime ;
    private  TextView btAddFriends;
    private  TextView tbnSaveEvent;
    private  TextView tbTitle ;
    private  TextView tbPlace;
    private TextView tbEndTime;
    private TextView tbDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        endDate = new Date();
        startDate = new Date();

        tbStartDate = (TextView) findViewById(R.id.tbDateStart);
        tbEndDate = (TextView)findViewById(R.id.tbDateEnd);
        tbStartTime = (TextView) findViewById(R.id.tbTimeStart);
        btAddFriends = (TextView) findViewById(R.id.btnFriends);
        tbnSaveEvent = (TextView)findViewById(R.id.btnSave);
        tbTitle = (TextView)findViewById(R.id.lblEventTitle);
        tbPlace = (TextView)findViewById(R.id.tbPlace);
        tbEndTime = (TextView)findViewById(R.id.tbTimeEnd);
        tbDesc =  (TextView)findViewById(R.id.tbDesc);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm");

        tbStartDate.setText(dateFormat.format(startDate));
        tbStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dc = new DatePickerDialog(true);
                dc.show(fm, "DatePicker");
            }
        });

        tbEndDate.setText(dateFormat.format(endDate));
        tbEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dc = new DatePickerDialog(false);
                dc.show(fm, "DatePicker");
            }

        });

        tbStartTime.setText(timeFormat.format(startDate));
        tbStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClockTimePickerDialog tp = new ClockTimePickerDialog();
                tp.show(fm, "ClockPicker");
            }
        });

        tbEndTime.setText(timeFormat.format(endDate));
        //TODO: add listener

        tbStartTime.setText(timeFormat.format(endDate));
        btAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFriendsDialog fd = new ChooseFriendsDialog();

                fd.show(fm, "FriendsPicker");
            }
        });
    }

    public boolean ValidateForm()
    {
        String errorMsg = "";

        errorMsg += isEmpty("Title", tbTitle.getText().toString());
        errorMsg += isEmpty("Place", tbPlace.getText().toString());
        errorMsg += isEmpty("Start date", tbStartDate.getText().toString());
        errorMsg += isEmpty("End date", tbEndDate.getText().toString());
        errorMsg += isEmpty("Start time", tbStartTime.getText().toString());
        errorMsg += isEmpty("End time", tbEndTime.getText().toString());

        if(endDate.before(startDate))
            errorMsg += "End date can not be before start date!\n";

        if(errorMsg.length() == 0)
            return true;
        else
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(errorMsg);
            dialog.setTitle("Error!");
            dialog.setPositiveButton("OK", null);
            dialog.setCancelable(true);
            dialog.create().show();
            return  false;
        }
    }
    /**
     * Checks whether given input is empty and returns error message as string.
     * @param inputName Name of controlled text box
     * @param input input to check
     * @return proper error message
     */
    private String isEmpty(String inputName,String input)
    {
        if(input.length() == 0)
            return inputName + " can not be empty!\n";
        return "";
    }

    public void SaveEvent(View view)
    {
        if(!ValidateForm())//there were errors
        {
            Log.d("newevent", "Errors found");
            return;
        }

        final ParseEvent newEvent = new ParseEvent();
        newEvent.setStartDate(startDate);
        newEvent.setStartDate(endDate);
        newEvent.setTitle(tbTitle.getText().toString());
        newEvent.setDescription(tbDesc.getText().toString());
        newEvent.setPlace(tbPlace.getText().toString());
        newEvent.setOwner(ParseUser.getCurrentUser());

        final ProgressDialog dlg = new ProgressDialog(AddEventActivity.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Creating event.  Please wait.");
        dlg.show();

        newEvent.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                AppUser user = new AppUser(ParseUser.getCurrentUser());
                try {
                    user.AddEvent(newEvent);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                dlg.dismiss();
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
