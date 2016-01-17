package com.lab.eventapp.Parsers;

import java.util.Arrays;

import models.User;

/**
 * Created by Adam on 2016-01-13.
 */
public class JsonUserResponseObject extends JsonResponseObject {
    public User[] users;

    @Override
    public String toString() {
        return "JsonUserResponseObject{" +
                "error=" + error +
                ", users=" + Arrays.toString(users) +
                '}';
    }
}
