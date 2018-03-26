package com.service;
import com.common.User;
import static com.common.Validator.*;
import com.service.StorageService;

public class LoginService {
	private StorageService users_db;
	
	public LoginService (StorageService store) {
		users_db = store;
	}
	
	public Boolean performLogin(String mail, String password) {
		try {
			ValidateMail(mail);
			ValidatePassword(password);
			
			User usr = users_db.getUserByMail(mail);
			return usr.checkPassword(password);
		} catch (Exception e) {
			return false;
		}
	}
}
