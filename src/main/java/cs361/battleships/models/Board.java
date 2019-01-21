package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public boolean m_placed = false;
	public boolean d_placed = false;
	public boolean b_placed = false;

	public Board() {
		// TODO Implement
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
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
		return true;
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
}