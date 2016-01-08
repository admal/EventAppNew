package com.lab.eventapp;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import CustomTasks.SendHttpRequestTask;
import Parsers.JsonParser;
import http.DbHandler;
import models.Event;
import models.User;

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


        //tmp staff

        String json = "";
        try {
            json = DbHandler.getUserData(2);

            Log.d("output", json);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


       // new SendHttpRequestTask().execute(url, "GET");
       // String json = " {\"error\":false,\"user\":[{\"id\":2,\"username\":\"test\",\"name\":\"test3\",\"surname\":\"test4\",\"email\":\"aqq2@gmail.com\"}]}";
        JsonParser parser = new JsonParser();
        User user = parser.parseUser(json);
        Log.d("User", user.getUsername());
        String json2 = "{\"error\":false,\"events\":[{\"id\":5,\"owner\":1,\"conversation\":1,\"title\":\"asda\",\"description\":\"adadad\",\"start\":\"2015-01-12\",\"end\":\"2015-02-23\"},{\"id\":6,\"owner\":1,\"conversation\":1,\"title\":\"sad\",\"description\":\"qwe\",\"start\":\"2015-01-12\",\"end\":\"2015-02-23\"}]}";
        ArrayList<Event> ev = parser.parseEvents(json2);
        if(ev != null) {
            for (Event e :
                    ev) {
                Log.d("events:", e.getTitle());
            }
        }
        Log.e("json-error", "Error null eventlist");
        ///

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
