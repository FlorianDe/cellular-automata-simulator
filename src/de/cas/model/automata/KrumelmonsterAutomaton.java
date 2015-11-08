package de.cas.model.automata;

import de.cas.model.Automaton;
import de.cas.model.Cell;

public class KrumelmonsterAutomaton extends Automaton {

	private final static int numberOfRows = 80;
	private final static int numberOfColumns = 80;
	private final static int numberOfStates = 8;
	private final static boolean isMooreNeighborHood = false;
	private final static boolean isTorus = true;


	public KrumelmonsterAutomaton() {
		super(numberOfRows,numberOfColumns,numberOfStates,isMooreNeighborHood,isTorus);
	}
	
	protected Cell transform(Cell cell, Cell[] neighbors) {
		int cellState = cell.getState();
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i].getState() == (cellState + 1) % numberOfStates) {
				cell.setState((cellState + 1) % numberOfStates);
				return cell;
			}
		}
		return cell;
	}
	
	public void GenKruemmel(){
		int counter = 0;
		for (int y = 0; y < this.getNumberOfRows(); y++) {
			for (int x = 0; x < this.getNumberOfColumns(); x++) {
				this.setState(y, x, counter);
				counter = (counter+1)%this.getStates().getNumberOfStates();
			}
		}
	}

}
