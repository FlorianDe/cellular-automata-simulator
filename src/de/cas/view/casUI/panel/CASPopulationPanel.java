package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.MouseWheelZoomListener;
import de.cas.controller.listener.population.PaintCellsListener;
import de.cas.model.Automaton;
import de.cas.model.Cell;
import de.cas.model.PopulationModel;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.util.RectangleFactory;

public class CASPopulationPanel extends JPanel implements CstmObserver  {

	private static final long serialVersionUID = 5065284549023365460L;

	IAutomatonController controller;
	Graphics2D g2d = null;

	public CASPopulationPanel(IAutomatonController controller){
		this.controller = controller;
		this.controller.getPopulationModel().addObserver(this);
		this.controller.getAutomatonModel().getStates().addObserver(this);
		this.controller.getAutomatonModel().addObserver(this);
		
		PaintCellsListener pcl = new PaintCellsListener(this.controller);
		this.addMouseListener(pcl);
		this.addMouseMotionListener(pcl);
        this.addMouseWheelListener(new MouseWheelZoomListener(controller)); 
		
		this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
        
	    update(null, this);
	}

	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		long startTime = System.currentTimeMillis();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        rh.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHints(rh);

        Dimension size = this.getParent().getSize();
        PopulationModel pm = this.controller.getPopulationModel();
        Automaton am = this.controller.getAutomatonModel();
        int colsVisual = (int)size.getWidth()/pm.getCellSize(); 
        int rowsVisual = (int)size.getHeight()/pm.getCellSize(); 
        colsVisual = (colsVisual<am.getNumberOfColumns())?colsVisual:am.getNumberOfColumns();
        rowsVisual = (rowsVisual<am.getNumberOfRows())?rowsVisual:am.getNumberOfRows();
        
        /*
        BufferedImage bim = new BufferedImage(colsVisual,rowsVisual,BufferedImage.TYPE_3BYTE_BGR);
        DataBufferByte bb = (DataBufferByte)bim.getRaster().getDataBuffer();
        byte[] buffer = bb.getData();
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 64;
		}

        System.out.println("buffer.length:"+buffer.length);


		g2d.drawImage(bim,0,0,500,500, null);
        
        */
        Color c = null;
        for (int y = 0; y < rowsVisual; y++){
			for (int x = 0; x < colsVisual; x++){
				c = am.getStates().getColorMapping()[am.getPopulation()[y][x].getState()];
				RectangleFactory.getRectangle(c).draw(g2d, pm, x, y, pm.getCellSize(), pm.getMargin());
			}
		}
        
		if(pm.isDrawCellRect()){
			g2d.setColor(Color.black);
			int margin = this.controller.getPopulationModel().getMargin();
			int cellSize = this.controller.getPopulationModel().getCellSize();
			
			int widthVisual = (int)size.getWidth();
			int widthPop = this.controller.getAutomatonModel().getNumberOfColumns()*cellSize+margin;
			if(widthVisual>widthPop)
				widthVisual=widthPop;
				
			int heightVisual = (int)size.getHeight();
			int heightPop = this.controller.getAutomatonModel().getNumberOfRows()*cellSize+margin;
			if(heightVisual>heightPop)
				heightVisual = heightPop;

			for (int y = 0; y <= rowsVisual; y++) {
				g2d.drawLine(margin, margin+y*cellSize, widthVisual, margin+y*cellSize);
			}
			for (int x = 0; x <= colsVisual; x++) {
				g2d.drawLine(margin+x*cellSize, margin, margin+x*cellSize, heightVisual);
			}
		}
		
		g2d.dispose();
        System.out.println("Redraw: "+(System.currentTimeMillis() - startTime)+" ms");
	}
	
	/*
	private static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
	    DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
	    ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width, height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
	}
	*/
		
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((2 * this.controller.getPopulationModel().getMargin()) + (this.controller.getAutomatonModel().getNumberOfColumns() * controller.getPopulationModel().getCellSize()), (2 * this.controller.getPopulationModel().getMargin()) + (this.controller.getAutomatonModel().getNumberOfRows() * controller.getPopulationModel().getCellSize()));
	}

	@Override
	public void update(CstmObservable arg0, Object arg1) {
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public boolean isOptimizedDrawingEnabled(){
		return true;
	}
}
