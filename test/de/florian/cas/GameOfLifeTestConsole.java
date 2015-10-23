package de.florian.cas;

import de.cas.model.automata.GameOfLife;

public class GameOfLifeTestConsole {
	public static void main(String[] args) throws InterruptedException{
		GameOfLife golt = new GameOfLife(20,20,2,true,true);
		//golt.randomPopulation();
		golt.createGliderGun();
		
		int steps = 100;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < steps; i++) {
			System.out.println(golt);
			golt.calcNextGeneration();
			Thread.sleep(100);
		}
		long ms = (System.currentTimeMillis() - startTime);
        System.out.println(ms+" ms for " + steps + " steps!");
        System.out.println("Real FPS:" + (steps*1000.0)/ms);
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
