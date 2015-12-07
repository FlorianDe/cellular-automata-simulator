package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.dispatcher.ExitSimulatorListener;
import de.cas.controller.listener.dispatcher.OpenSimulatorListener;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.view.casUI.component.CASJMenu;
import de.cas.view.casUI.component.CASJMenuItem;

public class CASMenuAutomat extends CASJMenu{

	private static final long serialVersionUID = 1857407324411535407L;
	
	CASJMenuItem menuItemNew;
	CASJMenuItem menuItemLoad;
	CASJMenuItem menuItemEditor;
	CASJMenuItem menuItemExit;
	
	IAutomatonController controller;
	public CASMenuAutomat(IAutomatonController controller, Property propertyText, Property propertyDescription) {
		super(controller, propertyText, propertyDescription);
		this.controller = controller;
		this.acceleratorModifiers = ActionEvent.CTRL_MASK;

    	this.menuItemNew = new CASJMenuItem(controller,
    			Property.CASMENUAUTOMAT_MENUITEM_NEW_TEXT,
    			Property.CASMENUAUTOMAT_MENUITEM_NEW_ACCELERATOR_KEY,
    			Property.CASMENUAUTOMAT_MENUITEM_NEW_DESCRIPTION,
    			this.acceleratorModifiers);
    	
    	this.menuItemLoad = new CASJMenuItem(controller,
    			Property.CASMENUAUTOMAT_MENUITEM_LOAD_TEXT,
    			Property.CASMENUAUTOMAT_MENUITEM_LOAD_ACCELERATOR_KEY,
    			Property.CASMENUAUTOMAT_MENUITEM_LOAD_DESCRIPTION,
    			this.acceleratorModifiers);
        this.menuItemLoad.addActionListener(new OpenSimulatorListener(this.controller));

    	
    	this.menuItemEditor = new CASJMenuItem(controller,
    			Property.CASMENUAUTOMAT_MENUITEM_EDITOR_TEXT,
    			Property.CASMENUAUTOMAT_MENUITEM_EDITOR_ACCELERATOR_KEY,
    			Property.CASMENUAUTOMAT_MENUITEM_EDITOR_DESCRIPTION,
    			this.acceleratorModifiers);
    	
    	this.menuItemExit = new CASJMenuItem(controller,
    			Property.CASMENUAUTOMAT_MENUITEM_EXIT_TEXT,
    			Property.CASMENUAUTOMAT_MENUITEM_EXIT_ACCELERATOR_KEY,
    			Property.CASMENUAUTOMAT_MENUITEM_EXIT_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemExit.addActionListener(new ExitSimulatorListener(controller));
    	
    	
    	this.add(menuItemNew);
    	this.add(menuItemLoad);
    	this.addSeparator();
    	this.add(menuItemEditor);
    	this.addSeparator();
    	this.add(menuItemExit);
    	
    	update(null, this);
	}
}
