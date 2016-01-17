package models;

import java.util.Date;

/**
 * Created by Adam on 2015-11-23.
 * It will be loaded from local database
 */
public class Notyfication extends DataEntity //not the final version!
{
    private String type; //will be edited into enum probably (types: msg, new users)
    private Date cameAt;
    private Date readAt;
    private String msg;

    public Notyfication(int id, String msg)
    {
        super(id);
        this.msg = msg;
    }
}
