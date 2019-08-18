package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.dispatcher.LoadAutomatonListener;
import de.cas.controller.listener.dispatcher.NewAutomatonListener;
import de.cas.controller.listener.editor.OpenEditorListener;
import de.cas.controller.listener.settings.DeleteSettingsListener;
import de.cas.controller.listener.settings.RecoverSettingsListener;
import de.cas.controller.listener.settings.SaveSettingsListener;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.view.casUI.component.CASJMenu;
import de.cas.view.casUI.component.CASJMenuItem;

public class CASMenuSettings extends CASJMenu{

	private static final long serialVersionUID = 1857407324411535407L;
	
	CASJMenuItem menuItemSave;
	CASJMenuItem menuItemRecover;
	CASJMenuItem menuItemDelete;
	
	IAutomatonController controller;
	public CASMenuSettings(IAutomatonController controller, Property propertyText, Property propertyDescription) {
		super(controller, propertyText, propertyDescription);
		this.controller = controller;
		this.acceleratorModifiers = ActionEvent.CTRL_MASK;

    	this.menuItemSave = new CASJMenuItem(controller,
    			Property.CASMENUSETTINGS_MENUITEM_SAVE_TEXT,
    			Property.CASMENUSETTINGS_MENUITEM_SAVE_ACCELERATOR_KEY,
    			Property.CASMENUSETTINGS_MENUITEM_SAVE_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemSave.addActionListener(new SaveSettingsListener(this.controller));
    	
    	this.menuItemRecover = new CASJMenuItem(controller,
    			Property.CASMENUSETTINGS_MENUITEM_RECOVER_TEXT,
    			Property.CASMENUSETTINGS_MENUITEM_RECOVER_ACCELERATOR_KEY,
    			Property.CASMENUSETTINGS_MENUITEM_RECOVER_DESCRIPTION,
    			this.acceleratorModifiers);
        this.menuItemRecover.addActionListener(new RecoverSettingsListener(this.controller));

    	this.menuItemDelete = new CASJMenuItem(controller,
    			Property.CASMENUSETTINGS_MENUITEM_DELETE_TEXT,
    			Property.CASMENUSETTINGS_MENUITEM_DELETE_ACCELERATOR_KEY,
    			Property.CASMENUSETTINGS_MENUITEM_DELETE_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemDelete.addActionListener(new DeleteSettingsListener(controller));
    	
    	this.add(menuItemSave);
    	this.add(menuItemRecover);
    	this.add(menuItemDelete);
    	
    	update(null, this);
	}
}
