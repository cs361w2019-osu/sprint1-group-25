package cs361.battleships.models;

public class Result {
	private Square location;

	public Result(Square s) {
		this.location = s;
	}

	public AtackStatus getResult() {
		//TODO implement
		return null;
	}

	public void setResult(AtackStatus result) {
		//TODO implement
	}

	public Ship getShip() {
		//TODO implement
		return null;
	}

	public void setShip(Ship ship) {
		//TODO implement
	}

	public Square getLocation() {
		return this.location;
	}

	public void setLocation(Square square) {
		this.location = square;
	}
}
