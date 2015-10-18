import java.util.Scanner;

import de.cas.model.Automaton;
import de.cas.model.Cell;

public class GameOfLifeTest extends Automaton {
	private final static int numberOfRows = 20;
	private final static int numberOfColumns = 20;
	private final static int numberOfStates = 2;
	private final static boolean isMooreNeighborHood = true;
	private final static boolean isTorus = true;
	
	
	public static void main(String[] args){
		GameOfLifeTest golt = new GameOfLifeTest();
		golt.randomPopulation();
		while(true){
			golt.printPopulation();
			golt.calcNextGeneration();
			new Scanner(System.in).nextLine();
		}
	}
	
	public GameOfLifeTest(){
		super(numberOfRows,numberOfColumns,numberOfStates,isMooreNeighborHood,isTorus);
	}
	
	public void printPopulation(){
		for(int y=0; y < this.getNumberOfRows(); y++){
            for(int x=0; x < this.getNumberOfRows(); x++) {
            	int state = this.getCell(y, x).getState();
            	if(state==0)
            		System.out.print("| ");
            	else if(state==1)
            		System.out.print("|■");
            }
            System.out.print("|\n");
        }
	}
	
	@Override
	protected Cell transform(Cell cell, Cell[] neighbors) {
		int count = countLivingNeighbors(neighbors);
		if (cell.getState() == 0)
			return (count==3)?new Cell(1):new Cell(0);
		return (count < 2 || count > 3)? new Cell(0): new Cell(1);
	}

	private int countLivingNeighbors(Cell[] neighbors) {
		int count = 0;
		for (Cell cell : neighbors)
			if (cell.getState() == 1)
				count++;
		return count;
	}
}
