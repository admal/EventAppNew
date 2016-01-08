package models;

import java.util.Date;

/**
 * Created by adam on 23.11.15.
 */
public class Message extends DataEntity //not the final version!
{
    private User sender;
    private Date sendDate;
    private String message;

    public Message(int id, String message, User sender) {
        super(id);
        this.message = message;
        this.sender = sender;
    }
}
