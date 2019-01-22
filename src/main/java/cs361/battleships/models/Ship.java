package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;

	public Ship() {
		occupiedSquares = new ArrayList<Square>();
		kind = new String();
	}

	public Ship(String kind) {
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(List<Square> occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

}