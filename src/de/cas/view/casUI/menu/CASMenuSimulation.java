package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.simulation.OneStepListener;
import de.cas.controller.listener.simulation.PlayListener;
import de.cas.controller.listener.simulation.StopListener;

public class CASMenuSimulation extends JMenu implements Observer  {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	JMenuItem menuItemStep;
	JMenuItem menuItemStart;
	JMenuItem menuItemStop;
	
	IAutomatonController controller;
	
	public CASMenuSimulation(String name, IAutomatonController controller){
		super(name);
		this.controller = controller;
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");
		
    	this.menuItemStep = this.jme.createJMenuItem(new JMenuItem("Schritt"), 'S', "STRING_DESCRIPTION", this);
    	this.menuItemStep.addActionListener(new OneStepListener(controller));
    	this.menuItemStart = this.jme.createJMenuItem(new JMenuItem("Start"), 'A', "STRING_DESCRIPTION", this);
    	this.menuItemStart.addActionListener(new PlayListener(controller));
    	this.menuItemStop = this.jme.createJMenuItem(new JMenuItem("Stopp"), 'O', "STRING_DESCRIPTION", this);
    	this.menuItemStop.addActionListener(new StopListener(controller));
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		this.menuItemStart.setEnabled(!this.controller.getSimulationModel().isRunning());
		this.menuItemStop.setEnabled(this.controller.getSimulationModel().isRunning());
		this.revalidate();
		this.repaint();
	}
}
