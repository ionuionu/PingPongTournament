package com.service;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.common.User;
import static com.common.Validator.ValidateMail;
import static com.common.Validator.ValidatePassword;

import com.google.gson.Gson;
import com.mongodb.*;

public class StorageService {

	MongoClient mongo_client;
	
	DB user_db;
	DBCollection user_table;
	
	String DB_HOST;
	int DB_PORT;
	String DB_NAME = "PingPongDB";
	
	public StorageService(String host, int port, String name) {
		DB_HOST = host;
		DB_PORT = port;
		DB_NAME = name;
		
		try {
		InitializeMongoClient();
		InitializeDB();
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, "DB_CONNECTION_ERROR: " + e.getMessage());
		}
	}
	
	public void close() {
		mongo_client.close();
	}
	 
	public User getUserByMail(String mail) {
		try {
			ValidateMail(mail);
			
			BasicDBObject userQuery = new BasicDBObject();
			userQuery.put("mail", mail);
			BasicDBObject userFields = new BasicDBObject();
			userFields.put("_id", 0);
			userFields.put("mail", 1);
			userFields.put("password", 1);
			userFields.put("role", 1);
			userFields.put("name", 1);
			
			DBObject usr = user_table.findOne(userQuery, userFields);
			Gson gson = new Gson();
			
			return gson.fromJson(usr.toString(), User.class);
		} catch(Exception e) {
			return null;
		}
	}
	
	public boolean createUser(User newuser) {
		try {
			String emailStr = newuser.getMail();
			String passwordStr = newuser.getPassword();
			String roleStr = newuser.getRole();
			String nameStr = newuser.getName();
			
			ValidateMail(emailStr);
			ValidatePassword(passwordStr);
			
			BasicDBObject userQuery = new BasicDBObject();
			userQuery.put("mail", emailStr);
			DBCursor cursor = user_table.find(userQuery);
			if(cursor.count() > 0) {
				cursor.close();
				return false;
			}
			cursor.close();
			
			BasicDBObject user = new BasicDBObject();
			user.put("mail", emailStr);
			user.put("password", passwordStr);
			
			user.put("role", roleStr);
			user.put("name", nameStr);
			user_table.insert(user);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean deleteUserByEmail(String mail) {
		try {
			ValidateMail(mail);
			
			BasicDBObject userQuery = new BasicDBObject();
			userQuery.put("mail", mail);
			DBCursor cursor = user_table.find(userQuery);
			if(cursor.count() > 0) {
				cursor.close();
				user_table.remove(userQuery);
				return true;
			}
			cursor.close();
			return false;
		} catch(Exception e) {
			return false;
		}
	}

	private void InitializeDB() {
		user_db = mongo_client.getDB(DB_NAME);
		user_table = user_db.getCollection("Users");
		/*
		if(user_table.count() == 0) {
			BasicDBObject user = new BasicDBObject();
			user.put("mail", "test@pingpong.com");
			user.put("password", "abcDEF78");
			user.put("role", "regular");
			user.put("name", "Regular Test User");
			user_table.insert(user);
		}
		*/
	}

	private void InitializeMongoClient() throws UnknownHostException {
		mongo_client = new MongoClient(DB_HOST, DB_PORT);
	}
}
