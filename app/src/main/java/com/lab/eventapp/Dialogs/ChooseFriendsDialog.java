package com.lab.eventapp.Dialogs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lab.eventapp.AddEventActivity;
import com.lab.eventapp.ListAdapters.ChooseFriendsListAdapter;
import com.lab.eventapp.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import Data.Repositories.UsersRepository;
import models.User;


/**
 * Created by Adam on 2015-11-28.
 */
public class ChooseFriendsDialog extends DialogFragment
{
    private ArrayList<ParseUser> friends;

    private Button btnSave;
    private Button btnCancel;
    private ListView usersList;
    private Button btnAddUser;
    private EditText tbUser;

    public ChooseFriendsDialog()
    {
        friends = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_choose_friends, container);

        btnSave = (Button)v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUsers();
            }
        });

        btnCancel = (Button)v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnAddUser = (Button)v.findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserClick();
            }
        });

        tbUser = (EditText)v.findViewById(R.id.tbUsername);
        usersList = (ListView)v.findViewById(R.id.listViewFriends);
        usersList.setAdapter(new ChooseFriendsListAdapter(getContext(), friends));

        return v;
    }

    public void AddUserClick()
    {
        String username = tbUser.getText().toString();
        UsersRepository repo = new UsersRepository();

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username", username);
        try {
            ParseUser user =query.getFirst();
            if (user == null)
            {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setMessage("There is no such a user!");
                    dialog.setTitle("Error!");
                    dialog.setPositiveButton("OK", null);
                    dialog.setCancelable(true);
                    dialog.create().show();
            }
            else
            {
                friends.add(user);
                Log.d("parse", "Added");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void SaveUsers()
    {
        AddEventActivity activity = (AddEventActivity)getActivity();
        activity.addUsers(friends);
        getDialog().dismiss();
    }
}
