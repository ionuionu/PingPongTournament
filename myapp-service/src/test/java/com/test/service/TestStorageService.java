package com.test.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.common.User;
import com.service.StorageService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestStorageService {
	static StorageService storageService = new StorageService("localhost", 27017, "PingPongTest");
	
	String valid_mail = "test@pingpong.com";
	String invalid_mail = "abcdefg@abcd.abc";
	
	User existingUser = new User(valid_mail, "abcDEF78", "regular", "Regular Test User");
	User newUser = new User("test2@pingpong.com", "abcDEF78", "regular", "Regular Test 2 User");
	User unexistingUser = new User(invalid_mail, "abcdefg", "regular", "Unexisting Test User");
	
	@AfterAll
	static void closeStorageService() {
		storageService.close();
	}
	
	@Test
	public void test_database_fetch_existing_user() {		
		User fetchedUser = storageService.getUserByMail(valid_mail);
		assertEquals(existingUser, fetchedUser,
				"Should allow fetching a user by email");
	}
	
	@Test
	public void test_database_fetch_unexisting_user() {
		User fetchedUser = storageService.getUserByMail(invalid_mail);
		assertNull(fetchedUser,
				"Should return null when fetching a user with an unexisting mail");
	}
	
	@Test
	public void test_database_create_user() {
		assertTrue(storageService.createUser(newUser),
				"Should create a user with success");
		storageService.deleteUserByEmail(newUser.getMail());
	}
	
	@Test
	public void test_database_create_existing_user() {		
		assertFalse(storageService.createUser(existingUser),
				"Should not allow multiple users with same email");
	}
	
	@Test
	public void test_database_delete_existing_user() {
		storageService.createUser(newUser);
		assertTrue(storageService.deleteUserByEmail(newUser.getMail()),
				"Should delete an existing user");
	}
	
	@Test
	public void test_database_delete_unexisting_user() {		
		assertFalse(storageService.deleteUserByEmail(invalid_mail),
				"Should not delete an unexisting user");
	}
}
