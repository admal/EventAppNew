package com.lab.eventapp.MainEventFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lab.eventapp.ActivityInterfaces.IRefreshable;
import com.lab.eventapp.ListAdapters.MyEventsListAdapter;
import com.lab.eventapp.R;
import com.lab.eventapp.Services.InternetConnectionService;
import com.lab.eventapp.Services.ModalService;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import com.lab.eventapp.models.AppUser;
import com.lab.eventapp.models.ParseEvent;


/**
 * Activity shows all events created by user.
 */
public class MyEventsFragment extends Fragment implements IRefreshable {

    /**
     *  List of created by user events.
     */
    private ListView eventList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MyEventsFragment.
     */
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
    public void onResume() {
        super.onResume();
        RefreshList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_events, container, false);

        eventList = (ListView)v.findViewById(R.id.listEvents);
        RefreshList();
        return v;
    }

    /**
     * Refresh and redraw list of events.
     */
    public  void RefreshList() {
        InternetConnectionService service = new InternetConnectionService(getActivity());
        if(!service.isInternetConnection())
        {
            ModalService.ShowNoConnetionError(getActivity());
            return;
        }

        AppUser user = new AppUser(ParseUser.getCurrentUser());
        List<ParseEvent> events = null;
        try {
            events = user.getCreatedEvents();
        } catch (ParseException e) {
            events = new ArrayList<>();
        }

        MyEventsListAdapter eventAdapter = new MyEventsListAdapter(getContext(), events, this);
        eventList.setAdapter(eventAdapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
