package com.lab.eventapp.MainEventFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lab.eventapp.ListAdapters.MyEventsListAdapter;
import com.lab.eventapp.R;
import com.lab.eventapp.Singleton;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import models.AppUser;
import models.Event;
import models.ParseEvent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView eventList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MyEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyEventsFragment newInstance() {
        MyEventsFragment fragment = new MyEventsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MyEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_events, container, false);

        eventList = (ListView)v.findViewById(R.id.listEvents);

        //ArrayList<Event> events = Singleton.getInstance().getCurrentUser().createdEvents;
        AppUser user = new AppUser(ParseUser.getCurrentUser());
        List<ParseEvent> events = null;
        try {
            events = user.getCreatedEvents();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(events == null) {
            TextView noEventsTextBox = new TextView(getContext());
            noEventsTextBox.setText( "You have not any upcoming events! Add friends to keep in touch with them. ;)");
        }
        else {
            MyEventsListAdapter eventAdapter = new MyEventsListAdapter(getContext(), events);
            eventList.setAdapter(eventAdapter);
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI users
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
