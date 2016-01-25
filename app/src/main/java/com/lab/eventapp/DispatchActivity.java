package com.lab.eventapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.lab.eventapp.Services.InternetConnectionService;
import com.lab.eventapp.Services.ModalService;
import com.parse.ParseUser;

/**
 * Class checking if user is logged in and managing activities accordingly
 */
public class DispatchActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        InternetConnectionService service = new InternetConnectionService(DispatchActivity.this);
//        service.CheckInternetConnection();
        //I don't know why InternetConnectionService can't be used

        // Check if there is internet connection: If there isn't show pop up dialog and close application
        ConnectivityManager cm = (ConnectivityManager) DispatchActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            AlertDialog.Builder builder1=new AlertDialog.Builder(DispatchActivity.this);

            builder1.setMessage("There is no internet connection. Please fix the problem and reload application.");
            builder1.setNeutralButton("OK",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });

            builder1.show();
            ModalService.ShowNoConnetionError(DispatchActivity.this);
        }
        else //If there is manage activities
        {
            // Check if there is current user info
            if (ParseUser.getCurrentUser() != null) {
                // Start an intent for the logged in activity
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // Start and intent for the logged out activity
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }
}
