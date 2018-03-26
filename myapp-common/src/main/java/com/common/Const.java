package com.common;

public class Const {
	public enum MATCH_STATE {NOT_STARTED, IN_PROGRESS, ENDED}
	public enum MATCH_TYPE {
		Custom, BO5, BO7, BO9;
		public boolean canBeEnded(int player1Score, int player2Score) {
			switch(this) {
				case BO5:
					return (player1Score == 3 || player2Score == 3);
				default:
					return false;
			}
		}
		}
}
