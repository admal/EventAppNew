package Data.Repositories;

import java.util.ArrayList;

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
}
