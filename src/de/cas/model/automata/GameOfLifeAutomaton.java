package de.cas.model.automata;

import de.cas.model.Automaton;
import de.cas.model.Cell;

public class GameOfLifeAutomaton extends Automaton {
	
	private final static int numberOfRows = 80;
	private final static int numberOfColumns = 80;
	private final static int numberOfStates = 2;
	private final static boolean isMooreNeighborHood = true;
	private final static boolean isTorus = true;
	
	public GameOfLifeAutomaton(){
		super(numberOfRows,numberOfColumns, numberOfStates, isMooreNeighborHood,isTorus);
	}
	
	@Override
	protected Cell transform(Cell cell, Cell[] neighbors) {
		int livingNeighbors = countLivingNeighbors(neighbors);
		return new Cell((cell.getState()==1 && livingNeighbors == 2 || livingNeighbors == 3)?1:0);
	}

	private int countLivingNeighbors(Cell[] neighbors) {
		int count = 0;
		for (Cell cell : neighbors)
			if(cell!=null)
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
	
	public void createGliderLU(int Y_OFF, int X_OFF){
		if(3+Y_OFF < this.getNumberOfRows() && 6+X_OFF < this.getNumberOfColumns()){
			this.getCell(2+Y_OFF, 0+X_OFF).setState(1);
			this.getCell(2+Y_OFF, 1+X_OFF).setState(1);
			this.getCell(2+Y_OFF, 2+X_OFF).setState(1);
			this.getCell(1+Y_OFF, 0+X_OFF).setState(1);
			this.getCell(0+Y_OFF, 1+X_OFF).setState(1);
		}
	}
	
	public void createGliderRU(int Y_OFF, int X_OFF){
		if(3+Y_OFF < this.getNumberOfRows() && 6+X_OFF < this.getNumberOfColumns()){
			this.getCell(2+Y_OFF, 0+X_OFF).setState(1);
			this.getCell(2+Y_OFF, 1+X_OFF).setState(1);
			this.getCell(2+Y_OFF, 2+X_OFF).setState(1);
			this.getCell(1+Y_OFF, 2+X_OFF).setState(1);
			this.getCell(0+Y_OFF, 1+X_OFF).setState(1);
		}
	}
	
	public void createGliderLO(int Y_OFF, int X_OFF){
		if(3+Y_OFF < this.getNumberOfRows() && 6+X_OFF < this.getNumberOfColumns()){
			this.getCell(0+Y_OFF, 0+X_OFF).setState(1);
			this.getCell(0+Y_OFF, 1+X_OFF).setState(1);
			this.getCell(0+Y_OFF, 2+X_OFF).setState(1);
			this.getCell(1+Y_OFF, 0+X_OFF).setState(1);
			this.getCell(2+Y_OFF, 1+X_OFF).setState(1);
		}
	}
	
	public void createGliderRO(int Y_OFF, int X_OFF){
		if(3+Y_OFF < this.getNumberOfRows() && 6+X_OFF < this.getNumberOfColumns()){
			this.getCell(0+Y_OFF, 0+X_OFF).setState(1);
			this.getCell(0+Y_OFF, 1+X_OFF).setState(1);
			this.getCell(0+Y_OFF, 2+X_OFF).setState(1);
			this.getCell(1+Y_OFF, 2+X_OFF).setState(1);
			this.getCell(2+Y_OFF, 1+X_OFF).setState(1);
		}
	}
	
	public void createTrafficRLUO_F1(){
		createTrafficRLUO(1);
	}
	public void createTrafficRLUO_F2(){
		createTrafficRLUO(2);
	}
	public void createTrafficRLUO_F3(){
		createTrafficRLUO(3);
	}
	public void createTrafficRLUO_F4(){
		createTrafficRLUO(4);
	}
	
	public void createTrafficRLUO(int scaleF){
		this.setSize(140*scaleF, 140*scaleF);
		int size =7;
		
		for (int y = 0; y < this.getNumberOfRows(); y=y+size*4) {
			for (int x = 0; x < this.getNumberOfColumns(); x=x+size*4) {
				this.createGliderRU(y, x);
				this.createGliderLU(y+size*2, x+size*3);
				this.createGliderRO(y+size*2, x+size*5);
				this.createGliderLO(y+size*5, x+size*2);	
			}
		}
	}
}
