package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.simulation.OneStepListener;
import de.cas.controller.listener.simulation.PlayListener;
import de.cas.controller.listener.simulation.StopListener;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.component.CASJMenu;
import de.cas.view.casUI.component.CASJMenuItem;

public class CASMenuSimulation extends CASJMenu implements CstmObserver  {

	private static final long serialVersionUID = 1857407324411535407L;
	CASJMenuItem menuItemStep;
	CASJMenuItem menuItemStart;
	CASJMenuItem menuItemStop;
	
	IAutomatonController controller;
	
	public CASMenuSimulation(IAutomatonController controller, Property propertyText, Property propertyDescription) {
		super(controller, propertyText, propertyDescription);
		
		this.controller = controller;
		this.acceleratorModifiers = ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK;
		this.controller.getSimulationModel().addObserver(this);
		
    	this.menuItemStep = new CASJMenuItem(controller,
    			Property.CASMENUSIMULATION_MENUITEM_STEP_TEXT,
    			Property.CASMENUSIMULATION_MENUITEM_STEP_ACCELERATOR_KEY,
    			Property.CASMENUSIMULATION_MENUITEM_STEP_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemStep.addActionListener(new OneStepListener(controller));
    	
    	this.menuItemStart = new CASJMenuItem(controller,
    			Property.CASMENUSIMULATION_MENUITEM_START_TEXT,
    			Property.CASMENUSIMULATION_MENUITEM_START_ACCELERATOR_KEY,
    			Property.CASMENUSIMULATION_MENUITEM_START_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemStart.addActionListener(new PlayListener(controller));
    	
    	this.menuItemStop = new CASJMenuItem(controller,
    			Property.CASMENUSIMULATION_MENUITEM_STOP_TEXT,
    			Property.CASMENUSIMULATION_MENUITEM_STOP_ACCELERATOR_KEY,
    			Property.CASMENUSIMULATION_MENUITEM_STOP_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemStop.addActionListener(new StopListener(controller));
    	
    	this.add(menuItemStep);
    	this.add(menuItemStart);
    	this.add(menuItemStop);
    	
    	update(null, this);
	}
	
	@Override
	public void update(CstmObservable arg0, Object arg1) {
		this.menuItemStart.setEnabled(!this.controller.getSimulationModel().isRunning());
		this.menuItemStop.setEnabled(this.controller.getSimulationModel().isRunning());
		this.revalidate();
		this.repaint();
	}
}
