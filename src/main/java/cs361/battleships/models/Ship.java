package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	protected String kind;
	protected int shipLength;
	protected boolean sunk;


	public Ship() {
		occupiedSquares = new ArrayList<Square>();
		kind = new String();
	}

	public Ship(String kind) {
		this.kind = kind;
		this.occupiedSquares = new ArrayList<Square>();
		this.sunk = false;

		if ( kind.equals( "MINESWEEPER" ) ){
			this.shipLength = 2;
		}
		else if( kind.equals( "DESTROYER" )){
			this.shipLength = 3;
		}
		else if( kind.equals( "BATTLESHIP" )){
			this.shipLength = 4;
		}
	}

	public String getKind(){
		return kind;
	}
	public int getShipLength(){
		return this.shipLength;
	}
    public boolean getSunk(){
	    return this.sunk;
    }

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(List<Square> occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

}