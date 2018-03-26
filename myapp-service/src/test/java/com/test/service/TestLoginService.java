package com.test.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.service.LoginService;
import com.service.StorageService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLoginService {
	String valid_mail = "test@pingpong.com";
	String valid_password = "abcDEF78";
	String invalid_mail = "abcdefg@abcd.abc";
	String invalid_password = "abcdefgh";
	
	static StorageService storageService = new StorageService("localhost", 27017, "PingPongTest");
	LoginService loginService = new LoginService(storageService);
	
	@AfterAll
	static void close() {
		storageService.close();
	}
	
	@Test
	public void test_success_login() {
		assertTrue(loginService.performLogin(valid_mail, valid_password),
				"Login should be performed successfuly for valid credentials");
	}
	
	@Test
	public void test_incorrect_login() {
		assertFalse(loginService.performLogin(valid_mail, invalid_password), 
				"Login shouldn't be performed for invalid credentials");
	}
	
	@Test
	public void test_unexisting_login() {
		assertFalse(loginService.performLogin(invalid_mail, valid_password), 
				"Login shouldn't be performed for invalid credentials");
	}
}
