package de.cas.view.casUI.util;

import java.awt.Graphics;

import de.cas.model.PopulationModel;

interface Drawable{
    public void draw(Graphics g, PopulationModel zoomFactor, int x, int y, int cellSize, int margin);
}