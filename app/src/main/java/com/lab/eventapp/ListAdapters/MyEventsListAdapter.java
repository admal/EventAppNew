package com.lab.eventapp.ListAdapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lab.eventapp.AddEventActivity;
import com.lab.eventapp.ActivityInterfaces.IRefreshable;
import com.lab.eventapp.R;
import com.lab.eventapp.Services.ModalService;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

import com.lab.eventapp.models.ParseEvent;

/**
 * Created by Adam on 2015-11-24.
 * Manages showing list of created by user events. It allows to open edit form and deleting the event.
 */
public class MyEventsListAdapter extends ArrayAdapter<ParseEvent>
{
    /**
     * Current context
     */
    private final Context context;
    /**
     * List of created events.
     */
    private final List<ParseEvent> events;
    /**
     * Instance of activity that implements @class(IRefreshable) interface.
     */
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
        final ParseEvent event = events.get(idx);

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

            View btnEdit = v.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AddEventActivity.class);
                    intent.putExtra("eventId", event.getObjectId());
                    context.startActivity(intent);
                }
            });

            if(title != null)
            {
                title.setText(event.getTitle());
            }
        }
        return v;
    }

    /**
     * Delete event.
     * @param view
     */
    public void CancelEvent(final View view)
    {
        int id = (int)view.getTag();

        final ProgressDialog dlg = new ProgressDialog(getContext());
        dlg.setTitle("Please wait.");
        dlg.setMessage("Removing event.  Please wait.");
        dlg.show();
        ParseEvent ev = events.get(id);

        try {
            List<ParseUser> usersEvents = ev.getAllUsers();

            for (ParseUser u :
                    usersEvents) {
                ev.removeUser(u);
            }
            ev.deleteInBackground(new DeleteCallback() {
                @Override
                public void done(ParseException e) {
                    dlg.dismiss();
                    refreshableActivity.RefreshList();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ModalService.ShowErrorModal("Could not delete the event!", getContext());
        }

    }
}
