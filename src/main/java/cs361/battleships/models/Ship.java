package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;
import org.apache.commons.lang.SystemUtils;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Ship {

	@JsonProperty private String kind;
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int size;
	@JsonProperty protected int cq;
	@JsonProperty protected int cqArmor;

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this();
		this.kind = kind;
		switch(kind) {
			case "MINESWEEPER":
				this.size = 2;
				this.cq = 0;
				this.cqArmor = 0;
				break;
			case "DESTROYER":
				this.size = 3;
				this.cq = 1;
				this.cqArmor = 1;
				break;
			case "BATTLESHIP":
				this.size = 4;
				this.cq = 2;
				this.cqArmor = 1;
				break;
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void place(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
			}
		}
	}

	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}

	public String getKind() {
		return kind;
	}

	public Result attack(Square s) {
		return attack( s.getRow(), s.getColumn() );
	}

	public Result attack(int x, char y) {
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		var result = new Result(attackedLocation);

		// Check if square exists
		if (!square.isPresent()) { return result; }

		var attackedSquare = square.get();

		if (attackedSquare.isHit()) {
			result.setResult(AtackStatus.INVALID);
			return result;
		}

		result.setShip(this);

		// Check if square is ship's CQ
		if ( isCq(attackedSquare) ) {
			if ( this.cqArmor > 0 ) {
				this.cqArmor--;
				result.setResult(AtackStatus.MISS);
			} else if ( this.cqArmor == 0 ) {
				attackedSquare.hit();
				result.setResult(AtackStatus.SUNK);
			}
		} else {
			attackedSquare.hit();
			if (isSunk()) {
				result.setResult(AtackStatus.SUNK);
			} else {
				result.setResult(AtackStatus.HIT);
			}
		}

		return result;
	}

	public boolean isCq(Square s) {
		return this.occupiedSquares.get(this.cq).equals(s);
	}

	@JsonIgnore
	public boolean isSunk() {
		return getOccupiedSquares().stream().allMatch(s -> s.isHit());
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		var otherShip = (Ship) other;

		return this.kind.equals(otherShip.kind)
				&& this.size == otherShip.size
				&& this.occupiedSquares.equals(otherShip.occupiedSquares);
	}

	@Override
	public int hashCode() {
		return 33 * kind.hashCode() + 23 * size + 17 * occupiedSquares.hashCode();
	}

	@Override
	public String toString() {
		return kind + occupiedSquares.toString();
	}


	//public int getCqArmor() {return this.CQ_Armor;}
	//public void hitCq() {this.CQ_Armor--;}
	//public boolean getSunkCheck() {return this.SunkCheck;}
	//public void setSunkCheck(boolean sinkIt) {this.SunkCheck = sinkIt;}
}
