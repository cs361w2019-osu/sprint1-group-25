package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

	@JsonProperty private AtackStatus result;
	@JsonProperty private Square location;
	@JsonProperty private Ship ship;
	@JsonProperty private boolean submerged;
	@SuppressWarnings("unused")

	public Result() {
	}

	public Result(Square location) {
		this.result = AtackStatus.MISS;
		this.location = location;
		this.submerged = false;
	}

	public AtackStatus getResult() {
		return this.result;
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

	public boolean getSubmerged() {
		return this.submerged;
	}

	public void setSubmerged(boolean submerged) {
		this.submerged = submerged;
	}
}
