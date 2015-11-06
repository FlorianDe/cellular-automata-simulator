package de.cas.view.casUI.util;

import java.awt.Color;
import java.awt.Graphics;

import de.cas.model.PopulationModel;

public class Rectangle {
	private Color color;

	public Rectangle(Color color) {
		this.color = color;
	}

	public void draw(PopulationModel zoomFactor, Graphics g, int x, int y, int cellSize, int margin) {
		g.setColor(color);
		g.fillRect(x * cellSize + margin, y * cellSize + margin, cellSize, cellSize);
		if(zoomFactor.isDrawCellRect()){
			g.setColor(Color.black);
			g.drawRect(x * cellSize + margin, y * cellSize + margin, cellSize, cellSize);
		}
	}
}
