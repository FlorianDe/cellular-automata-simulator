package de.cas.view.casUI.util;

import java.awt.Color;
import java.awt.Graphics;

import de.cas.model.PopulationModel;

public class Rectangle implements Drawable{
	private Color color;

	public Rectangle(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Graphics g, PopulationModel zoomFactor, int x, int y, int cellSize, int margin) {
		g.setColor(color);
		g.fillRect(x * cellSize + margin, y * cellSize + margin, cellSize, cellSize);
		if(zoomFactor.isDrawCellRect()){
			g.setColor(Color.black);
			g.drawRect(x * cellSize + margin, y * cellSize + margin, cellSize, cellSize);
		}
	}
}
