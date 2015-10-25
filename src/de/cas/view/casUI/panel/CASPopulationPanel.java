package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.cas.model.Automaton;
import de.cas.model.Cell;

public class CASPopulationPanel extends JPanel {

	private static final long serialVersionUID = 5065284549023365460L;

	private Automaton automaton;
	private final int margin = 10;
	private int cellSize;

	
	public CASPopulationPanel(Automaton automaton){
		this.automaton = automaton;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		cellSize = 4; //*zoomFactor...dies das...

		for (int y = 0; y < automaton.getNumberOfRows(); y++) {
			for (int x = 0; x < automaton.getNumberOfColumns(); x++) {
				g.setColor(automaton.getColorMapping()[automaton.getPopulation()[y][x].getState()]);
				g.fillRect(x * cellSize + margin, y * cellSize + margin, cellSize, cellSize);
				g.setColor(Color.black);
				g.drawRect(x * cellSize + margin, y * cellSize + margin, cellSize, cellSize);
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((2 * margin) + (automaton.getNumberOfColumns() * cellSize), (2 * margin) + (automaton.getNumberOfRows() * cellSize));
	}

}
