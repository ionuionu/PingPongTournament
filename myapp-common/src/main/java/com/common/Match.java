package com.common;

import java.util.Deque;
import java.util.LinkedList;

import com.common.Const.MATCH_STATE;
import com.common.Const.MATCH_TYPE;

public class Match {
	private User player1 = null;
	private User player2 = null;
	
	private Deque<Game> games;
	private MATCH_STATE state = MATCH_STATE.NOT_STARTED;
	private MATCH_TYPE matchType = MATCH_TYPE.BO5;
	
	public Match(User player1, User player2) {
		this.player1 = player1;
		this.player2 = player2;
		games = new LinkedList<Game>();
		games.offer(new Game());
	}
	
	public LinkedList<Game> getGames() {
		return (LinkedList<Game>)games;
	}

	public void setGames(LinkedList<Game> matchGames) {
		games = matchGames;
	}
	
	public User getPlayer1() {
		return player1;
	}
	public void setPlayer1(User player1) {
		this.player1 = player1;
	}
	public User getPlayer2() {
		return player2;
	}
	public void setPlayer2(User player2) {
		this.player2 = player2;
	}
	public MATCH_STATE getState() {
		return state;
	}
	public void setState(MATCH_STATE state) {
		this.state = state;
	}
	
	public String getScoreboard() {
		if(games.isEmpty()) return "0-0";
		
		String score = "";
		for(Game game : games) {
			if(!score.isEmpty()) score += " ";
			score += game.getScore();
		}
		return score;
	}
	
	public User getWinner() {
		if(state != MATCH_STATE.ENDED) return null;
		if(!games.peekLast().hasEnded()) return null;
		
		int scorePlayer1 = getPlayer1Score();
		int scorePlayer2 = getPlayer2Score();
		
		if(matchType.canBeEnded(scorePlayer1, scorePlayer2)) {
			return scorePlayer1 > scorePlayer2 ? player1 : player2;
		}
		return null;
	}
	
	public void scorePoint(User player) {
		if(state != MATCH_STATE.IN_PROGRESS) return;
		
		Game currentGame = null;
		if(games.isEmpty()) {
			currentGame = new Game();
			games.offerLast(currentGame);
		} else {
			Game lastGame = games.peekLast();
			if(lastGame.hasEnded()) {
				currentGame = new Game();
				games.offerLast(currentGame);
			} else {
				currentGame = lastGame;
			}
		}

		if(player1.equals(player)) {
			currentGame.player1Scored();
		} else {
			if(player2.equals(player)) {
				currentGame.player2Scored();
			}
		}
		
		if(matchType.canBeEnded(getPlayer1Score(), getPlayer2Score())) setState(MATCH_STATE.ENDED);
	}
	
	public int getPlayer1Score() {
		int score = 0;
		for(Game game : games) {
			if(game.hasEnded()) {
				if(game.getWinner() == -1) {
					score++;
				}
			}
		}
		return score;
	}
	public int getPlayer2Score() {
		int score = 0;
		for(Game game : games) {
			if(game.hasEnded()) {
				if(game.getWinner() == 1) {
					score++;
				}
			}
		}
		return score;
	}
}
