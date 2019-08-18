package de.cas.model;

import java.awt.Point;

import de.cas.util.CstmObservable;

public class PopulationModel extends CstmObservable {
	
	public final static int MARGIN_DEFAULT = 10;

	public final static int CELL_SIZE_MIN = 1;
	public final static int CELL_SIZE_DEFAULT = 6;
	public final static int CELL_SIZE_MAX = 20;
	//public final static int CELL_SIZE_DRAW_RECT_THRESHOLD = 5;

	private volatile boolean drawCellRect;
	private int cellSize;
	private int margin;


	public PopulationModel(){
		this.setCellSize(CELL_SIZE_DEFAULT);
		this.margin = MARGIN_DEFAULT;
		this.drawCellRect = true;
	}
	public PopulationModel(int cellSize){
		this();
		this.setCellSize(cellSize);
	}
	
	public Point coordinatesToCell(int y, int x){
		return new Point(coordinatesToCellHelper(x), coordinatesToCellHelper(y));
	}
	
	private int coordinatesToCellHelper(int value){
		int t = (value-this.margin)/(this.cellSize);
		return (t<0)?0:t;
	}
	
	public boolean isDrawCellRect() {
		return drawCellRect;
	}
	
	public void setDrawCellRect(boolean drawCellRect) {
		this.drawCellRect = drawCellRect;
		notify(null);
	}
	public boolean isMinimum(){
		return this.getCellSize()<=CELL_SIZE_MIN;
	}
	public int getCellSize() {
		return cellSize;
	}
	public synchronized void setCellSize(int value) {
		if (value >= CELL_SIZE_MIN && value <= CELL_SIZE_MAX){
			cellSize = value;
			this.setDrawCellRect(value > CELL_SIZE_MIN);
		}
	}
	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}
	

	public synchronized void zoomIn() {
		this.setCellSize(this.getCellSize()+1);
	}

	public synchronized void zoomOut() {
		this.setCellSize(this.getCellSize()-1);
	}
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}
