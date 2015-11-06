package de.cas.model;

import java.awt.Point;
import java.util.Observable;

public class PopulationModel extends Observable {
	
	public final static int MARGIN_DEFAULT = 10;

	public final static int CELL_SIZE_MIN = 1;
	public final static int CELL_SIZE_DEFAULT = 10;
	public final static int CELL_SIZE_MAX = 20;
	public final static int CELL_SIZE_DRAW_RECT_THRESHOLD = 5;

	private boolean drawCellRect;
	private int cellSize;
	private int margin;


	public PopulationModel(){
		this.setCellSize(CELL_SIZE_DEFAULT);
		this.margin = MARGIN_DEFAULT;
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
	
	public int getCellSize() {
		return cellSize;
	}
	public void setCellSize(int value) {
		if (value >= CELL_SIZE_MIN && value <= CELL_SIZE_MAX){
			cellSize = value;
			this.drawCellRect = (value >= CELL_SIZE_DRAW_RECT_THRESHOLD);
			notify(null);
		}
	}
	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}
	

	public void zoomIn() {
		this.setCellSize(this.getCellSize()+1);
	}

	public void zoomOut() {
		this.setCellSize(this.getCellSize()-1);
	}
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}
