package models;

import java.util.ArrayList;

/**
 * Created by adam on 23.11.15.
 */
public class Conversation extends DataEntity //not the final version!
{
    private Event event;
    private ArrayList<Message> messages;

    public Conversation(int id, ArrayList<Message> messages, Event event) {
        super(id);
        this.messages = messages;
        this.event = event;
    }
}
