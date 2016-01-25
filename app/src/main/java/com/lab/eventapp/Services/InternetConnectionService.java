package com.lab.eventapp.Services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Adam on 2016-01-25.
 */
public class InternetConnectionService {
    private Activity currActivity;

    public InternetConnectionService(Activity currActivity) {
        this.currActivity = currActivity;
    }

    public boolean isInternetConnection()
    {
        ConnectivityManager cm = (ConnectivityManager) currActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
