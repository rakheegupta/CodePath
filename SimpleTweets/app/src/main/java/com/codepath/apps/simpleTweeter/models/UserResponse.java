package com.codepath.apps.simpleTweeter.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakhe on 2/26/2016.
 */
public class UserResponse {
    List<User> users;
    public UserResponse(){
        users= new ArrayList<User>();
    }
}
