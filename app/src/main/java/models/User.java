package models;

/**
 * Created by adam on 23.11.15.
 */

import java.util.ArrayList;

/**
 * Class representing user in app
 */
public class User extends DataEntity //not the final version!
{
    private String username;
    private String passwd;
    private String name;
    private String surname;
    private String email;

    /**
     * Friends of a user
     */
    private ArrayList<User> Friends;
    /**
     * Messages sent by user
     */
    private ArrayList<Message> Messages;
    /**
     * Events that user was invited
     */
    public ArrayList<UsersEvents> usersEvents; //user's events (that was invited to)
    /**
     * Events created by a user
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
