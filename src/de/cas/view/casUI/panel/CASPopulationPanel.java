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
import de.cas.model.Automaton;
import de.cas.model.Cell;
import de.cas.model.PopulationModel;
import de.cas.view.casUI.util.RectangleFactory;

public class CASPopulationPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 5065284549023365460L;

	IAutomatonController controller;
	Graphics2D g2d = null;

	public CASPopulationPanel(IAutomatonController controller){
		this.controller = controller;
		
		PaintCellsListener pcl = new PaintCellsListener(this.controller);
		this.addMouseListener(pcl);
		this.addMouseMotionListener(pcl);
		
		this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
        
	    update(null, this);
	}

	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		//long startTime = System.currentTimeMillis();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g2d.setRenderingHints(rh);

        Dimension size = this.getParent().getSize();
        PopulationModel pm = this.controller.getPopulationModel();
        Automaton am = this.controller.getAutomatonModel();
        int colsVisual = (int)size.getWidth()/pm.getCellSize(); 
        int rowsVisual = (int)size.getHeight()/pm.getCellSize(); 
        colsVisual = (colsVisual<am.getNumberOfColumns())?colsVisual:am.getNumberOfColumns();
        rowsVisual = (rowsVisual<am.getNumberOfRows())?rowsVisual:am.getNumberOfRows();
		
        for (int y = 0; y < rowsVisual; y++){
			for (int x = 0; x < colsVisual; x++){
				Color c = am.getStates().getColorMapping()[am.getPopulation()[y][x].getState()];
				RectangleFactory.getRectangle(c).draw(g2d, pm, x, y, pm.getCellSize(), pm.getMargin());
			}
		}
        //System.out.println((System.currentTimeMillis() - startTime)+" ms");
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
	
	@Override
	public boolean isOptimizedDrawingEnabled(){
		return true;
	}
}
