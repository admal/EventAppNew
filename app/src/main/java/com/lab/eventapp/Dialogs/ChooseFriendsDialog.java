package com.lab.eventapp.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.lab.eventapp.ListAdapters.ChooseFriendsListAdapter;
import com.lab.eventapp.R;

import java.util.ArrayList;
import java.util.ListResourceBundle;

import models.User;


/**
 * Created by Adam on 2015-11-28.
 */
public class ChooseFriendsDialog extends DialogFragment
{
    private ArrayList<User> friends;
    public ChooseFriendsDialog()
    {
        friends = new ArrayList<>();
        friends.add(new User(100,"my firend!"));
        friends.add(new User(101,"my other friend!"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_choose_friends, container);
        Button btnSave = (Button)v.findViewById(R.id.btnSave);
        Button btnCancel = (Button)v.findViewById(R.id.btnCancel);

        ListView list = (ListView)v.findViewById(R.id.listViewFriends);
        list.setAdapter(new ChooseFriendsListAdapter(getContext(),friends));

        return v;
    }

}
