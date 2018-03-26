package com.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {
	private static final Pattern VALID_MAIL_REGEX = Pattern.compile("^[A-Z0-9]+@[A-Z0-9]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", Pattern.UNICODE_CASE);
	
	public static void ValidateMail(String emailStr) throws Exception {
		Matcher matcher = VALID_MAIL_REGEX .matcher(emailStr);
		if (!matcher.find()) {
			throw new Exception("Incorrect mail");
		}
	}
	
	public static void ValidatePassword(String passwordString) throws Exception {
		/*
		 * ^                 # start-of-string
		 * (?=.*[0-9])       # a digit must occur at least once
		 * (?=.*[a-z])       # a lower case letter must occur at least once
		 * (?=.*[A-Z])       # an upper case letter must occur at least once
		 * (?=\\S+$)         # no whitespace allowed in the entire string
		 * .{8,}             # anything, at least 8 places though
		 * $                 # end-of-string
		 */
		Matcher matcher = VALID_PASSWORD_REGEX .matcher(passwordString);
		if (!matcher.find()) {
			throw new Exception("Password must be at least 8 characters");
		}
	}
}
