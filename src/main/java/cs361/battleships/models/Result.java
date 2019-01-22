package cs361.battleships.models;

public class Result {
	private AtackStatus result;
	private Square location;
	private Ship ship;

	public Result(Square s) {
		this.location = s;
	}

	public AtackStatus getResult() {
		return result;
	}

	public void setResult(AtackStatus result) {
		this.result = result;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return this.location;
	}

	public void setLocation(Square square) {
		this.location = square;
	}
}
