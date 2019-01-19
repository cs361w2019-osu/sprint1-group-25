package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;;

public class Board {

	private int maxRows = 10;
	private int maxCols = 10;

	@JsonProperty private List<Square> boardSquares;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
		// Create a bunch of new square objects via loop, and store them in a list.

		for (int i = 1; i <= maxRows; i++) {
			for (int j = 0; j < maxCols; j++) {
				Square loopSquare = new Square(i, (char)(j + 65));
				this.boardSquares.add(loopSquare);
			}
		}

	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		//TODO implement
		return null;
	}

	public void setShips(List<Ship> ships) {
		//TODO implement
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}

	public List<Square> getBoardSquares() {
		return boardSquares;
	}
}