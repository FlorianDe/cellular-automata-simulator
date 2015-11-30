package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.ScrollPane;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.MouseWheelZoomListener;
import de.cas.controller.listener.population.PaintCellsListener;
import de.cas.model.Automaton;
import de.cas.model.Cell;
import de.cas.model.PopulationModel;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.util.RectangleFactory;
import javafx.scene.shape.Rectangle;
import sun.java2d.loops.DrawLine;

public class CASPopulationPanel extends JPanel implements CstmObserver  {

	private static final long serialVersionUID = 5065284549023365460L;
	
    private JScrollPane scrollPane;
	IAutomatonController controller;
    PopulationModel pm;
    Automaton am;
	BufferedImage populationImageBuffer;
	RenderingHints rh;

	public CASPopulationPanel(IAutomatonController controller){
		this.controller = controller;
		this.pm = this.controller.getPopulationModel();
		this.am = this.controller.getAutomatonModel();
		this.scrollPane = new JScrollPane();
		this.controller.getPopulationModel().addObserver(this);
		this.controller.getAutomatonModel().getStates().addObserver(this);
		this.controller.getAutomatonModel().addObserver(this);
		
		PaintCellsListener pcl = new PaintCellsListener(this.controller);
		this.addMouseListener(pcl);
		this.addMouseMotionListener(pcl);
        this.addMouseWheelListener(new MouseWheelZoomListener(controller)); 
		
		this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
        
        this.setPaintSettings();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.populationImageBuffer = new BufferedImage(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight(),BufferedImage.TYPE_INT_RGB);
        
	    update(null, this);
	}
	
	private void setPaintSettings(){
        rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        rh.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        pointPopulation(g);
	}
	
	protected void pointPopulation(Graphics g){
        synchronized(this.controller.getAutomatonModel().getPopulation()){
			long startTime = System.currentTimeMillis();
			Graphics2D g2d = (Graphics2D)g;
	        g2d.setRenderingHints(rh);
	
	        Point parentLocation = this.getScrollPane().getViewport().getViewRect().getLocation();
	        Dimension parentSize = this.getScrollPane().getViewport().getSize();
	        Rectangle popVisible = new Rectangle();
	        int colStart = setStartPosition((parentLocation.getX()-pm.getMargin())/pm.getCellSize());
	        int rowStart = setStartPosition((parentLocation.getY()-pm.getMargin())/pm.getCellSize());
	        int colsEnd = setColsEnd(colStart+(int)(parentSize.getWidth()+2*pm.getMargin())/pm.getCellSize()); 
	        int rowsEnd = setRowsEnd(rowStart+(int)(parentSize.getHeight()+2*pm.getMargin())/pm.getCellSize()); 
	
	        //System.out.printf("CS:%s, Start[X:%s,Y:%s], PL[X:%s,Y:%s], PS[W:%s,H:%s], Visible[C:%s,R:%s]   \n", pm.getCellSize(), colStart, rowStart, parentLocation.x , parentLocation.y, parentSize.getWidth(), parentSize.getHeight(), colsEnd,rowsEnd);
	        
	        BufferedImage bim = new BufferedImage(am.getNumberOfColumns(),am.getNumberOfRows(),BufferedImage.TYPE_INT_RGB);
	        DataBufferInt bb = (DataBufferInt)bim.getRaster().getDataBuffer();
	        int[] buffer = bb.getData();
	        for (int y = rowStart; y < rowsEnd; y++)
				for (int x = colStart; x < colsEnd; x++)
					buffer[y*bim.getWidth()+x] = am.getStates().getColorRGBInt(am.getPopulation()[y][x].getState());
	  
			g2d.drawImage(bim,pm.getMargin(),pm.getMargin(),am.getNumberOfColumns()*pm.getCellSize(), am.getNumberOfRows()*pm.getCellSize(), null);	
			if(pm.isDrawCellRect()){
				g2d.setColor(Color.black);
				for (int y = 0; y <= rowsEnd; y++)
					g2d.drawLine(pm.getMargin(), pm.getMargin()+y*pm.getCellSize(), colsEnd*pm.getCellSize()+pm.getMargin(), pm.getMargin()+y*pm.getCellSize());
				for (int x = 0; x <= colsEnd; x++)
					g2d.drawLine(pm.getMargin()+x*pm.getCellSize(), pm.getMargin(), pm.getMargin()+x*pm.getCellSize(), rowsEnd*pm.getCellSize()+pm.getMargin());
			}
	        
			g2d.dispose();
	        System.out.print("Real Redraw Time: "+(System.currentTimeMillis() - startTime)+" ms    ");
        }
	}
	
	
	private int setStartPosition(int minimum){
		return minimum<0?0:minimum;
	}
	private int setStartPosition(double minimum){
		return minimum<0?0:(int)minimum;
	}
	private int setRowsEnd(double endRows){
		return (endRows<am.getNumberOfRows())?(int)endRows:am.getNumberOfRows();
	}
	private int setColsEnd(double endCols){
		return (endCols<am.getNumberOfColumns())?(int)endCols:am.getNumberOfColumns();
	}
	

	protected void pointPopulationOld(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		long startTime = System.currentTimeMillis();
        g2d.setRenderingHints(rh);
        
        Dimension size = this.getParent().getSize();
        PopulationModel pm = this.controller.getPopulationModel();
        Automaton am = this.controller.getAutomatonModel();
        int colsVisual = (int)size.getWidth()/pm.getCellSize(); 
        int rowsVisual = (int)size.getHeight()/pm.getCellSize(); 
        colsVisual = (colsVisual<am.getNumberOfColumns())?colsVisual:am.getNumberOfColumns();
        rowsVisual = (rowsVisual<am.getNumberOfRows())?rowsVisual:am.getNumberOfRows();
        
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
