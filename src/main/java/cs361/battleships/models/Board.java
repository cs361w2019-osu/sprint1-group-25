package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Ship> submarines;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Result> sonarSquares;
	@JsonProperty private int sonars;
	@JsonProperty private int sinkCount;
	@JsonProperty private boolean sonarEarned;
	@JsonProperty private boolean laserEarned;


	public Board() {
		ships = new ArrayList<>();
		submarines = new ArrayList<>();
		attacks = new ArrayList<>();
		sonarSquares = new ArrayList<>();
		sonars = 1;
		sinkCount=0;
	}

	public boolean placeShip(Ship ship, int x, char y, boolean isVertical, boolean isSubmerged) {

		// Check for max ship count on board, fail if max is hit
		if ( ( ships.size() + submarines.size() ) >= 4 ) {
			return false;
		}

		// Check for other ships of same type, fail if found
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}

		// Create a new ship
		final var placedShip = new Ship(ship.getKind());

		// Handle submarine case
		if ( placedShip.getKind() == "SUBMARINE") {
			placedShip.place(y, x, isVertical, isSubmerged);
			submarines.add(placedShip);
			return true;
		}

		// Place the ship on the board
		placedShip.place(y, x, isVertical, false);

		// Check if new ship overlaps others
		if (ships.stream().anyMatch(s -> s.overlaps(placedShip))) {
			return false;
		}
		// Check if new ship is off the board
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);
		return true;

	}

	public Result attack(int x, char y) {

		// Attack surface ships
		Result attackResult = attack(ships, new Square(x, y) );
		attacks.add(attackResult);

		// Attack subsurface ships if laser has been earned (after 1 sink)
		if ( this.sinkCount > 0 ) {

			Result subAttackResult = attack( submarines, new Square(x, y) );
			subAttackResult.setSubmerged(true);
			attacks.add(subAttackResult);

			// Check if game is over
			if ( ships.stream().allMatch(ship -> ship.isSunk()) && submarines.stream().allMatch(ship -> ship.isSunk()) ) {

				attackResult.setResult(AtackStatus.SURRENDER);
				return attackResult;

			// Return hit if anything is hit
			} else if (
				attackResult.getResult() == AtackStatus. HIT ||
				attackResult.getResult() == AtackStatus. SUNK ||
				subAttackResult.getResult() == AtackStatus.HIT ||
				subAttackResult.getResult() == AtackStatus.SUNK
			) {
				attackResult.setResult(AtackStatus.HIT);
			}
		}

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

	public void moveFleet(char direction) {
		int y = 0;
		int x = 0;
		if (direction == 'n') {
			y = -1;
		}
		if (direction == 'e') {
			x = 1;
		}
		if (direction == 's') {
			y = 1;
		}
		if (direction == 'w') {
			x = -1;
		}
		for (int i = 0; i < ships.size(); i++) {
			if (canMove(i,x,y)) {
				ships.get(i).move(x,y);
			}
		}
	}

	private boolean canMove(int index, int x, int y) {
		for (int i = 0; i < ships.size(); i++) {
			if (i != index) {
				for (int j = 0; j < ships.get(index).getOccupiedSquares().size(); j++) {
					for (int k = 0; k < ships.get(i).getOccupiedSquares().size(); k++) {
						if (ships.get(index).getOccupiedSquares().get(j).getRow() + y == ships.get(i).getOccupiedSquares().get(k).getRow() && (char)((int)ships.get(index).getOccupiedSquares().get(j).getColumn() + x) == ships.get(i).getOccupiedSquares().get(k).getColumn()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private Result attack(List<Ship> shipList, Square s) {

		var shipsAtLocation = shipList.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());

    if (shipsAtLocation.size() == 0) {
			var attackResult = new Result(s);
			return attackResult;
		}

		var hitShip = shipsAtLocation.get(0);
		var attackResult = hitShip.attack(s.getRow(), s.getColumn());

		if (attackResult.getResult() == AtackStatus.SUNK) {
			for ( Square shipSquare : hitShip.getOccupiedSquares() ) {
				boolean squareFound = false;
				for ( Result eachAttack : this.attacks ) {
					if ( eachAttack.getLocation() == shipSquare ) {
						eachAttack.setResult(AtackStatus.SUNK);
						squareFound = true;
					}
				}
				if ( !squareFound ) {
					Result missingResult = new Result(shipSquare);
					missingResult.setResult(AtackStatus.SUNK);
					this.attacks.add(missingResult);
				}
			}

			if ( sinkCount == 0 ) {
				this.sonars++;
			}

			this.sinkCount++;
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
