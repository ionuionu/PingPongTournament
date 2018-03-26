package com.common;

public class User {

	String mail;
	String password;
	String role;
	String name;
	
	public User(String mail, String password, String role, String name) {
		this.mail = mail;
		this.password = password;
		this.role = role;
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getRole() {
		return role;
	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public boolean equals(Object other) {
		if (other == this) return true;
	    if (other == null) return false;
	    if (getClass() != other.getClass()) return false;
		
	    User usr = (User)other;
		return mail.equals(usr.getMail()) &&
				name.equals(usr.getName()) &&
				role.equals(usr.getRole()) &&
				password.equals(usr.getPassword());
	}
}
