package com.lab.eventapp.ActivityInterfaces;

import com.parse.ParseUser;

import java.util.List;

import models.ParseUsersEvent;

/**
 * Created by Adam on 2016-01-25.
 * Provides addable interface that allows to update user lists in activities that implements it.
 */
public interface IUserAddable {
    /**
     * Add user to the list
     * @param users user to be added
     */
    void AddUsers(List<ParseUser> users);
}
