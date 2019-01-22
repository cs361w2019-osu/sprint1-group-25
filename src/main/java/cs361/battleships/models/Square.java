package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	public int row;
	public char column;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	public int getRow() {
//		System.out.println("Get row is called");
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}