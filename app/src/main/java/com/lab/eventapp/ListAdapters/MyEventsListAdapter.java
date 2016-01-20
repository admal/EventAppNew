package com.lab.eventapp.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lab.eventapp.Parse;
import com.lab.eventapp.R;

import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.ParseEvent;

/**
 * Created by Adam on 2015-11-24.
 */
public class MyEventsListAdapter extends ArrayAdapter<ParseEvent>
{

    private final Context context;
    private final List<ParseEvent> events;

    public MyEventsListAdapter(Context context, List<ParseEvent> events) {
        super(context, R.layout.list_adapter_event, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int idx= position; //index in the table of events
        View v = convertView;
        ParseEvent event = events.get(idx);

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_adapter_event, null);
        }

        if(idx < events.size())
        {
            TextView title = (TextView)v.findViewById(R.id.lblEventTitle);

            if(title != null)
            {
                title.setText(event.getTitle());
            }
        }
        return v;
    }
    
    
    
}
