package com.common;

public class Game {
	private int player1Score = 0;
	private int player2Score = 0;
	
	public Game() {};
	public Game(int scoreP1, int scoreP2) {
		player1Score = scoreP1;
		player2Score = scoreP2;
	}
	
	public String getScore() {
		return Integer.toString(player1Score).concat("-").concat(Integer.toString(player2Score));
	}
	
	public void player1Scored() {
		if(!hasEnded()) {
			player1Score++;
		}
	}
	
	public void player2Scored() {
		if(!hasEnded()) {
			player2Score++;
		}
	}
	
	public boolean hasEnded() {
		return (player1Score >= 11 || player2Score >= 11) &&
				(Math.abs(player2Score - player1Score) >= 2);
	}
	 
	/*
	 * return -1 if player 1 has won the game, 
	 * return 1 if player 2 has won the game,
	 * return 0 if game has no winner
	 */
	public int getWinner() {
		if(hasEnded()) {
			return player1Score > player2Score ? -1 : 1;
		}
		return 0;
	}
}
