package de.cas.view.casUI.util;

import java.awt.Graphics2D;

import de.cas.model.PopulationModel;

interface IDrawable{
    public void draw(Graphics2D g, PopulationModel zoomFactor, int x, int y, int cellSize, int margin);
}