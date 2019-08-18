package de.cas.controller.listener.population;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.cas.controller.IAutomatonController;

public class PaintCellsListener implements MouseListener, MouseMotionListener {
	
	private int lastButtonPressed;
	private Point pStart;
	private Point pEnd;
	protected IAutomatonController controller;

	public PaintCellsListener(IAutomatonController controller) {
		this.controller = controller;
		this.lastButtonPressed = -1;
		this.pStart = new Point(0,0);
		this.pEnd = null;
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		this.pEnd = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
		//System.out.printf("[mouseDragged] pStart[Y:%s | X:%s],  pEnd[Y:%s | X:%s]\n",pStart.getY(),pStart.getX(), pEnd.getY(), pEnd.getX());
		if (this.lastButtonPressed == MouseEvent.BUTTON3) {
			drawCellLine();
			pStart.setLocation(pEnd.getX(),pEnd.getY());
		}
		else{
			drawCellArea();
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
		this.lastButtonPressed = me.getButton();
		this.pStart = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
		//System.out.printf("[mousePressed] pStart[Y:%s | X:%s],  pEnd[Y:%s | X:%s]\n",pStart.getY(),pStart.getX(), pEnd.getY(), pEnd.getX());
		if(this.pEnd != null){
			if (this.lastButtonPressed == MouseEvent.BUTTON3) {
				drawCellLine();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		this.pEnd = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
		//System.out.printf("[mouseReleased] pStart[Y:%s | X:%s],  pEnd[Y:%s | X:%s]\n",pStart.getY(),pStart.getX(), pEnd.getY(), pEnd.getX());
		if (this.lastButtonPressed != MouseEvent.BUTTON3) {
			this.drawCellArea();
			pEnd = null;
		}
		else{
			pEnd.setLocation(pStart.getX(), pStart.getY());
		}
	}
	
	private void drawCellLine() {
		if(!pStart.equals(pEnd)){
			int dX = (int)(pStart.getX()-pEnd.getX());
			int dY = (int)(pStart.getY()-pEnd.getY());
			int maxXY = Math.max(Math.abs(dX), Math.abs(dY));
			double kX = (dX != 0)?(dX/(double)maxXY):0;
			double kY = (dY != 0)?(dY/(double)maxXY):0;
			
			int state = this.controller.getAutomatonModel().getStates().getActualState();
			
			for (int i = 0; i <= maxXY; i++) {
				int pX = (int)pEnd.getX() + (int)(kX*i);
				int pY = (int)pEnd.getY() + (int)(kY*i);
				this.controller.getAutomatonModel().setState(pY, pX, state);
			}
		}
	}

	private void drawCellArea(){
		if (!pStart.equals(pEnd)){
			int dX = (int)(pEnd.getX() - pStart.getX());
			int dY = (int)(pEnd.getY() - pStart.getY());
			int kX = (dX != 0)?(dX/(Math.abs(dX))):0;
			int kY = (dY != 0)?(dY/(Math.abs(dY))):0;
			
			int state = this.controller.getAutomatonModel().getStates().getActualState();
			
			for (int y = 0; y <= Math.abs(dY); y++) {
				int pY =  (int)pStart.getY() + kY*y;
				for (int x = 0; x <= Math.abs(dX); x++) {
					int pX = (int)pStart.getX() + kX*x;
					this.controller.getAutomatonModel().setState(pY, pX, state);
				}
			}
		}
	}
	
	
	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent me) {}

}
