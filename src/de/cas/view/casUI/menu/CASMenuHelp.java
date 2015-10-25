package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class CASMenuHelp extends JMenu {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	JMenuItem menuItemHelp;
	JMenuItem menuItemAbout;
	
	public CASMenuHelp(String name){
		super(name);
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");
		
    	this.menuItemHelp = this.jme.createJMenuItem(new JMenuItem("Hilfe"), 'U', "STRING_DESCRIPTION", this);
    	this.menuItemAbout = this.jme.createJMenuItem(new JMenuItem("Über CASimulator"), 'A', "STRING_DESCRIPTION", this);
	}
}
