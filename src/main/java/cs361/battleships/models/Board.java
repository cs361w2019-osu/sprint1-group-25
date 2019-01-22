package cs361.battleships.models;

import java.util.*;

public class Board {
  
	public boolean m_placed = false;
	public boolean d_placed = false;
	public boolean b_placed = false;

	private int maxRows = 10;
	private int maxCols = 10;

	private List<Ship> ships;
	private List<Result> attacks;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships =  new ArrayList<Ship>();
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

		Ship new_ship = new Ship();
		List<Square> occupiedSquares = new ArrayList<Square>();

		// Make sure ship is in bounds and doesn't overlap
		if (!isVertical) {
			if (x > (10 - shipLength) || x < 0 || (int)y > 75 || (int)y < 65) {
				return false;
			}
			System.out.println("OKKKK");
			System.out.println(ships.size());

			// For all ships
			for (int i = 0; i < ships.size(); i++) {
				// For all occupied squares
				for (int j = 0; i < ships.get(i).getOccupiedSquares().size(); j++) {
					for (int k = 0; k < shipLength; k++) {
						// Check if every square of the new ship overlaps with a previously placed ship
						if (ships.get(i).getOccupiedSquares().get(j).getRow() == (x + k) && ships.get(i).getOccupiedSquares().get(j).getColumn() == y) {
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

		} else {
			if (x > 10 || x < 0 || (int)y > (75 - shipLength) || y < 65) {
				return false;
			}
			for (int i = 0; i < ships.size(); i++) {
				for (int j = 0; i < ships.get(i).getOccupiedSquares().size(); j++) {
					for (int k = 0; k < shipLength; k++) {
						if (ships.get(i).getOccupiedSquares().get(j).getColumn() == (char)((int)y + k) || ships.get(i).getOccupiedSquares().get(j).getRow() == x) {
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