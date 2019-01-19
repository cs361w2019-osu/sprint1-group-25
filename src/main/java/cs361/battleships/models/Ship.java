package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;

	public Ship() {
		occupiedSquares = new ArrayList<>();
		kind = new String();
	}

	public Ship(String kind) {
		//TODO implement
		this.kind = kind;
	}

	public List<Square> getOccupiedSquares() {
		//TODO implement
		return occupiedSquares;
	}
}