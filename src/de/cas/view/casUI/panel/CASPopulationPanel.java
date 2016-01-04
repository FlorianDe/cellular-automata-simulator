package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.MouseWheelZoomListener;
import de.cas.controller.listener.population.PaintCellsListener;
import de.cas.model.Automaton;
import de.cas.model.PopulationModel;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class CASPopulationPanel extends JPanel implements CstmObserver  {

	private static final long serialVersionUID = 5065284549023365460L;
	
    private JScrollPane scrollPane;
	IAutomatonController controller;
	BufferedImage populationImageBuffer;
	RenderingHints rh;

	public CASPopulationPanel(IAutomatonController controller){
		this.controller = controller;

		this.scrollPane = new JScrollPane();
		this.addToObserverable();
		
		PaintCellsListener pcl = new PaintCellsListener(this.controller);
		this.addMouseListener(pcl);
		this.addMouseMotionListener(pcl);
        this.addMouseWheelListener(new MouseWheelZoomListener(controller)); 
		
		this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
        
        this.setPaintSettings();
        //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //this.populationImageBuffer = new BufferedImage(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight(),BufferedImage.TYPE_INT_RGB);
        
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
        paintPopulation(g, true, 0);
		g.dispose();
	}
	
	public void paintFullPopulation(Graphics g, int yOffset){
		paintPopulation(g, false, yOffset);
	}
	
	protected void paintPopulation(Graphics g, boolean drawVisibleAreaOnly, int yOffset){
        //synchronized(this.controller.getAutomatonModel().getPopulation()){
			//long startTime = System.currentTimeMillis();
			Graphics2D g2d = (Graphics2D)g;
	        g2d.setRenderingHints(rh);
	        
			PopulationModel pm = this.controller.getPopulationModel();
			Automaton am = this.controller.getAutomatonModel();
	
			int colStart = 0;
			int rowStart = 0;
			int colsEnd = 0;
			int rowsEnd = 0;
			
			int automatonColumns = am.getNumberOfColumns();
			int automatonRows = am.getNumberOfRows();
			
			int margin = pm.getMargin();
			int cellSize = pm.getCellSize();
			
			if(drawVisibleAreaOnly){
		        Point parentLocation = this.getScrollPane().getViewport().getViewRect().getLocation();
		        Dimension parentSize = this.getScrollPane().getViewport().getSize();

		        colStart = setStartPosition((parentLocation.getX()-margin)/cellSize);
		        rowStart = setStartPosition((parentLocation.getY()-margin)/cellSize);
		        colsEnd = setEndPosition(colStart+(int)(parentSize.getWidth()+2*margin)/cellSize, automatonColumns); 
		        rowsEnd = setEndPosition(rowStart+(int)(parentSize.getHeight()+2*margin)/cellSize, automatonRows); 
			} else {
		        colsEnd = automatonColumns; 
		        rowsEnd = automatonRows; 
			}
	        //System.out.printf("CS:%s, Start[X:%s,Y:%s], PL[X:%s,Y:%s], PS[W:%s,H:%s], Visible[C:%s,R:%s]   \n", cellSize, colStart, rowStart, parentLocation.x , parentLocation.y, parentSize.getWidth(), parentSize.getHeight(), colsEnd,rowsEnd);
	        
	        BufferedImage bim = new BufferedImage(automatonColumns,automatonRows,BufferedImage.TYPE_INT_RGB);
	        DataBufferInt bb = (DataBufferInt)bim.getRaster().getDataBuffer();
	        int[] buffer = bb.getData();
	        for (int y = rowStart; y < rowsEnd; y++)
				for (int x = colStart; x < colsEnd; x++)
					buffer[y*bim.getWidth()+x] = am.getStates().getColorRGBInt(am.getPopulation()[y][x].getState());
	  
			g2d.drawImage(bim,margin,margin+yOffset,automatonColumns*cellSize, automatonRows*cellSize, null);	
			if(pm.isDrawCellRect()){
				g2d.setColor(Color.black);
				for (int y = 0; y <= rowsEnd; y++)
					g2d.drawLine(margin, margin+y*cellSize+yOffset, colsEnd*cellSize+margin, margin+y*cellSize+yOffset);
				for (int x = 0; x <= colsEnd; x++)
					g2d.drawLine(margin+x*cellSize, margin+yOffset, margin+x*cellSize, rowsEnd*cellSize+margin+yOffset);
			}
	        
			//Lang.println(am, "Real Redraw Time: "+(System.currentTimeMillis() - startTime)+" ms    ");
        //}
	}
	
	
	private int setStartPosition(int minimum){
		return minimum<0?0:minimum;
	}
	private int setStartPosition(double minimum){
		return setStartPosition((int)minimum);
	}
	private int setEndPosition(double dimCalcEnd, int dimRealEnd){
		return (dimCalcEnd<dimRealEnd)?(int)dimCalcEnd:dimRealEnd;
	}

	/*
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
	
	@Override
	public void removeFromObserverable() {
		this.controller.getAutomatonModel().getStates().deleteObserver(this);
		this.controller.getAutomatonModel().deleteObserver(this);
	}

	@Override
	public void addToObserverable() {
		this.controller.getView().getObservers().add(this);
		this.controller.getPopulationModel().addObserver(this);
		this.controller.getAutomatonModel().getStates().addObserver(this);
		this.controller.getAutomatonModel().addObserver(this);
	}
}
