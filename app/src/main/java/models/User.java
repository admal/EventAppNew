package models;

/**
 * Created by adam on 23.11.15.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Class representing users in app
 */
//public class User extends ParseUser
//{
//    public User() {}
//
//    //GETTERS
//    public String getUsername(){
//        return getString("username");
//    }
//    public String getEmail()
//    {
//        return getString("email");
//    }
//
//
//}

public class User extends DataEntity //not the final version!
{
    public String username;
    public  String passwd;
    public String name;
    public String surname;
    public String email;

    /**
     * Friends of a users
     */
    private ArrayList<User> Friends;
    /**
     * Messages sent by users
     */
    private ArrayList<Message> Messages;
    /**
     * Events that users was invited
     */
    public ArrayList<UsersEvents> usersEvents; //users's events (that was invited to)
    /**
     * Events created by a users
     */
    public ArrayList<Event> createdEvents;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFriends(ArrayList<User> friends) {
        Friends = friends;
    }

    public void setMessages(ArrayList<Message> messages) {
        Messages = messages;
    }

    public User()
    {
        super();
    }
    public User(int id) {

        super(id);
    }

    public User(int id, String username) {
        super(id);
        this.username = username;
        usersEvents = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\''+
                '}';
    }
}
