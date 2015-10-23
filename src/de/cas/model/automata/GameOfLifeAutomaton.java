package de.cas.model.automata;

import de.cas.model.Automaton;
import de.cas.model.Cell;

public class GameOfLifeAutomaton extends Automaton {
	
	public GameOfLifeAutomaton(){
		super(100,100,2,true,true);
	}
	public GameOfLifeAutomaton(int numberOfRows, int numberOfColumns, int numberOfStates, boolean isMooreNeighborHood, boolean isTorus){
		super(numberOfRows,numberOfColumns,numberOfStates,isMooreNeighborHood,isTorus);
	}
	
	@Override
	protected Cell transform(Cell cell, Cell[] neighbors) {
		int livingNeighbors = countLivingNeighbors(neighbors);
		return new Cell((cell.getState()==1 && livingNeighbors == 2 || livingNeighbors == 3)?1:0);
	}

	private int countLivingNeighbors(Cell[] neighbors) {
		int count = 0;
		for (Cell cell : neighbors)
			if (cell.getState() == 1)
				count++;
		return count;
	}

	
	
	
	
	//Methods for creating several objects
	public void createGliderGun(){
		int OFF_Y = -1;
		int OFF_X = -1;
		this.setSize(11,38);
		
		this.getCell(6+OFF_Y, 2+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 2+OFF_X).setState(1);
		this.getCell(6+OFF_Y, 3+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 3+OFF_X).setState(1);
		
		
		this.getCell(6+OFF_Y, 12+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 12+OFF_X).setState(1);
		this.getCell(8+OFF_Y, 12+OFF_X).setState(1);
		this.getCell(9+OFF_Y, 13+OFF_X).setState(1);
		this.getCell(10+OFF_Y, 14+OFF_X).setState(1);
		this.getCell(10+OFF_Y, 15+OFF_X).setState(1);
		this.getCell(9+OFF_Y, 17+OFF_X).setState(1);
		this.getCell(8+OFF_Y, 18+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 19+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 18+OFF_X).setState(1);
		this.getCell(6+OFF_Y, 18+OFF_X).setState(1);
		this.getCell(5+OFF_Y, 17+OFF_X).setState(1);
		this.getCell(4+OFF_Y, 15+OFF_X).setState(1);
		this.getCell(4+OFF_Y, 14+OFF_X).setState(1);
		this.getCell(5+OFF_Y, 13+OFF_X).setState(1);
		
		this.getCell(4+OFF_Y, 22+OFF_X).setState(1);
		this.getCell(5+OFF_Y, 22+OFF_X).setState(1);
		this.getCell(6+OFF_Y, 22+OFF_X).setState(1);
		this.getCell(4+OFF_Y, 23+OFF_X).setState(1);
		this.getCell(5+OFF_Y, 23+OFF_X).setState(1);
		this.getCell(6+OFF_Y, 23+OFF_X).setState(1);
		this.getCell(3+OFF_Y, 24+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 24+OFF_X).setState(1);
		this.getCell(3+OFF_Y, 26+OFF_X).setState(1);
		this.getCell(7+OFF_Y, 26+OFF_X).setState(1);
		this.getCell(2+OFF_Y, 26+OFF_X).setState(1);
		this.getCell(8+OFF_Y, 26+OFF_X).setState(1);
		
		
		this.getCell(4+OFF_Y, 36+OFF_X).setState(1);
		this.getCell(4+OFF_Y, 37+OFF_X).setState(1);
		this.getCell(5+OFF_Y, 36+OFF_X).setState(1);
		this.getCell(5+OFF_Y, 37+OFF_X).setState(1);
	}
	
	public void createObj1(int Y_OFF, int X_OFF){
		this.setSize(10,40);
		
		this.getCell(5+Y_OFF, 5+X_OFF).setState(1);
		this.getCell(5+Y_OFF, 6+X_OFF).setState(1);
		this.getCell(5+Y_OFF, 7+X_OFF).setState(1);
		this.getCell(4+Y_OFF, 7+X_OFF).setState(1);
		this.getCell(3+Y_OFF, 6+X_OFF).setState(1);
	}
}
