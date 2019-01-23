package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Board {
  
	public boolean m_placed = false;
	public boolean d_placed = false;
	public boolean b_placed = false;

	private int maxRows = 10;
	private int maxCols = 10;

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships = new ArrayList<Ship>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {

		int shipLength = ship.getShipLength();


		Ship new_ship = new Ship();
		List<Square> occupiedSquares = new ArrayList<Square>();

		// Make sure ship is in bounds and doesn't overlap
		if (!isVertical) { // Horizontal
			if (x > 10 || x < 0 || (int)y > (75 - shipLength) || y < 65) {
				return false;
			}
			// For all ships
			for (int i = 0; i < ships.size(); i++) {
				// For all occupied squares
				for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
					for (int k = 0; k < shipLength; k++) {
						// Check if every square of the new ship overlaps with a previously placed ship
						if (ships.get(i).getOccupiedSquares().get(j).getRow() == x && ships.get(i).getOccupiedSquares().get(j).getColumn() == (char)((int)y + k)) {
							return false;
						}
					}
				}
			}
			// Set the occupied squares for the new ship
			for (int i = 0; i < shipLength; i++) {
				Square s = new Square(x, (char)((int)y + i));
				occupiedSquares.add(s);
			}
			new_ship.setOccupiedSquares(occupiedSquares);
			new_ship.getOccupiedSquares().get(0).getColumn();

		} else { // Vertical
			if (x > (10 - shipLength) || x < 0 || (int)y > 74 || (int)y < 65) {
				return false;
			}
			// For all ships
			for (int i = 0; i < ships.size(); i++) {
				// For all occupied squares
				for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {
					for (int k = 0; k < shipLength; k++) {
						// Check if every square of the new ship overlaps with a previously placed ship
						if (ships.get(i).getOccupiedSquares().get(j).getColumn() == y && ships.get(i).getOccupiedSquares().get(j).getRow() == (x + k)) {
							return false;
						}
					}
				}
			}
			// Set the occupied squares for the new ship
			for (int i = 0; i < shipLength; i++) {
				Square s = new Square(x + i, y);
				occupiedSquares.add(s);
			}
			new_ship.setOccupiedSquares(occupiedSquares);
		}

		// Make sure ship cant get placed twice
		if (ship.getKind().equals("MINESWEEPER")) {
			if (this.m_placed) {
				System.out.println("already placed");
				return false;
			}
			this.m_placed = true;
		}
		if (ship.getKind().equals("DESTROYER")) {
			if (this.d_placed) {
				System.out.println("already placed");
				return false;
			}
			this.d_placed = true;
		}
		if (ship.getKind().equals("BATTLESHIP")) {
			if (this.b_placed) {
				System.out.println("already placed");
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
		int y_idx = (int)y - 65;
		Square s = new Square(x, y);
		Result a = new Result(s);
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