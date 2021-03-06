package com.lab.eventapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lab.eventapp.Services.InternetConnectionService;
import com.lab.eventapp.Services.ModalService;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lab.eventapp.models.ParseEvent;
import com.lab.eventapp.models.ParseMessage;
import com.lab.eventapp.models.ParseUsersEvent;

/**
 * Activity showing and managing chat for given event
 */
public class MessagesActivity extends AppCompatActivity {

    private Timer autoUpdate;
    final ArrayList<String> messages = new ArrayList<String>();

    /**
     * In onCreate program bootstrap the view and download previous messages from database
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        InternetConnectionService service = new InternetConnectionService(MessagesActivity.this);
        if(!service.isInternetConnection())
        {
            ModalService.ShowNoConnetionError(MessagesActivity.this);
            return;
        }

        Intent intent = getIntent();
        final String eventId = intent.getStringExtra("eventId");
        Log.d("eventId", eventId);
        ListView messagesContainer = (ListView) findViewById(R.id.listMessages);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MessagesActivity.this, android.R.layout.simple_list_item_1, messages);

        messagesContainer.setAdapter(adapter);

        final EditText input = (EditText) findViewById(R.id.lblMessage);
        final ParseUser currUser = ParseUser.getCurrentUser();

        final ParseEvent[] event = {new ParseEvent()};

        final ProgressDialog dlg = new ProgressDialog(MessagesActivity.this);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Loading Messages");
        dlg.show();
        ParseQuery<ParseEvent> findEvent = ParseQuery.getQuery("Event");
        findEvent.whereEqualTo("objectId", eventId);

        try {
            event[0] = findEvent.getFirst();
            Log.d("Event", "Event Description: " + event[0].getDescription());
            dlg.dismiss();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * Button for sending messages.
         */
        Button send = (Button) findViewById(R.id.btnSendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetConnectionService service2 = new InternetConnectionService(MessagesActivity.this);
                if(!service2.isInternetConnection())
                {
                    ModalService.ShowNoConnetionError(MessagesActivity.this);
                    return;
                } else {
                    final String content = input.getText().toString();
                    if(content.indexOf(":") < 0)
                    {
                        if(!content.isEmpty() && content.trim().length() > 0)
                        {
                            final ParseMessage message = new ParseMessage();
                            message.setSender(currUser);

                            ParseQuery<ParseEvent> q = ParseQuery.getQuery("Event");
                            q.getInBackground(eventId, new GetCallback<ParseEvent>() {
                                @Override
                                public void done(ParseEvent object, ParseException e) {
                                    if (e == null) {
                                        try {
                                            ParseObject user = object.getOwner();

                                            message.setEvent(object);
                                            message.setContent(content);
                                            message.saveInBackground();

                                            Log.d("parse", "Event title: " + object.getTitle());
                                            Log.d("parse", "owner: " + user.getString("username"));
                                        } catch (ParseException e1) {
                                            Log.d("parse", e.getMessage());
                                        }
                                    } else
                                        Log.d("Event", "Error: " + e.getMessage());
                                }
                            });

                            adapter.add("You: " + content);

                            input.setText("");
                            scrollMyListViewToBottom(adapter);

                            ParseQuery<ParseUsersEvent> query = ParseQuery.getQuery("UsersEvent");
                            query.whereEqualTo("event", event[0]);
                            query.findInBackground(new FindCallback<ParseUsersEvent>() {
                                public void done(List<ParseUsersEvent> usersevent, ParseException e) {
                                    if (e == null) {
                                        for (ParseUsersEvent usrevent : usersevent) {
                                            try {
                                                if(usrevent.getUser().getUsername() != currUser.getUsername())
                                                {
                                                    ParsePush push = new ParsePush();
                                                    push.setChannel(usrevent.getUser().getUsername());
                                                    push.setMessage(currUser.getUsername() + " send: " + content);
                                                    push.sendInBackground();
                                                }
                                            } catch (ParseException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    } else {
                                        Log.d("Messages on refresh", "Error: " + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MessagesActivity.this, "Right something in message and rember to not use only blankspaces", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(MessagesActivity.this, "Please don't use ':' character in message", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        /**
         * Timer for updating messages
         */
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                        refreshMessages(event, adapter, currUser, messages);
                    }
                });
            }
        }, 0, 2000);
    }


    /**
     * Download previous messages or download and update messages sent by other users when user look at the chat
     */
    private void refreshMessages(ParseEvent[] evnt, ArrayAdapter<String> adapt, ParseUser usr, ArrayList<String> msgs)
    {
        final ParseEvent[] event = evnt;
        final ArrayAdapter<String> adapter = adapt;
        final ParseUser currUser = usr;
        final Date[] lastUpdated = {null};

        InternetConnectionService service = new InternetConnectionService(MessagesActivity.this);
        if(!service.isInternetConnection())
        {
            ModalService.ShowNoConnetionError(MessagesActivity.this);
            return;
        }

        if(messages.size()-1 > -1)
        {
            String lastMessage = messages.get(messages.size()-1);
            String delims = ": ";
            String[] tokens = lastMessage.split(delims);
            String lastMessageContent = tokens[1];

            ParseQuery<ParseMessage> query = ParseQuery.getQuery("Message");
            query.whereEqualTo("content", lastMessageContent);
            query.findInBackground(new FindCallback<ParseMessage>() {
                public void done(List<ParseMessage> messages, ParseException e) {
                    if (e == null) {
                        for (ParseMessage message : messages) {
                            Date dateInDatabase = message.getCreatedAt();
                            if (lastUpdated[0] != null) {
                                if (dateInDatabase.after(lastUpdated[0])) {
                                    lastUpdated[0] = dateInDatabase;
                                }
                            } else {
                                lastUpdated[0] = dateInDatabase;
                            }
                        }
                        if(lastUpdated[0] != null)
                        {
                            Log.d("Last date", lastUpdated[0].toString());
                            ParseQuery<ParseMessage> updateMesssagesQuery = ParseQuery.getQuery("Message");
                            updateMesssagesQuery.whereGreaterThan("createdAt", lastUpdated[0]);
                            updateMesssagesQuery.findInBackground(new FindCallback<ParseMessage>() {
                                public void done(List<ParseMessage> messages, ParseException e) {
                                    if (e == null) {
                                        Log.d("Message on refresh", "Retrieved " + messages.size() + " scores");
                                        for (ParseMessage message : messages) {
                                            try {
                                                if (message.getSenderUsername() == currUser.getUsername()) {
                                                    adapter.add("You: " + message.getContent());
                                                } else {
                                                    adapter.add(message.getSenderUsername() + ": " + message.getContent());
                                                }
                                                scrollMyListViewToBottom(adapter);
                                            } catch (ParseException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    } else {
                                        Log.d("Messages on updt", "Error: " + e.getMessage());
                                    }
                                }
                            });
                        }
                    } else {
                        Log.d("Messages on update", "Error: " + e.getMessage());
                    }
                }
            });
        } else {
            adapter.clear();
            adapter.notifyDataSetChanged();
            ParseQuery<ParseMessage> query = ParseQuery.getQuery("Message");
            query.whereEqualTo("event", event[0]);
            query.findInBackground(new FindCallback<ParseMessage>() {
                public void done(List<ParseMessage> messages, ParseException e) {
                    if (e == null) {
                        Log.d("Message on refresh", "Retrieved " + messages.size() + " scores");
                        for (ParseMessage message : messages) {
                            try {
                                if (message.getSenderUsername() == currUser.getUsername()) {
                                    adapter.add("You: " + message.getContent());
                                } else {
                                    adapter.add(message.getSenderUsername() + ": " + message.getContent());
                                }
                                scrollMyListViewToBottom(adapter);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        Log.d("Messages on refresh", "Error: " + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Helper, it scrolls ListView to last element when application update messages
     */
    private void scrollMyListViewToBottom(ArrayAdapter<String> adapt) {
        final ListView messagesContainer = (ListView) findViewById(R.id.listMessages);
        final ArrayAdapter<String> adapter = adapt;
        messagesContainer.post(new Runnable() {
            @Override
            public void run() {
                messagesContainer.setSelection(adapter.getCount() - 1);
            }
        });
    }

    /**
     * Overwritten onBackPressed that stops refresh timer for updating messages
     */
    @Override
    public void onBackPressed()
    {
        if(autoUpdate != null) {
            autoUpdate.cancel();
            autoUpdate = null;
        }
        finish();
    }
}
