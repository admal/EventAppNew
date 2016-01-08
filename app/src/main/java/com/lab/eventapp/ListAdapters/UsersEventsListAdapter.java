package com.lab.eventapp.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.lab.eventapp.CustomEventListeners.MyOnCheckedChangeListener;
import com.lab.eventapp.R;
import com.lab.eventapp.UsersEventDetailsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.UsersEvents;

/**
 * Created by Adam on 2015-11-24.
 */
public class UsersEventsListAdapter extends ArrayAdapter<UsersEvents>
{
    private final Context context;
    private final ArrayList<UsersEvents> events;

    public UsersEventsListAdapter(Context context, ArrayList<UsersEvents> events) {
        super(context, R.layout.list_adapter_usersevents, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        int idx= position; //index in the table of events
        View v = convertView;
        UsersEvents event = events.get(idx);

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_adapter_usersevents, null);
        }

        if(idx < events.size())
        {
            TextView titleTb = (TextView) v.findViewById(R.id.lblEventTitle);
            Switch isGoingSwitch = (Switch) v.findViewById(R.id.switchIsGoing);
            TextView dateTb = (TextView) v.findViewById(R.id.lblDate);
            if(titleTb != null && isGoingSwitch != null && dateTb != null)
            {
                titleTb.setText(event.event.getTitle());

                Date d = event.event.getStartDate();
                DateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                dateTb.setText(f.format(d));

                isGoingSwitch.setChecked(event.isGoing);
                isGoingSwitch.setTag(event.event.getId());
                isGoingSwitch.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
            }
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UsersEventDetailsActivity.class);
                intent.putExtra("eventid",position);
                context.startActivity(intent);
            }
        });

        return v;
    }


}
