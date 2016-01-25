package com.lab.eventapp.MainEventFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.eventapp.ListAdapters.UsersEventsListAdapter;
import com.lab.eventapp.R;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

import models.AppUser;
import models.ParseEvent;


/**
 * Activity shows all events that user is invited to.
 */
public class EventsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * List of events that user is invited to.
     */
    private ListView eventList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventsFragment.
     */
    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshEvents();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_events,container, false);

        eventList = (ListView)v.findViewById(R.id.listEvents);
        RefreshEvents();
        return v;
    }


    /**
     * Refresh whole list of events in the fragment.
     */
    public void RefreshEvents() {
        AppUser currUser = new AppUser(ParseUser.getCurrentUser());
        List<ParseEvent> events = null;
        try {
            events = currUser.getUsersEvents();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(events == null) {
            TextView noEventsTextBox = new TextView(getContext());
            noEventsTextBox.setText( "You have not any upcoming events! Add friends to keep in touch with them. ;)");
        }
        else {
            UsersEventsListAdapter eventAdapter = new UsersEventsListAdapter(getContext(), events);
            eventList.setAdapter(eventAdapter);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
