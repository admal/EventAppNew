package com.lab.eventapp.ListAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.lab.eventapp.R;
import com.lab.eventapp.UsersEventDetailsActivity;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.joda.time.LocalDateTime;

import java.util.List;

import models.ParseEvent;

/**
 * Created by Adam on 2015-11-24.
 */
public class UsersEventsListAdapter extends ArrayAdapter<ParseEvent>
{
    private final Context context;
    private final List<ParseEvent> events;

    public UsersEventsListAdapter(Context context, List<ParseEvent> events) {
        super(context, R.layout.list_adapter_usersevents, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        int idx= position; //index in the table of events
        View v = convertView;
        final ParseEvent event = events.get(idx);

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_adapter_usersevents, null);
        }

        if(idx < events.size())
        {
            TextView titleTb = (TextView) v.findViewById(R.id.lblEventTitle);
            final Switch isGoingSwitch = (Switch) v.findViewById(R.id.switchIsGoing);
            TextView dateTb = (TextView) v.findViewById(R.id.lblDate);
            if(titleTb != null && isGoingSwitch != null && dateTb != null)
            {
                if(event.getEndDate().isBefore(new LocalDateTime()))
                {

                }
                titleTb.setText(event.getTitle());

                LocalDateTime d = event.getStartDate();
                dateTb.setText(d.toString("dd/MM/yyyy HH:mm"));

                try {
                    if (event.getOwner() == ParseUser.getCurrentUser())
                    {
                        isGoingSwitch.setEnabled(false);
                    }
                    isGoingSwitch.setChecked(true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                isGoingSwitch.setTag(event.getObjectId());
                isGoingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        dialog.dismiss();
                                        try {
                                            event.removeUser(ParseUser.getCurrentUser());
                                            events.remove(event);
                                            notifyDataSetChanged();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        isGoingSwitch.setEnabled(true);
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to leave that event?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                });
            }
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UsersEventDetailsActivity.class);
                intent.putExtra("eventid",event.getObjectId());
                context.startActivity(intent);
            }
        });

        return v;
    }


}
