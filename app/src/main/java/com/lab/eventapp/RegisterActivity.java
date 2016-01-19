package com.lab.eventapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        tbUsername = (EditText) findViewById(R.id.tbLogin);
        tbEmail = (EditText) findViewById(R.id.tbEmail);
        tbPasswd = (EditText) findViewById(R.id.tbPassword);
        tbRepeatPasswd = (EditText) findViewById(R.id.tbRepeatPasswd);
        lblErrors = (TextView)findViewById(R.id.lblErrors);
    }

    private EditText tbUsername;
    private EditText tbEmail;
    private EditText tbPasswd;
    private EditText tbRepeatPasswd;
    private TextView lblErrors;


    public void RegisterUser(View view)
    {
        if(ValidateInput())
        {
            // Set up a progress dialog
            final ProgressDialog dlg = new ProgressDialog(RegisterActivity.this);
            dlg.setTitle("Please wait.");
            dlg.setMessage("Signing up.  Please wait.");
            dlg.show();

            // Set up a new Parse user
            ParseUser user = new ParseUser();
            user.setEmail(tbEmail.getText().toString());
            user.setUsername(tbUsername.getText().toString());
            user.setPassword(tbPasswd.getText().toString());
            // Call the Parse signup method
            user.signUpInBackground(new SignUpCallback() {

                @Override
                public void done(ParseException e) {
                    dlg.dismiss();
                    if (e != null) {
                        // Show the error message
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        // Start an intent for the dispatch activity
                        Intent intent = new Intent(RegisterActivity.this, DispatchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private boolean ValidateInput()
    {
        boolean isCorrect = true;
        String errorMsg = "";

        String username = tbUsername.getText().toString();
        String email = tbEmail.getText().toString();
        String passwd = tbPasswd.getText().toString();
        String repeatPasswd = tbRepeatPasswd.getText().toString();

        if(username.length() == 0)
        {
            isCorrect = false;
            errorMsg += "Username can not be empty! \n";
        }

        //validating email
        Pattern pattern = Pattern.compile(".+\\@.+\\..+");
        Matcher matcher = pattern.matcher(email);

        if(email.length() ==0 ||  !matcher.find() )
        {
            isCorrect = false;
            errorMsg += "Provide proper email address! \n";
        }

        //validating password
        if(passwd.length()==0)
        {
            isCorrect = false;
            errorMsg += "Provide password! \n";
        }
        if(passwd.length() < 6)
        {
            isCorrect = false;
            errorMsg += "Password needs to be at list 6 characheters long! \n";
        }
        //validating repeatPassword
        if(repeatPasswd.length() == 0)
        {
            isCorrect = false;
            errorMsg += "Repeat password! \n";
        }
        if(!repeatPasswd.equals(passwd))
        {
            isCorrect = false;
            errorMsg += "Passwords are not the same! \n";
        }
        Log.d("register", email);
        lblErrors.setText(errorMsg);
        return isCorrect;
    }
}
