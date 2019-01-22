package cs361.battleships.models;

import java.util.*;

public class Board {
  
	public boolean m_placed = false;
	public boolean d_placed = false;
	public boolean b_placed = false;

	private int maxRows = 10;
	private int maxCols = 10;

	private List<Square> boardSquares;
	private List<Ship> ships;
	private List<Result> attacks;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// Create a bunch of new square objects via loop, and store them in a list.
		boardSquares = new ArrayList<Square>();
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
		boolean shipPlaced = true;
		int shipLength = 0;

		// Set shipLength according to ship type
		if (ship.getKind().equals("MINESWEEPER")) {
			shipLength = 2;
		}
		if (ship.getKind().equals("DESTROYER")) {
			shipLength = 3;
		}
		if (ship.getKind().equals("BATTLESHIP")) {
			shipLength = 4;
		}

		// Make a new ship object and fill its occupiedSquares
		List<Square> occupiedSquares = new ArrayList<Square>();
		Ship new_ship = new Ship();

		for (int i = x; i < shipLength; i++) {
			if (i > 10) {
				return false;
			}
			Square s = new Square(i, y);
			occupiedSquares.add(s);
		}
		new_ship.setOccupiedSquares(occupiedSquares);


		// Check if this ship kind has been placed before
		if (ship.getKind().equals("MINESWEEPER")) {
			if (this.m_placed) {
				return false;
			}
			this.m_placed = true;
		}

		if (ship.getKind().equals("DESTROYER")) {
			if (this.d_placed) {
				return false;
			}
			this.d_placed = true;
		}

		if (ship.getKind().equals("BATTLESHIP")) {
			if (this.b_placed) {
				return false;
			}
			this.b_placed = true;
		}

		// Add new ship to the board's ship list
		ships.add(new_ship);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		Square location = new Square(x, y);
		Result a = new Result(location);
		return a;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Result> getAttacks() {
		return this.attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}

}