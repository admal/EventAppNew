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

import com.lab.eventapp.ActivityInterfaces.IUserAddable;
import com.lab.eventapp.AddEventActivity;
import com.lab.eventapp.ListAdapters.ChooseFriendsListAdapter;
import com.lab.eventapp.R;
import com.lab.eventapp.Services.ModalService;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Adam on 2015-11-28.
 * Dialog box for finding users.
 */
public class ChooseFriendsDialog extends DialogFragment
{
    /**
     * list of added users
     */
    private List<ParseUser> friends;

    private Button btnSave;
    private Button btnCancel;
    private ListView usersList;
    private Button btnAddUser;
    private EditText tbUser;
    /**
     * Indicates whether user has admin priviliges. Admin priviliges allows user to delete users from the list.
     */
    private boolean isAdmin = false;
    private ChooseFriendsListAdapter adapter;

    public ChooseFriendsDialog()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        isAdmin = getArguments().getBoolean("isAdmin");

        View v = inflater.inflate(R.layout.dialog_choose_friends, container);

        AddEventActivity activity = (AddEventActivity) getActivity();
        friends = activity.getAddedUsers();

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
        adapter = new ChooseFriendsListAdapter(getContext(), friends, isAdmin);
        adapter.notifyDataSetChanged();
        usersList.setAdapter(adapter);

        return v;
    }

    /**
     * Button click handler. Validates added user and shows proper notyfication about it.
     */
    public void AddUserClick()
    {
        String username = tbUser.getText().toString();

        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username", username);

            final ProgressDialog dlg = new ProgressDialog(getContext());
            dlg.setTitle("Please wait.");
            dlg.setCancelable(false);
            dlg.setMessage("Removing event.  Please wait.");
            dlg.show();

            //ParseUser user;
             query.getFirstInBackground(new GetCallback<ParseUser>() {
                 @Override
                 public void done(ParseUser object, ParseException e) {
                     if (e != null) {
                         ModalService.ShowErrorModal("Error occured!", getActivity());
                         dlg.dismiss();
                         return;
                     } else {
                         ParseUser user = object;
                         dlg.dismiss();
                         if (user.getUsername() == ParseUser.getCurrentUser().getUsername()) {
                             showModal("You are already added to this event!");
                             return;
                         }
                         if (friends.contains(user)) {
                             showModal("You have already added this user!");
                             return;
                         }
                         friends.add(user);
                         adapter.notifyDataSetChanged();
                     }
                 }
             });

//
//            Log.d("parse", "Added");
//
//        } catch (ParseException e) {
//            showModal("There is no such a user!");

    }

    /**
     * Show error modal.
     * @param errorMsg error message to be displayed.
     */
    public void showModal(String errorMsg)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage(errorMsg);
        dialog.setTitle("Error!");
        dialog.setPositiveButton("OK", null);
        dialog.setCancelable(true);
        dialog.create().show();
    }

    /**
     * Sends list of added user to activity that implements @class(IUserAddable) interface.
     */
    public void SaveUsers()
    {
        IUserAddable activity = (IUserAddable)getActivity();
        activity.AddUsers(friends);
        getDialog().dismiss();
    }
}
