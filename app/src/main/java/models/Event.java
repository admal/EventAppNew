package models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adam on 2015-11-23.
 */
public class Event extends DataEntity
{
    private String title;
    private User ownerObject;
    private int owner;
    private ArrayList<UsersEvents> participants;
    private Date start;
    private Date end;
    private String description;
    private String place;
    private int conservation;

    public Event()
    {
        super();
    }
    public Event(int id, String tit, User ownerObject, Date start)
    {
        super(id);
        title = tit;
        this.ownerObject = ownerObject;
        this.start = start;
    }

    public Event(int id) {
        super(id);
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId()
    {
        return ownerObject.id;
    }

    public Date getStartDate() {
        return start;
    }

    public String getPlace() {
        return place;
    }

    public Date getEndDate() {
        return end;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setParticipants(ArrayList<UsersEvents> participants) {
        this.participants = participants;
    }

    public void setOwnerObject(User ownerObject) {
        this.ownerObject = ownerObject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
}
