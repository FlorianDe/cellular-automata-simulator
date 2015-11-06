package de.cas.controller.listener.population;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.cas.controller.IAutomatonController;

public class PaintCellsListener implements MouseListener, MouseMotionListener {
	
	private int lastButtonPressed = -1;
	private Point pStart;
	private Point pEnd;
	protected IAutomatonController controller;

	public PaintCellsListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		this.lastButtonPressed = me.getButton();
		Point p = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
		if (this.controller.getAutomatonModel().isValidPosition(p.y, p.x))
			this.controller.getAutomatonModel().setState(p.y, p.x, this.controller.getAutomatonModel().getStates().getActualState());
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		this.pEnd = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
		if (this.lastButtonPressed == MouseEvent.BUTTON3) {
			drawCellLine();
		}
		else{
			drawCellArea();
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
		this.lastButtonPressed = me.getButton();
		this.pStart = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		this.pEnd = this.controller.getPopulationModel().coordinatesToCell(me.getY(), me.getX());
		if (this.lastButtonPressed != MouseEvent.BUTTON3) {
			this.drawCellArea();
		}
	}
	
	private void drawCellLine() {
		if(pEnd != null){
			if (this.controller.getAutomatonModel().isValidPosition((int)pEnd.getY(), (int)pEnd.getX())) {
				this.controller.getAutomatonModel().setState((int)pEnd.getY(), (int)pEnd.getX(), this.controller.getAutomatonModel().getStates().getActualState());
			}
		}
	}

	private void drawCellArea(){
		if(pStart != null && pEnd != null){
			if (!pStart.equals(pEnd)){
				int dX = (int)(pEnd.getX() - pStart.getX());
				int dY = (int)(pEnd.getY() - pStart.getY());
				int kX = (dX != 0)?(dX/(Math.abs(dX))):0;
				int kY = (dY != 0)?(dY/(Math.abs(dY))):0;
				
				for (int y = 0; y <= Math.abs(dY); y++) {
					int pY =  (int)pStart.getY() + kY*y;
					for (int x = 0; x <= Math.abs(dX); x++) {
						int pX = (int)pStart.getX() + kX*x;
						if (this.controller.getAutomatonModel().isValidPosition(pY, pX)) {
							this.controller.getAutomatonModel().setState(pY, pX, this.controller.getAutomatonModel().getStates().getActualState());
						}
					}
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

}
