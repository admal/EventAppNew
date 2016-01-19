package com.lab.eventapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lab.eventapp.MainEventFragments.EventsFragment;
import com.lab.eventapp.MainEventFragments.MyEventsFragment;
import com.lab.eventapp.MainEventFragments.NotyficationsFragment;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import models.ParseEvent;

public class MainActivity extends AppCompatActivity {

////temporary staff
//
//    // URL to get contacts JSON
//    private static String url = "http://api.androidhive.info/contacts/";
//
//    // JSON Node names
//    private static final String TAG_CONTACTS = "contacts";
//    private static final String TAG_ID = "id";
//    private static final String TAG_NAME = "name";
//    private static final String TAG_EMAIL = "email";
//    private static final String TAG_ADDRESS = "address";
//    private static final String TAG_GENDER = "gender";
//    private static final String TAG_PHONE = "phone";
//    private static final String TAG_PHONE_MOBILE = "mobile";
//    private static final String TAG_PHONE_HOME = "home";
//    private static final String TAG_PHONE_OFFICE = "office";
//    /////////


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });



        //TEMPORARY SECTION (just template for further coding)
        ParseUser currUser = ParseUser.getCurrentUser();
        Log.d("parse", "Inherited: " + currUser.getUsername() + "Email: " + currUser.getEmail());

        ParseQuery<ParseEvent> q = ParseQuery.getQuery("Event");
        q.getInBackground("8o6TsyjT47", new GetCallback<ParseEvent>() {
            @Override
            public void done(ParseEvent object, ParseException e) {
                if (e == null) {
                    try {
                        ParseObject user = object.getOwner();
                        Log.d("parse","Event title: " + object.getTitle());
                        Log.d("parse", "owner: " + user.getString("username"));
                    } catch (ParseException e1) {
                        Log.d("parse",e.getMessage());
                    }
                }
                else
                    Log.d("parse", "again nothing :/");

            }
        });
//
//        ParseObject event = new ParseObject("Event");
//        event.put("title", "From android");
//        event.put("endDate", new Date());
//        event.put("startDate", new Date());
//        event.put("owner", currUser);
//        try {
//            event.save();
//        } catch (ParseException e) {
//            Log.d("parse",e.getMessage());
//        }
        //END
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment f1 = EventsFragment.newInstance();
            MyEventsFragment f2 = MyEventsFragment.newInstance();
            NotyficationsFragment f3 = NotyficationsFragment.newInstance();
            switch (position) {
                case 0:
                    return f1;
                case 1:
                    return f2;
                case 2:
                    return f3;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            EventsFragment f1 = new EventsFragment();
//            MyEventsFragment f2 = new MyEventsFragment();
//            NotyficationsFragment f3 = new NotyficationsFragment();
//            switch (position) {
//                case 0:
//                    return "SECTION 1";
//                case 1:
//                    return "SECTION 2";
//                case 2:
//                    return "SECTION 3";
//            }
//            return null;
//        }
    }

}
