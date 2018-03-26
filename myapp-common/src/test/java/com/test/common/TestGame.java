package com.test.common;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.common.Game;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestGame {

	@Test
	public void test_game_getScore() {
		Game testGame = new Game();
		
		assertEquals("0-0", testGame.getScore(),
				"An game should start at 0 points for each player");
	}
	
	@Test
	public void test_game_playerScoresPoint() {
		Game testGame = new Game(0, 0);
		
		testGame.player1Scored();
		assertEquals("1-0", testGame.getScore(),
				"When a player scores a point, score should be updated");
	}
	
	@Test
	public void test_game_ended_playerScoresPoint() {
		Game testGame = new Game(7, 11);
		
		assertTrue(testGame.hasEnded(),
				"A game should when a player has at least 11 points and has 2 points more than the opponent");
		testGame.player1Scored();
		assertEquals("7-11", testGame.getScore(),
				"Score should not be updated after the game ends");
		testGame.player2Scored();
		assertEquals("7-11", testGame.getScore(),
				"Score should not be updated after the game ends");
	}
	
	@Test
	public void test_game_hasEnded() {
		Game testGame1 = new Game(1, 1);
		Game testGame2 = new Game(10, 11);
		Game testGame3 = new Game(15, 16);
		Game testGame4 = new Game(15, 17);
		
		assertFalse(testGame1.hasEnded(),
				"A game shouldn't end before a player hits 11 points");
		assertFalse(testGame2.hasEnded(),
				"A game shouldn't end until there are 2 points difference between players");
		assertFalse(testGame3.hasEnded(),
				"A game shouldn't end until there are 2 points difference between players");
		assertTrue(testGame4.hasEnded(),
				"A game should when a player has at least 11 points and has 2 points more than the opponent");
		
	}
}
