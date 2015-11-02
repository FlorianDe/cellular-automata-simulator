package de.cas.view.casUI.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.cas.controller.IAutomatonController;

public class CASMenuBar extends JMenuBar{

	private static final long serialVersionUID = -7726514404946774468L;
	private JMenu menuAutomat;
	private JMenu menuPopulation;
	private JMenu menuSimulation;
	private JMenu menuHelp;
	
	IAutomatonController controller;
	
	public CASMenuBar(IAutomatonController controller){
		super();
		this.controller = controller;
		initializeUI_Menu();
	}
	
	private void initializeUI_Menu() {
		this.menuAutomat = new CASMenuAutomat("Automat");	
		this.menuPopulation = new CASMenuPopulation(this.controller, "Population");	
		this.menuSimulation = new CASMenuSimulation("Simulation");
		this.menuHelp = new CASMenuHelp("Help");


		this.add(this.menuAutomat);
		this.add(this.menuPopulation);
		this.add(this.menuSimulation);
		this.add(this.menuHelp);
    }
}
