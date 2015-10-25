package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class CASMenuSimulation extends JMenu {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	JMenuItem menuItemStep;
	JMenuItem menuItemStart;
	JMenuItem menuItemStop;
	
	public CASMenuSimulation(String name){
		super(name);
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK+ActionEvent.ALT_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");
		
    	this.menuItemStep = this.jme.createJMenuItem(new JMenuItem("Schritt"), 'S', "STRING_DESCRIPTION", this);
    	this.menuItemStart = this.jme.createJMenuItem(new JMenuItem("Start"), 'A', "STRING_DESCRIPTION", this);
    	this.menuItemStop = this.jme.createJMenuItem(new JMenuItem("Stopp"), 'O', "STRING_DESCRIPTION", this);
	}
}
