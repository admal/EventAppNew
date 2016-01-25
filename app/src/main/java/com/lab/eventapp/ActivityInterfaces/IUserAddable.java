package com.lab.eventapp.ActivityInterfaces;

import com.parse.ParseUser;

import java.util.List;

import models.ParseUsersEvent;

/**
 * Created by Adam on 2016-01-25.
 */
public interface IUserAddable {
    void AddUsers(List<ParseUser> users);
}
