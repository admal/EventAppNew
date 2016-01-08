package models;

/**
 * Created by Adam on 2015-11-23.
 */
public class UsersEvents
{
    public User user;
    public Event event;
    public boolean isGoing;

    public UsersEvents(User user, Event event)
    {
        this.user = user;
        this.event = event;
        isGoing = false;
    }
}
