package com.test.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.*;

import com.common.Const.MATCH_STATE;
import com.common.Game;
import com.common.Match;
import com.common.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestMatch {

	@Test
	public void test_match_not_started_getScore() {
		User player1 = new User("player1@pingpong.com", "abcDEF78", "regular", "player1");
		User player2 = new User("player2@pingpong.com", "abcDEF78", "regular", "player2");
		Match testMatch = new Match(player1, player2);
		
		assertEquals("0-0", testMatch.getScoreboard(),
				"Match should start at 0-0");
	}
	
	@Test
	public void test_match_not_started_scorePoint() {
		User player1 = new User("player1@pingpong.com", "abcDEF78", "regular", "player1");
		User player2 = new User("player2@pingpong.com", "abcDEF78", "regular", "player2");
		Match testMatch = new Match(player1, player2);
		testMatch.scorePoint(player1);
		
		assertEquals("0-0", testMatch.getScoreboard(),
				"Player should not be able to score when match is not started");
	}
	
	@Test
	public void test_match_getWinner() {
		User player1 = new User("player1@pingpong.com", "abcDEF78", "regular", "player1");
		User player2 = new User("player2@pingpong.com", "abcDEF78", "regular", "player2");
		Game gameWonByPlayer1 = new Game(11, 8);
		Game gameWonByPlayer2 = new Game(10, 12);
		LinkedList<Game> gamesList = new LinkedList<Game>();
		gamesList.add(gameWonByPlayer1);
		gamesList.add(gameWonByPlayer2);
		gamesList.add(gameWonByPlayer2);
		gamesList.add(gameWonByPlayer1);
		gamesList.add(gameWonByPlayer1);
		
		Match testMatch = new Match(player1, player2);

		testMatch.setState(MATCH_STATE.NOT_STARTED);
		assertNull(testMatch.getWinner(),
				"Match should not have a winner until is ended");
		
		testMatch.setState(MATCH_STATE.IN_PROGRESS);
		testMatch.scorePoint(player1);
		assertNull(testMatch.getWinner(),
				"Match should not have a winner until is ended");
		
		testMatch.setGames(gamesList);
		assertNull(testMatch.getWinner(), 
				"Match should not have a winner until is ended");
		
		testMatch.setState(MATCH_STATE.ENDED);
		assertEquals(player1, testMatch.getWinner(), 
				"A match should have a winner if a player has won 3 games out of 5");
		assertEquals("11-8 10-12 10-12 11-8 11-8", testMatch.getScoreboard(),
				"Should return the scoreboard");
	}
	
	@Test
	public void test_match_in_progress_scorePoint() {
		User player1 = new User("player1@pingpong.com", "abcDEF78", "regular", "player1");
		User player2 = new User("player2@pingpong.com", "abcDEF78", "regular", "player2");
		
		Match testMatch = new Match(player1, player2);
		
		testMatch.setState(MATCH_STATE.IN_PROGRESS);
		testMatch.scorePoint(player1);
		
		assertEquals("1-0", testMatch.getScoreboard(),
				"When a player scores a point, score should be updated");
	}
	
	@Test
	public void test_match_in_progress_scorePoint_end_game() {
		User player1 = new User("player1@pingpong.com", "abcDEF78", "regular", "player1");
		User player2 = new User("player2@pingpong.com", "abcDEF78", "regular", "player2");
		Game gameWonByPlayer1 = new Game(11, 8);
		Game gameWonByPlayer2 = new Game(10, 11);
		LinkedList<Game> gamesList = new LinkedList<Game>();
		gamesList.add(gameWonByPlayer1);
		gamesList.add(gameWonByPlayer2);
		
		Match testMatch = new Match(player1, player2);
		
		testMatch.setGames(gamesList);
		testMatch.setState(MATCH_STATE.IN_PROGRESS);
		assertEquals("11-8 10-11", testMatch.getScoreboard(),
				"Should be able to retrive the score when a match is in progress");
		
		testMatch.scorePoint(player2);
		assertEquals("11-8 10-12", testMatch.getScoreboard(),
				"When a player scores a point, score should be updated");
		
		testMatch.scorePoint(player2);
		assertEquals("11-8 10-12 0-1", testMatch.getScoreboard(),
				"When a player scores a point, score should be updated");
		
		gamesList = testMatch.getGames();
		gamesList.removeLast();
		gamesList.addLast(gameWonByPlayer2);
		gameWonByPlayer2 = new Game(12, 13);
		gamesList.addLast(gameWonByPlayer2);
		testMatch.setGames(gamesList);
		assertEquals("11-8 10-12 10-12 12-13", testMatch.getScoreboard(),
				"Should allow updating the game list");
		
		testMatch.scorePoint(player2);
		assertEquals("11-8 10-12 10-12 12-14", testMatch.getScoreboard(),
				"When a player scores a point, score should be updated");
		assertEquals(player2, testMatch.getWinner(),
				"When a player scores a point, winner should be updated");
		assertEquals(MATCH_STATE.ENDED, testMatch.getState(),
				"When the winner is decleared, the match should end");
		
		testMatch.scorePoint(player2);
		assertEquals(player2, testMatch.getWinner(),
				"After match has ended, score shouldn't be changed");
		
	}
}
