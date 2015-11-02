package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.PaintCellsListener;
import de.cas.view.casUI.util.RectangleFactory;

public class CASPopulationPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 5065284549023365460L;

	IAutomatonController controller;
	
	public CASPopulationPanel(IAutomatonController controller){
		this.controller = controller;

		PaintCellsListener pcl = new PaintCellsListener(this.controller);
		this.addMouseListener(pcl);
		this.addMouseMotionListener(pcl);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g2d.setRenderingHints(rh);

		for (int y = 0; y < this.controller.getAutomatonModel().getNumberOfRows(); y++){
			for (int x = 0; x < this.controller.getAutomatonModel().getNumberOfColumns(); x++){
				Color c = this.controller.getAutomatonModel().getStates().getColorMapping()[this.controller.getAutomatonModel().getPopulation()[y][x].getState()];
				RectangleFactory.getRectangle(c).draw(this.controller.getPopulationModel(), g2d, x, y, controller.getPopulationModel().getCellSize(), this.controller.getPopulationModel().getMargin());
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((2 * this.controller.getPopulationModel().getMargin()) + (this.controller.getAutomatonModel().getNumberOfColumns() * controller.getPopulationModel().getCellSize()), (2 * this.controller.getPopulationModel().getMargin()) + (this.controller.getAutomatonModel().getNumberOfRows() * controller.getPopulationModel().getCellSize()));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.revalidate();
		this.repaint();
	}
}
