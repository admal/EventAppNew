package com.lab.eventapp.ListAdapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lab.eventapp.MainEventFragments.IRefreshable;
import com.lab.eventapp.MainEventFragments.MyEventsFragment;
import com.lab.eventapp.R;
import com.parse.DeleteCallback;
import com.parse.ParseException;

import java.util.List;

import models.ParseEvent;

/**
 * Created by Adam on 2015-11-24.
 */
public class MyEventsListAdapter extends ArrayAdapter<ParseEvent>
{

    private final Context context;
    private final List<ParseEvent> events;
    private IRefreshable refreshableActivity;

    public MyEventsListAdapter(Context context, List<ParseEvent> events, IRefreshable refreshableActivity) {
        super(context, R.layout.list_adapter_event, events);
        this.context = context;
        this.events = events;
        this.refreshableActivity = refreshableActivity;
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
            View btnCancel = v.findViewById(R.id.btnCancel);
            btnCancel.setTag(idx);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    dialog.dismiss();
                                    CancelEvent(v);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

            if(title != null)
            {
                title.setText(event.getTitle());
            }
        }
        return v;
    }
    public void CancelEvent(final View view)
    {
        int id = (int)view.getTag();

        final ProgressDialog dlg = new ProgressDialog(getContext());
        dlg.setTitle("Please wait.");
        dlg.setMessage("Removing event.  Please wait.");
        dlg.show();

        events.get(id).deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                dlg.dismiss();
                refreshableActivity.RefreshList();
            }
        });
    }
    
}
