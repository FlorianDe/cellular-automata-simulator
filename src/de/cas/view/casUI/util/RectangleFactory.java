package de.cas.view.casUI.util;

import java.awt.Color;
import java.util.HashMap;

public class RectangleFactory {
	private static final HashMap<Color, Rectangle> rectanglesByColor = new HashMap<Color, Rectangle>();
	
	public static Rectangle getRectangle(Color color){
		Rectangle rect = rectanglesByColor.get(color);
		if(rect == null) { 
			rect = new Rectangle(color);
			rectanglesByColor.put(color, rect);
		}
		return rect;
	}
}
