package de.cas.view.casUI.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.PaintCellsListener;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.CASFrame;
import de.cas.view.casUI.util.RectangleFactory;

public class CASPopulationCanvas extends Canvas implements CstmObserver {

	private static final long serialVersionUID = 5065284549023365460L;

	IAutomatonController controller;
	Graphics2D g2d = null;
	BufferStrategy bs = null;

	public CASPopulationCanvas(IAutomatonController controller){
		this.controller = controller;
		//this.bs = CASFrame.bufferStrategy;
		
		PaintCellsListener pcl = new PaintCellsListener(this.controller);
		this.addMouseListener(pcl);
		this.addMouseMotionListener(pcl);
	}

	
	
	/*
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
	
	
	protected void paintComponent() {
		//super.paintComponent(g);
		try{
			g2d = (Graphics2D)CASFrame.bufferStrategy.getDrawGraphics();
			//BufferCapabilities bc = this.bufferStrategy.getCapabilities();
			//System.out.println(bc);
			
	        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	        rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
	        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
	        g2d.setRenderingHints(rh);
	        
		
	        Dimension size = this.getParent().getSize();
	        int colsVisual = (int)(size.getWidth()-0*this.controller.getPopulationModel().getMargin())/this.controller.getPopulationModel().getCellSize(); 
	        int rowsVisual = (int)(size.getHeight()-0*this.controller.getPopulationModel().getMargin())/this.controller.getPopulationModel().getCellSize(); 
	        colsVisual = (colsVisual<this.controller.getAutomatonModel().getNumberOfColumns())?colsVisual:this.controller.getAutomatonModel().getNumberOfColumns();
	        rowsVisual = (rowsVisual<this.controller.getAutomatonModel().getNumberOfRows())?rowsVisual:this.controller.getAutomatonModel().getNumberOfRows();
			for (int y = 0; y < rowsVisual; y++){
				for (int x = 0; x < colsVisual; x++){
					Color c = this.controller.getAutomatonModel().getStates().getColorMapping()[this.controller.getAutomatonModel().getPopulation()[y][x].getState()];
					RectangleFactory.getRectangle(c).draw(g2d, this.controller.getPopulationModel(), x, y, controller.getPopulationModel().getCellSize(), this.controller.getPopulationModel().getMargin());
				}
			}
			if(!CASFrame.bufferStrategy.contentsLost() )
				CASFrame.bufferStrategy.show();
			//Thread.yield();
	    } finally {
	    	if(g2d!=null)
	           g2d.dispose();
	    }		
		
	}
		*/
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((2 * this.controller.getPopulationModel().getMargin()) + (this.controller.getAutomatonModel().getNumberOfColumns() * controller.getPopulationModel().getCellSize()), (2 * this.controller.getPopulationModel().getMargin()) + (this.controller.getAutomatonModel().getNumberOfRows() * controller.getPopulationModel().getCellSize()));
	}

	@Override
	public void update(CstmObservable arg0, Object arg1) {
		//this.revalidate();
		//this.repaint();
		
		//this.paintComponent();
	}
	
	@Override
	public void removeFromObserverable() {

	}

	@Override
	public void addToObserverable() {

	}
}
