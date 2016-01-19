package com.lab.eventapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tbUsername = (EditText) findViewById(R.id.tbUsername);
        tbEmail = (EditText) findViewById(R.id.tbEmail);
        tbPasswd = (EditText) findViewById(R.id.tbPasswd);
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
        boolean isValid = ValidateInput();
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
