package com.lab.eventapp.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.lab.eventapp.R;

import java.util.ArrayList;

import models.User;

/**
 * Created by Adam on 2015-11-28.
 */
public class ChooseFriendsListAdapter extends ArrayAdapter<User>
{
    private final Context context;
    private final ArrayList<User> users;


    public ChooseFriendsListAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.list_adapter_choose_myfriends, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int idx= position; //index in the table of users
        View v = convertView;
        User user = users.get(idx);

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_adapter_choose_myfriends, null);
        }
        if(idx < users.size())
        {
            CheckedTextView userName = (CheckedTextView)v.findViewById(R.id.tbUserName);
            userName.setText(user.getUsername());
            userName.setChecked(false);

        }
        return v;
    }
}
