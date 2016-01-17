package com.lab.eventapp.Parsers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Data.Repositories.UsersRepository;
import models.Event;
import models.Message;
import models.User;

/**
 * Created by Adam on 2015-11-30.
 */
public class JsonParser
{
    public User[] parseUsers(String json)
    {
        Gson gson = new Gson();
        JsonUserResponseObject obj = gson.fromJson(json, JsonUserResponseObject.class);
        Log.d("fromParser", obj.toString());
        if(obj.error)
            return null;

        return obj.users;
    }
    public Event[] parseEvents(String json)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        JsonEventResponseObject obj = gson.fromJson(json, JsonEventResponseObject.class);
        Log.d("fromParser", obj.toString());
        if(obj.error)
            return null;

        return obj.events;
    }
    public Message[] parseMessages(String json)
    {
        Gson gson = new Gson();
        JsonMessageResponseObject obj = gson.fromJson(json, JsonMessageResponseObject.class);
        Log.d("fromParser", obj.toString());
        if(obj.error)
            return null;

        return obj.messages;
    }

    public <T> ArrayList<T>parseListEntities(String json, Class<T> clazz)
    {
        ArrayList<T> list = new ArrayList<>();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<T>>(){}.getType(); //dat java -.-
        list = gson.fromJson(json,collectionType);
        return list;
    }

//    public User parseUser(String json)
//    {
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray arr = jsonObject.getJSONArray("users");
//            JSONObject obj = arr.getJSONObject(0);
//            int userId = obj.getInt("id");
//            User user = new User(userId);
//            user.setEmail(obj.getString("email"));
//            user.setUsername(obj.getString("username"));
//            user.setSurname(obj.getString("surname"));
//
//            return user;
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            Log.e("json-error", e.getMessage());
//            return null;
//        }
//    }
    /* {"error":false,"users":[{"id":2,"username":"test","name":"test3","surname":"test4","email":"aqq2@gmail.com"}]}}*/

    /**
     * Parses table of events
     * @param json string in sjon format
     * @return ListArray of events, null if any error occured
     */
//    public ArrayList<Event> parseEvents(String json)
//    {
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray events = jsonObject.getJSONArray("events");
//            ArrayList<Event> retArray = new ArrayList<>();
//            for (int i = 0; i < events.length(); i++)
//            {
//                JSONObject obj = events.getJSONObject(i);
//                int id = obj.getInt("id");
//
//                Event event = new Event(id);
//
//                UsersRepository repo = new UsersRepository();
//                event.setOwnerObject(repo.getUserById(obj.getInt("owner")));
//
//                event.setTitle(obj.getString("title"));
//                event.setDescription(obj.getString("description"));
//
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                event.setStart(df.parse(obj.getString("start")));
//                event.setEnd(df.parse(obj.getString("end")));
//
//
//                retArray.add(event);
//            }
//            return  retArray;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
    /*{"error":false,
    "events":[
    {"id":5,"owner":1,"conversation":1,"title":"asda","description":"adadad","start":"2015-01-12","end":"2015-02-23"}
    ,{"id":6,"owner":1,"conversation":1,"title":"sad","description":"qwe","start":"2015-01-12","end":"2015-02-23"}]}*/


}
