package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	private int row;
	private char column;

	private boolean attackStat;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.attackStat = false;
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public  boolean getAttackStat() {
		return attackStat;
	}

	public void setAttackStat() {
		this.attackStat = true;
	}

}