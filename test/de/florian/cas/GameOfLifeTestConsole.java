package de.florian.cas;

import de.cas.model.Automaton;
import de.cas.model.automata.GameOfLifeAutomaton;

public class GameOfLifeTestConsole {
	public static void main(String[] args) throws InterruptedException{
		GameOfLifeAutomaton golt = new GameOfLifeAutomaton(10,10,2,false,true);
		//golt.randomPopulation();
		//golt.createGliderGun();
		golt.setState(2, 3, 1);
		golt.setState(2, 4, 1);
		golt.setState(2, 5, 1);
		
		int steps = 100;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < steps; i++) {
			System.out.println(stringRepresentation(golt));
			golt.calcNextGeneration();
			Thread.sleep(100);
		}
		long ms = (System.currentTimeMillis() - startTime);
        System.out.println(ms+" ms for " + steps + " steps!");
        System.out.println("Real FPS:" + (steps*1000.0)/ms);
	}
	
	public static String stringRepresentation(Automaton automat){
		StringBuilder sb = new StringBuilder();
		for(int y=0; y < automat.getNumberOfRows(); y++){
            for(int x=0; x < automat.getNumberOfColumns(); x++) {
            	int state = automat.getCell(y, x).getState();
            	if(state==0)
            		sb.append("| ");
            	else if(state==1)
            		sb.append("|\u25A0");
            }
            sb.append("|\n");
        }
		return sb.toString();
	}
}


//FELD 20x20
//2FORS: FOR ~10000CALCS (796ms|744ms|750ms|730ms|766ms|761ms|763ms)
//ArrayCopy: FOR ~10000CALCS (723ms|709ms|703ms|698ms|727ms|703ms|705ms|712ms)
//AFTER PERF BOOST:
//2FORS: FOR ~10000CALCS (426 ms | 439|  440 ms)
//ArrayCopy: FOR ~10000CALCS (447ms || 462 ms || 444ms)

//FELD 200x200
//2FORS: FOR ~10000CALCS (70843 ms) // int[][] (73848ms) //int[][]  65192 ms
//ArrayCopy: FOR ~10000CALCS (86062 ms) //int[][] 69664 ms // 37456 ms

//>60FPS bei Feldern<400x400
