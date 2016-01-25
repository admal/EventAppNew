package com.lab.eventapp.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.eventapp.R;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Adam on 2015-11-28.
 */
public class ChooseFriendsListAdapter extends ArrayAdapter<ParseUser>
{
    private final Context context;
    private final List<ParseUser> users;

    private boolean isAdmin = false;

    public ChooseFriendsListAdapter(Context context, List<ParseUser> users, boolean isAdmin) {
        super(context, R.layout.list_adapter_choose_myfriends, users);
        this.context = context;
        this.users = users;
        this.isAdmin = isAdmin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int idx= position; //index in the table of users
        View v = convertView;
        final ParseUser user = users.get(idx);

        if (v == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_adapter_choose_myfriends, null);

            ImageButton btnRemove =(ImageButton) v.findViewById(R.id.btnRemove);
            btnRemove.setTag(idx);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    users.remove(idx);
//                    for(ParseUser userPush : users)
//                    {
//
//                    }
                    notifyDataSetChanged();
                }
            });

            if(!isAdmin)
            {
                btnRemove.setVisibility(View.INVISIBLE);
            }

        }
        if(idx < users.size())
        {
            TextView userName = (TextView)v.findViewById(R.id.tbUsername);
            userName.setText(user.getUsername());
        }
        return v;
    }
}
