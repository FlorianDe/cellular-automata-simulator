package de.cas.model;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlRootElement;

import de.cas.util.ACallable;
import de.cas.util.CstmObservable;

@XmlRootElement
public abstract class Automaton extends CstmObservable{
	private static volatile ConcurrentHashMap<Automaton,Integer> runningAutomatons;
	private static volatile int totalAutomatonsInstantiated;
	
	private StateModel states;
	private volatile Cell[][] population;
	private int numberOfRows;
	private int numberOfColumns;
	private volatile boolean isTorus;
	private volatile boolean isMooreNeighborHood;
	
	private static final int[][] directionsMoore = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };
	private static final int[][] directionsNeumann = {{0, -1}, {-1, 0}, {0, 1}, {1, 0} };
	private int[][] directions;

	static{
		runningAutomatons = new ConcurrentHashMap<>();
		totalAutomatonsInstantiated = 0;
	}
	
	public Automaton(){}
	
	/**
	 * Konstruktor
	 *
	 * @param rows Anzahl an Reihen
	 * @param columns Anzahl an Spalten
	 * @param numberOfStates Anzahl an Zuständen; die Zustände des Automaten sind dann die Werte 0 bis numberOfStates-1
	 * @param isMooreNeighborHood true, falls der Automat die Moore-Nachbarschaft benutzt; falls, falls der Automat die von-Neumann-Nachbarschaft benutzt
	 * @param isTorus true, falls die Zellen als Torus betrachtet werden
	 */
	public Automaton(int rows, int columns, int numberOfStates, boolean isMooreNeighborHood, boolean isTorus){
		this.population = new Cell[rows][columns];
		this.setSize(rows, columns);
		this.setTorus(isTorus);
		this.clearPopulation();
		this.states = new StateModel(numberOfStates);
		
		this.isMooreNeighborHood = isMooreNeighborHood;
		//this.neighbors = isMooreNeighborHood?new Cell[8]:new Cell[4];
		this.directions = isMooreNeighborHood?directionsMoore:directionsNeumann;
		Automaton.putRunningAutomaton(this);
	}
	
	/**
	 * Implementierung der Transformationsregel
	 *
	 * @param cell die betroffene Zelle (darf nicht verändert werden!!!)
	 * @param neighbors die Nachbarn der betroffenen Zelle (dürfen nicht verändert werden!!!) </br>
	 * Neighbor indices: </br>
	 * 0 1 2 </br>
	 * 3 X 4 </br>
	 * 5 6 7 </br>
	 *
	 * @return eine neu erzeugte Zelle, die gemäß der Transformationsregel aus der betroffenen Zelle hervorgeht
	 */
	protected abstract Cell transform(Cell cell, Cell[] neighbors);
	
	public StateModel getStates() {
		return states;
	}

	public void setStates(StateModel states) {
		this.states = states;
	}
	
	/**
	 * Liefert die Anzahl an Reihen
	 *
	 * @return die Anzahl an Reihen
	 */
	public int getNumberOfRows(){
		return numberOfRows;
	}
	
	/**
	 * Liefert die Anzahl an Spalten
	 *
	 * @return die Anzahl an Spalten
	 */
	public int getNumberOfColumns(){
		return numberOfColumns;
	}
	
	private void setSizeHelper(){
		if(population!=null){
			final Cell[][] oldPopulation = this.population.clone();
			this.population = new Cell[numberOfRows][numberOfColumns];
			Cell.iterator(this.population, (cell, y, x) -> (y >= oldPopulation.length || x >= oldPopulation[y].length)? new Cell() : oldPopulation[y][x]);
		}
		notify(null);
	}
	
	/**
	 * Ändert die Größe des Automaten; Achtung: aktuelle Belegungen nicht
	 * gelöschter Zellen sollen beibehalten werden; neue Zellen sollen im
	 * Zustand 0 erzeugt werden
	 *
	 * @param rows die neue Anzahl an Reihen
	 * @param columns die neue Anzahl an Spalten
	 */
	public void setSize(int rows, int columns){
		synchronized(this.population){
			if(rows > 0)
				this.numberOfRows = rows;
			if(columns > 0)
				this.numberOfColumns = columns;
			setSizeHelper();
		}
	}
	
	/**
	 * Ändert die Anzahl an Reihen des Automaten
	 *
	 * @param rows die neue Anzahl an Reihen
	 */
	public void setNumberOfRows(int rows){
		synchronized(this.population){
			setSize(rows, numberOfColumns);
		}
	}
	
	/**
	 * Ändert die Anzahl an Spalten des Automaten
	 *
	 * @param columns die neue Anzahl an Spalten
	 */
	public void setNumberOfColumns(int columns){
		synchronized(this.population){
			setSize(numberOfRows, columns);
		}
	}
	
	/**
	 * Liefert Informationen, ob der Automat als Torus betrachtet wird
	 *
	 * @return true, falls der Automat als Torus betrachtet wird; falls sonst
	 */
	public boolean isTorus(){
		return this.isTorus;
	}
	
	/**
	 * Ändert die Torus-Eigenschaft des Automaten
	 *
	 * @param isTorus true, falls der Automat als Torus betrachtet wird; falls sonst
	 */
	public void setTorus(boolean isTorus){
		this.isTorus = isTorus;
	}
	
	/**
	 * Liefert Informationen über die Nachbarschaft-Eigenschaft des Automaten
	 * (Hinweis: Die Nachbarschaftseigenschaft kann nicht verändert werden)
	 *
	 * @return true, falls der Automat die Moore-Nachbarschaft berücksicht; false, falls er die von-Neumann-Nachbarschaft berücksichtigt
	 */
	public boolean isMooreNeighborHood(){
		return this.isMooreNeighborHood;
	}
	
	/**
	 * Liefert die Population als Cell-Matrix
	 *
	 * @return die Population als Cell-Matrix
	 */
	public Cell[][] getPopulation(){
		//synchronized(this.population){
			return this.population;
		//}
	}
	
	/**
	 * Ändert die Population
	 *
	 * @param cells eine neue Population des Automaten
	 */
	public void setPopulation(Cell[][] cells){
		synchronized(this.population){
			if (cells != null && cells.length > 0) {
				this.population = cells;
				this.setSize(cells.length, cells[0].length);
			}
		}
	}
	
	/**
	 * setzt alle Zellen in den Zustand 0
	 */
	public void clearPopulation(){
		synchronized(this.population){
			Cell.iterator(this.population, () -> new Cell());
		}
		notify(null);
		
	}
	
	/**
	 * setzt für jede Zelle einen zufällig erzeugten Zustand
	 */
	public void randomPopulation(){
		synchronized(this.population){
			Cell.iterator(this.population, (cell)->cell.setState((new Random()).nextInt(this.states.getNumberOfStates())));
		}
		notify(null);
	}
	
	/**
	 * Ändert den Zustand einer Zelle
	 *
	 * @param row Reihe der Zelle
	 * @param column Spalte der Zelle
	 * @param state neuer Zustand der Zelle
	 */
	public void setState(int row, int column, int state){
		synchronized(this.population){
			if(isValidPosition(row, column) && states.isValidState(state)){
				population[row][column].setState(state);
				notify(null);
			}
		}
	}
	
	public void setSizeAndStates(Automaton automaton){
		synchronized(this.population){
			Cell.iterator(this.population, (cell, y, x) -> (y >= automaton.getNumberOfRows() || x >= automaton.getNumberOfColumns() || automaton.getCell(y, x).getState()>this.getStates().getNumberOfStates())? new Cell() : automaton.getCell(y, x));
		}
		notify(null);
	}
	
	/**
	 * Liefert eine Zelle des Automaten
	 *
	 * @param row Reihe der Zelle
	 * @param column Spalte der Zelle
	 * @return Cell-Objekt an Position row/column
	 */
	public Cell getCell(int row, int column){
		synchronized(this.population){
			return (isValidPosition(row, column))?population[row][column]:null;
		}
	}
		
	/**
	 * Berechnet und liefert die nächste Generation; ruft dabei die abstrakte
	 * Methode "transform" für alle Zellen auf; Hinweis: zu berücksichtigen sind
	 * die Nachbarschaftseigenschaft und die Torus-Eigenschaft des Automaten
	 *
	 * @return
	 */
	
	public Cell[][] calcNextGenerationParallel(){
		synchronized(this.population){
			//long startTime = System.currentTimeMillis();
			ExecutorService taskExecutor = Executors.newFixedThreadPool(Math.min(Runtime.getRuntime().availableProcessors() + 1, this.numberOfRows));
			final Cell[][] populationCopy = clonePopulation();

			for (int y = 0; y < population.length; y++) {
				final int actY = y;
				taskExecutor.execute(new Thread() {
					@Override
					public void run() {
						//System.out.println("Y:"+actY);
						for (int x = 0; x < population[actY].length; x++) {
							final Cell[][] populationCopyCopy = clonePopulation();
							population[actY][x] = transform(populationCopy[actY][x], getCellNeighbors(populationCopyCopy, actY, x));
							//System.out.print("["+actY+"]"+"X:"+x);
						}
					}
				});
			}
			
			taskExecutor.shutdown();
			
			try {
				taskExecutor.awaitTermination(10, TimeUnit.SECONDS);
				if(!taskExecutor.isTerminated())
					throw new InterruptedException("Timeout!");
			} catch (InterruptedException ex) {
				Logger.getLogger(new Throwable().getStackTrace()[0].getClassName()).log(Level.SEVERE, null, ex);
			}
			
			
	        //System.out.println("Recalc: "+(System.currentTimeMillis() - startTime)+" ms");
			notify(null);
			return population;
		}
	}
	
	public Cell[][] calcNextGeneration(){
		synchronized(this.population){
			//long startTime = System.currentTimeMillis();
			final Cell[][] populationCopy = clonePopulation();
			Cell.iterator(this.population, (cell, y, x) -> transform(population[y][x], getCellNeighbors(populationCopy, y, x)));	
			
	        //System.out.println("Recalc: "+(System.currentTimeMillis() - startTime)+" ms");
			notify(null);
			return population;
		}
	}
	
	/**
	 * Berechnet die Nachbarn der angegebenen Zelle, die Ausgabe hängt auch von den Parametern torus und mooreNeighborHood ab.
	 * 
	 * @param oldPopulation
	 * @param x Spalte
	 * @param y Reihe
	 * @return Zellennachbarn als eindimensionales Array
	 */
	private Cell[] getCellNeighbors(Cell[][] oldPopulation, int y, int x) {
		final Cell[] neighbors = isMooreNeighborHood ? new Cell[8] : new Cell[4];
		int dY,dX,ctr = 0;
		for (int[] d : directions) {
			dY = isTorus()?(y+d[0]+numberOfRows)%numberOfRows:(y+d[0]);
			dX = isTorus()?(x+d[1]+numberOfColumns)%numberOfColumns:(x+d[1]);
			neighbors[ctr++] = (isValidPosition(dY, dX) ? oldPopulation[dY][dX].clone() : new Cell(-1));
		}
		return neighbors;
	}

	public boolean isValidPosition(int row, int column){
		return((row >= 0 && row < this.numberOfRows) && (column >= 0 && column < this.numberOfColumns));
	}
	
	
	//Im "Benchmark" schneller als System.arraycopy(...)
	private Cell[][] clonePopulation() {
		Cell[][] populationCopy = new Cell[numberOfRows][numberOfColumns];
		Cell.iterator(populationCopy, (cell, y, x) -> this.population[y][x].clone());
		return populationCopy;
	}
	
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
	
	public static void notifyAllAutomatonObservers(){
		for (Automaton automaton : runningAutomatons.keySet()) {
			automaton.notify(null);
		}
	}
	
	public ArrayList<Method> getParameterlessMethods(){
		ArrayList<Method> rMethods = new ArrayList<>();
		for (Method method : this.getClass().getDeclaredMethods()) {
			if(method.getParameterTypes().length==0 && 
					method.getAnnotation(ACallable.class) != null && 
					Modifier.isPublic(method.getModifiers()) &&
					!Modifier.isAbstract(method.getModifiers())){
				rMethods.add(method);
			}
		}
		return rMethods;
	}


	public static ConcurrentHashMap<Automaton, Integer> getRunningAutomatons() {
		return runningAutomatons;
	}
	public static int getNextRunningCount() {
		return totalAutomatonsInstantiated++;	
	}
	public static synchronized void putRunningAutomaton(Automaton automaton) {
		Automaton.getRunningAutomatons().put(automaton, Automaton.getNextRunningCount());
		Automaton.notifyAllAutomatonObservers();
	}
	public static synchronized Integer removeRunningAutomaton(Automaton automaton){
		Integer mappedValue = Automaton.getRunningAutomatons().remove(automaton);
		Automaton.notifyAllAutomatonObservers();
		return mappedValue;
	}
}