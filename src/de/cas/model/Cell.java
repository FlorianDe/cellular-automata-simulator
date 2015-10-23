package de.cas.model;

public class Cell {
	public static final int INIT_STATE = 0;
	
	private int state;

	public Cell() {
		this(INIT_STATE);
	}

	public Cell(int state) {
		this.state = state;
	}

	public Cell(Cell cell) {
		this.state = cell.state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return state+"";
	}
	
	@Override
	public Cell clone() {
		return new Cell(this.state);
	}

}
