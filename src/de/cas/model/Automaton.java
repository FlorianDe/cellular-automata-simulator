package de.cas.model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public abstract class Automaton {

	
	protected Cell[][] population;
	
	protected int numberOfRows;
	protected int numberOfColumns;
	protected int numberOfStates;
	private boolean isTorus;
	private boolean isMooreNeighborHood;
	private Point[] directions;

	private Color[] colors;
	
	/**
	 * Konstruktor
	 *
	 * @param rows Anzahl an Reihen
	 * @param columns Anzahl an Spalten
	 * @param numberOfStates Anzahl an Zust�nden; die Zust�nde des Automaten sind dann die Werte 0 bis numberOfStates-1
	 * @param isMooreNeighborHood true, falls der Automat die Moore-Nachbarschaft benutzt; falls, falls der Automat die von-Neumann-Nachbarschaft benutzt
	 * @param isTorus true, falls die Zellen als Torus betrachtet werden
	 */
	public Automaton(int rows, int columns, int numberOfStates, boolean isMooreNeighborHood, boolean isTorus){
		this.setNumberOfRows(rows);
		this.setNumberOfColumns(columns);
		this.numberOfStates = numberOfStates;
		this.setTorus(isTorus);
		this.isMooreNeighborHood = isMooreNeighborHood;
		this.setDirections(isMooreNeighborHood);
		this.population = new Cell[rows][columns];
		for (int y = 0; y < population.length; y++) {
			for (int x = 0; x < population[y].length; x++) {
				population[y][x] = new Cell();
			}
		}
		this.defineColors();
	}
	
	/**
	 * Implementierung der Transformationsregel
	 *
	 * @param cell die betroffene Zelle (darf nicht ver�ndert werden!!!)
	 * @param neighbors die Nachbarn der betroffenen Zelle (d�rfen nicht ver�ndert werden!!!)
	 * @return eine neu erzeugte Zelle, die gem�� der Transformationsregel aus der betroffenen Zelle hervorgeht
	 */
	protected abstract Cell transform(Cell cell, Cell[] neighbors);
	
	/**
	 * Liefert die Anzahl an Zust�nden des Automaten; g�ltige Zust�nde sind
	 * int-Werte zwischen 0 und Anzahl-1
	 *
	 * @return die Anzahl an Zust�nden des Automaten
	 */
	public int getNumberOfStates(){
		return numberOfStates;
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
	
	/**
	 * �ndert die Gr��e des Automaten; Achtung: aktuelle Belegungen nicht
	 * gel�schter Zellen sollen beibehalten werden; neue Zellen sollen im
	 * Zustand 0 erzeugt werden
	 *
	 * @param rows die neue Anzahl an Reihen
	 * @param columns die neue Anzahl an Spalten
	 */
	public void setSize(int rows, int columns){
		Cell[][] oldPopulation = this.population.clone();
		this.setNumberOfRows(rows);
		this.setNumberOfColumns(columns);
		
		this.population = new Cell[this.getNumberOfRows()][this.getNumberOfColumns()];
		for (int y = 0; y < numberOfRows; y++)
			for (int x = 0; x < numberOfColumns; x++)
				population[y][x] = (y >= oldPopulation.length || x >= oldPopulation[y].length)?new Cell():oldPopulation[y][x];	
	}
	
	/**
	 * �ndert die Anzahl an Reihen des Automaten
	 *
	 * @param rows die neue Anzahl an Reihen
	 */
	public void setNumberOfRows(int rows){
		if(rows > 0){
			this.numberOfRows = rows;
		}
	}
	
	/**
	 * �ndert die Anzahl an Spalten des Automaten
	 *
	 * @param rows die neue Anzahl an Spalten
	 */
	public void setNumberOfColumns(int columns){
		if(columns > 0){
			this.numberOfColumns = columns;
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
	 * �ndert die Torus-Eigenschaft des Automaten
	 *
	 * @param isTorus true, falls der Automat als Torus betrachtet wird; falls sonst
	 */
	public void setTorus(boolean isTorus){
		this.isTorus = isTorus;
	}
	
	/**
	 * Liefert Informationen �ber die Nachbarschaft-Eigenschaft des Automaten
	 * (Hinweis: Die Nachbarschaftseigenschaft kann nicht ver�ndert werden)
	 *
	 * @return true, falls der Automat die Moore-Nachbarschaft ber�cksicht; false, falls er die von-Neumann-Nachbarschaft ber�cksichtigt
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
		return this.population;
	}
	
	/**
	 * �ndert die Population
	 *
	 * @param cells eine neue Population des Automaten
	 */
	public void setPopulation(Cell[][] cells){
		if (cells != null && cells.length > 0) {
			this.population = cells;
			this.setSize(cells.length, cells[0].length);
		}
	}
	
	/**
	 * setzt alle Zellen in den Zustand 0
	 */
	public void clearPopulation(){
		this.population = new Cell[numberOfRows][numberOfColumns];
	}
	
	/**
	 * setzt f�r jede Zelle einen zuf�llig erzeugten Zustand
	 */
	public void randomPopulation(){
		for (int y = 0; y < numberOfRows; y++)
			for (int x = 0; x < numberOfColumns; x++)
				population[y][x].setState((new Random()).nextInt(this.getNumberOfStates()));
	}
	
	/**
	 * �ndert den Zustand einer Zelle
	 *
	 * @param row Reihe der Zelle
	 * @param column Spalte der Zelle
	 * @param state neuer Zustand der Zelle
	 */
	public void setState(int row, int column, int state){
		if(isValidPosition(row, column))
			population[row][column].setState(state);
	}
	
	/**
	 * Liefert eine Zelle des Automaten
	 *
	 * @param row Reihe der Zelle
	 * @param column Spalte der Zelle
	 * @return Cell-Objekt an Position row/column
	 */
	public Cell getCell(int row, int column){
		return (isValidPosition(row, column))?population[row][column]:null;
	}
	
	/**
	 * definiert die Farbrepr�sentation der einzelnen Zust�nde; implementiert
	 * wie folgt: <br>
	 * Anzahl der Zust�nde = 2: 0 = wei�; 1 = schwarz <br>
	 * Anzahl der Zust�nde = 3: 0 = wei�; 1 = grau; 2 = schwarz <br>
	 * Ansonsten: Farbzuordnung per Zufall
	 */
	protected void defineColors(){
		colors = new Color[numberOfStates];
		colors[0] = Color.WHITE;
		if (numberOfStates == 2) {
			colors[1] = Color.BLACK;
		} else if (numberOfStates == 3) {
			colors[1] = Color.GRAY;
			colors[2] = Color.BLACK;
		} else {
			Random random = new Random();
			for (int i = 1; i < numberOfStates; i++) {
				colors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
			}
		}
	}
	
	/**
	 * Liefert die Farbrepr�sentation eines Zustandes
	 *
	 * @param state der Zustand, dessen Farbrepr�sentation geliefert werden soll
	 * @return die Farbrepr�sentation des Zustandes state
	 */
	public Color getColor(int state){
		if(0 <= state && state < numberOfStates)
			return colors[state];
		return null;
	}
	
	/**
	 * Liefert die Farbrepr�sentation alles Zust�nde des Automaten
	 *
	 * @return die Farbrepr�sentation alles Zust�nde des Automaten
	 */
	public Color[] getColorMapping(){
		return colors;
	}
	
	/**
	 * �ndert die Farbrepr�sentation eines Zustandes
	 *
	 * @param state der Zustand
	 * @param newColor die neue Farbrepr�sentation des Zustandes state
	 */
	public void changeColor(int state, Color newColor){
		if(0 <= state && state < numberOfStates)
			colors[state] = newColor;
	}
	
	/**
	 * Berechnet und liefert die n�chste Generation; ruft dabei die abstrakte
	 * Methode "transform" f�r alle Zellen auf; Hinweis: zu ber�cksichtigen sind
	 * die Nachbarschaftseigenschaft und die Torus-Eigenschaft des Automaten
	 *
	 * @return
	 */
	public Cell[][] calcNextGeneration(){
		Cell[][] oldPopulation = this.population.clone();
		for (int y = 0; y < numberOfRows; y++)
			for (int x = 0; x < numberOfColumns; x++)
				population[y][x] = transform(oldPopulation[y][x], getCellNeighbors(oldPopulation, y, x));
		return population;
	}
	
	/**
	 * Berechnet die Nachbarn der angegebenen Zelle, die Ausgabe h�ngt auch von den Parametern torus und mooreNeighborHood ab.
	 * 
	 * @param oldPopulation
	 * @param x Spalte
	 * @param y Reihe
	 * @return Zellennachbarn als eindimensionales Array
	 */
	private Cell[] getCellNeighbors(Cell[][] oldPopulation, int x, int y) {
		ArrayList<Cell> neighbors = new ArrayList<>();
		int dY,dX;
		for (Point d : this.directions) {
			dY = isTorus?(y+d.y)%numberOfRows:(y+d.y);
			dX = isTorus?(x+d.x)%numberOfColumns:(x+d.x);
			if(isValidPosition(dY, dX))
				neighbors.add(oldPopulation[dY][dX]);
		}
		return neighbors.toArray(new Cell[neighbors.size()]);
	}

	public boolean isValidPosition(int row, int column){
		return((row >= 0 && row < numberOfRows) && (column >= 0 && column < numberOfColumns));
	}
	
	/* 0/0 is the center!
	 * [-1 / -1] [-1 / 0] [-1 / 1]
	 * [ 0 / -1] [ 0 / 0] [ 0 / 1]
	 * [ 1 / -1] [ 1 / 0] [ 1 / 1]
	 */
	private void setDirections(boolean isMooreNeighborHood){
		if(isMooreNeighborHood)
			this.directions = new Point[]{new Point(-1, -1), new Point(-1, 0) , new Point(-1, 1), new Point(0, -1), new Point(0, 1), new Point(1, -1), new Point(1, 0), new Point(1, 1) };
		else
			this.directions = new Point[]{new Point(0, -1), new Point(-1, 0), new Point(0, 1), new Point(1, 0) };
	}
}