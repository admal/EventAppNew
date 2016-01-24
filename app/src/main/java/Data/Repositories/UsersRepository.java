package Data.Repositories;

import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import bolts.Task;
import models.User;

/**
 * Created by Adam on 2015-11-30.
 *
 * TODO :ATTENTION! In every repository's contructor get data from db
 */
public class UsersRepository
{
    private ArrayList<User> users = new ArrayList<>();

    public User getUserById(int id)
    {
        for (User user :
                users) {
            if (user.getId() == id)
                return user;
        }
        return  null;
    }

    public Task<ParseUser> getUser(String username)
    {
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username", username);
        return query.getFirstInBackground();
    }
}
