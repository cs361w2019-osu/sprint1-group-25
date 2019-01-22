package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
  
	public boolean m_placed = false;
	public boolean d_placed = false;
	public boolean b_placed = false;

	private int maxRows = 10;
	private int maxCols = 10;

	@JsonProperty private List<Square> boardSquares;
	@JsonProperty private List<Square> shipSquares;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
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
    if (ship.getKind().equals("MINESWEEPER")) {
        shipLength = 2;
        if (this.m_placed) {
            return false;
        }
        this.m_placed = true;
    }

    if (ship.getKind().equals("DESTROYER")) {
        shipLength = 3;
        if (this.d_placed) {
            return false;
        }
        this.d_placed = true;
    }

    if (ship.getKind().equals("BATTLESHIP")) {
        shipLength = 4;
        if (this.b_placed) {
            return false;
        }
        this.b_placed = true;
    }
    
		boolean shipPlaced = true;
		int shipLength = 0;

		if (isVertical) {
			for (int i = x; i < shipLength; i++) {
				if (i > 10) {
					shipPlaced = false;
				} else {
					Square shipSquare = new Square(i, y);
					shipSquares.add(shipSquare);
				}
			}
		} else {
			for (int j = ((int)y - 65); j < shipLength; j++) {
				if (j > 10) {
					shipPlaced = false;
				} else {
					char newChar = (char)(j + 65);
					Square shipSquare = new Square(x, newChar);
					shipSquares.add(shipSquare);
				}
			}
		}


		return shipPlaced;
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