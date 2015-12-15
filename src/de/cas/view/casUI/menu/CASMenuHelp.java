package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JMenuItem;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.dispatcher.ChangeLanguageListener;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.component.CASJMenu;
import de.cas.view.casUI.component.CASJMenuItem;


public class CASMenuHelp extends CASJMenu implements CstmObserver{

	private static final long serialVersionUID = 1857407324411535407L;
	CASJMenuItem menuItemHelp;
	CASJMenuItem menuItemAbout;
	CASJMenu submenuLanguage;
	ArrayList<JMenuItem> submenuLanguageItems;
	
	IAutomatonController controller;
	
	public CASMenuHelp(IAutomatonController controller, Property propertyText, Property propertyDescription) {
		super(controller, propertyText, propertyDescription);
		this.controller = controller;
		this.submenuLanguageItems = new ArrayList<>();
		this.acceleratorModifiers = ActionEvent.CTRL_MASK;
		
    	this.menuItemHelp = new CASJMenuItem(this.controller, 
    			Property.CASMENUHELP_MENUITEM_HELP_TEXT,
    			Property.CASMENUHELP_MENUITEM_HELP_ACCELERATOR_KEY,
    			Property.CASMENUHELP_MENUITEM_HELP_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemAbout = new CASJMenuItem(this.controller, 
    			Property.CASMENUHELP_MENUITEM_ABOUT_TEXT,
    			Property.CASMENUHELP_MENUITEM_ABOUT_ACCELERATOR_KEY,
    			Property.CASMENUHELP_MENUITEM_ABOUT_DESCRIPTION,
    			this.acceleratorModifiers);
	
    	this.submenuLanguage = new CASJMenu(controller, 
    			Property.CASMENUHELP_SUBMENU_LANGUAGE_TEXT, 
    			Property.CASMENUHELP_SUBMENU_LANGUAGE_DESCRIPTION);
    	
    	for (Locale locale : CASLanguageBundle.getSupportedLocales()) {
			JMenuItem langItem = new JMenuItem(locale.getDisplayLanguage());
			langItem.addActionListener(new ChangeLanguageListener(controller, locale));
			this.submenuLanguageItems.add(langItem);
			this.submenuLanguage.add(langItem);
		}
    	
    	this.add(menuItemHelp);
    	this.add(menuItemAbout);
    	this.addSeparator();
    	this.add(submenuLanguage);
    	
    	update(null, this);
	}
	
	@Override
	public void update(CstmObservable arg0, Object arg1) {
		super.update(arg0, arg1);
		for (JMenuItem jMenuItem : submenuLanguageItems) {
			jMenuItem.setEnabled(!this.controller.getLanguageBundle().getLocale().getDisplayLanguage().equals(jMenuItem.getText()));
		}
		this.revalidate();
		this.repaint();
	}
}
