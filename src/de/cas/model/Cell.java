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
	
	
	//Dibo fragen, ob es hier einen besseren Trick gibt...sieht sehr ugly aus

	public interface CellIteratorPara0{
        Cell apply();
    }
    public static void iterator(Cell[][] cells, CellIteratorPara0 ci) { 
        for(int y = 0; y < cells.length; y++)
            for (int x = 0; x < cells[y].length; x++)
               cells[y][x] = ci.apply();
    }
    
	public interface CellIteratorPara1{
		void apply(Cell cell);
    }
    public static void iterator(Cell[][] cells, CellIteratorPara1 ci) { 
        for(int y = 0; y < cells.length; y++)
            for (int x = 0; x < cells[y].length; x++)
               ci.apply(cells[y][x]);
    }
    
	public interface CellIteratorPara3{
        Cell apply(Cell cell, int y, int x);
    }
    public static void iterator(Cell[][] cells, CellIteratorPara3 ci) { 
        for(int y = 0; y < cells.length; y++)
            for (int x = 0; x < cells[y].length; x++)
               cells[y][x] = ci.apply(cells[y][x], y, x);
    }
}
