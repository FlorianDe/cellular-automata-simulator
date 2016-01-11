package de.cas.util;

public class Settings {
	public static final String ATR_NAME = "name";
	public static final String ATR_X = "x";
	public static final String ATR_Y = "y";
	public static final String ATR_WIDTH = "width";
	public static final String ATR_HEIGHT = "height";
	public static final String ATR_CELLSIZE = "cellsize";
	public static final String ATR_DELAY = "delay";
	
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int cellSize;
	private int delay;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getCellSize() {
		return cellSize;
	}
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public Settings(){
		
	}
	public Settings(String name, int x, int y, int width, int height, int cellSize, int delay){
		this.name=name;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.cellSize=cellSize;
		this.delay=delay;
	}
}
