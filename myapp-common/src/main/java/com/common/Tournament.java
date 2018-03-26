package com.common;

public class Tournament {

	private String name;
	private Match grandF;
	private Match semiF1;
	private Match semiF2;
	private Match match1;
	private Match match2;
	private Match match3;
	private Match match4;
	
	public Tournament(String name) {
		this.name = name;
	}
	
	public User getWinner() {
		return grandF != null ? grandF.getWinner() : null;
	}
}
