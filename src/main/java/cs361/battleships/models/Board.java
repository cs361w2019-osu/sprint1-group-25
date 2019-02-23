package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Result> sonarSquares;
	@JsonProperty private int sonars;
	@JsonProperty private boolean sonarEarned;
	

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
		sonarSquares = new ArrayList<>();
		sonars = 1;
		sonarEarned = false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (ships.size() >= 3) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		final var placedShip = new Ship(ship.getKind());
		placedShip.place(y, x, isVertical);
		if (ships.stream().anyMatch(s -> s.overlaps(placedShip))) {
			return false;
		}
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		Result attackResult = attack(new Square(x, y));
		attacks.add(attackResult);
		return attackResult;
	}

	public void sonar(int x, char y) {
		this.sonars--;
		for( int boxY = -2; boxY <= 2; boxY++ ) {
			for( int boxX = -2; boxX <= 2; boxX++ ) {
				if ( Math.abs(boxX) + Math.abs(boxY)  <= 2 ) {
					Square s = new Square( (x + boxX), (char)((int)y + boxY) );
					if (!s.isOutOfBounds()) {
						Result attackResult = sonar(s);
						sonarSquares.add(attackResult);
					}
				}
			}
		}
	}

	private Result attack(Square s) {
		if (attacks.stream().anyMatch(r -> r.getLocation().equals(s))) {
			var attackResult = new Result(s);
			attackResult.setResult(AtackStatus.INVALID);
			return attackResult;
		}
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}
		var hitShip = shipsAtLocation.get(0);
		var attackResult = hitShip.attack(s.getRow(), s.getColumn());
		if (attackResult.getResult() == AtackStatus.SUNK) {
			for ( Result oneAttack : attacks ) {
				if ( hitShip.isAtLocation(oneAttack.getLocation()) ) {
					oneAttack.setResult(AtackStatus.SUNK);
				}
			}
			if ( this.sonarEarned == false ) {
				this.sonars++;
				this.sonarEarned = true;
			}
			if (ships.stream().allMatch(ship -> ship.isSunk())) {
				attackResult.setResult(AtackStatus.SURRENDER);
			}
		}
		return attackResult;
	}
	// Returns a result status based on the square passed in, but doesn't test against the attacks list.
	private Result sonar(Square s) {
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}
		var attackResult = new Result(s);
		attackResult.setResult(AtackStatus.HIT);
		return attackResult;
	}

	public int getSonars() {
		return this.sonars;
	}

	List<Ship> getShips() {
		return ships;
	}
}
