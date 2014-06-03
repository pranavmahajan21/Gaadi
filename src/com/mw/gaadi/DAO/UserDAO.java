package com.mw.gaadi.DAO;

import android.content.Context;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserDAO {

	ParseQuery<ParseUser> query;

	public UserDAO(Context context) {
		super();
		Parse.initialize(context, "crIV3jTpML60lFhYDqXOQmLt1oVxKVctwZfpfRtx",
				"llKpT2eivS7Mi5bRGSSfa0hr9tbbxU8Vey9HUuYh");
		query = ParseUser.getQuery();
	}

	public ParseUser loginUser(String userName, String password) {
        try {
            ParseUser.logIn(userName, password);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return ParseUser.getCurrentUser();
	}
	
	public List<ParseUser> getAllUsers() {
		System.out.println("get all users");
		List<ParseUser> userList = null;
		try {
			userList = query.orderByAscending("name").find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return userList;
	}

    public ParseUser getUserById(String userId) {
        System.out.println(">>>>>> get user by id");
        ParseUser contactUser = null;
        query.whereEqualTo("objectId", userId);
        try {
            contactUser = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return contactUser;
    }
}
