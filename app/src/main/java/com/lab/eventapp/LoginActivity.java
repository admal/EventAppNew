package com.lab.eventapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.eventapp.Services.InternetConnectionService;
import com.lab.eventapp.Services.ModalService;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    public void goToRegisterView(View view)
    {

        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
    }

    // UI references.
    private TextView tbUsername;
    private TextView tbPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InternetConnectionService service = new InternetConnectionService(LoginActivity.this);
        if(!service.isInternetConnection())
        {
            ModalService.ShowNoConnetionError(LoginActivity.this);
            return;
        }


        tbUsername = (TextView) findViewById(R.id.tbLogin);
        tbPasswd = (TextView) findViewById(R.id.tbPassword);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();
                // Call the Parse login method
                ParseUser.logInInBackground(tbUsername.getText().toString(), tbPasswd.getText().toString(), new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}

