package de.cas.view.casUI.casMenu;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class CASMenuAutomat extends JMenu {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	JMenuItem menuItemNew;
	JMenuItem menuItemLoad;
	JMenuItem menuItemEditor;
	JMenuItem menuItemExit;
	
	public CASMenuAutomat(String name){
		super(name);
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");

    	this.menuItemNew = this.jme.createJMenuItem(new JMenuItem("Neu..."), 'N', "STRING_DESCRIPTION", this);
    	this.menuItemLoad = this.jme.createJMenuItem(new JMenuItem("Laden..."),'L', "STRING_DESCRIPTION", this);

    	this.addSeparator();
    	
    	this.menuItemEditor = this.jme.createJMenuItem(new JMenuItem("Editor"),'E', "STRING_DESCRIPTION", this);

    	this.addSeparator();

    	this.menuItemExit = this.jme.createJMenuItem(new JMenuItem("Beenden"),'Q', "STRING_DESCRIPTION",this);  	
	}	
}
