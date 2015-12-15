package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.editor.CompileFileListener;
import de.cas.controller.listener.editor.SaveFileListener;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.view.casUI.component.CASJMenu;
import de.cas.view.casUI.component.CASJMenuItem;

public class EditorMenuTools  extends CASJMenu{
	
	private static final long serialVersionUID = 7434766771482838347L;
	IAutomatonController controller;
	private CASJMenuItem menuItemLoad;
	private CASJMenuItem menuItemSave;
	private CASJMenuItem menuItemCompile;
	
	public EditorMenuTools(IAutomatonController controller, Property propertyText, Property propertyDescription) {
		super(controller, propertyText, propertyDescription);
		this.controller = controller;
		this.acceleratorModifiers = ActionEvent.CTRL_MASK;
		
    	this.menuItemLoad = new CASJMenuItem(controller,
    			Property.EDITORTOOLS_MENUITEM_LOAD_TEXT,
    			Property.EDITORTOOLS_MENUITEM_LOAD_ACCELERATOR_KEY,
    			Property.EDITORTOOLS_MENUITEM_LOAD_DESCRIPTION,
    			this.acceleratorModifiers);
    	
    	this.menuItemSave = new CASJMenuItem(controller,
    			Property.EDITORTOOLS_MENUITEM_SAVE_TEXT,
    			Property.EDITORTOOLS_MENUITEM_SAVE_ACCELERATOR_KEY,
    			Property.EDITORTOOLS_MENUITEM_SAVE_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemSave.addActionListener(new SaveFileListener(controller));

    	this.menuItemCompile = new CASJMenuItem(controller,
    			Property.EDITORTOOLS_MENUITEM_COMPILE_TEXT,
    			Property.EDITORTOOLS_MENUITEM_COMPILE_ACCELERATOR_KEY,
    			Property.EDITORTOOLS_MENUITEM_COMPILE_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemCompile.addActionListener(new CompileFileListener(controller));
    	
    	this.add(menuItemLoad);
    	this.add(menuItemSave);
    	this.add(menuItemCompile);

    	update(null, this);
	}
}
