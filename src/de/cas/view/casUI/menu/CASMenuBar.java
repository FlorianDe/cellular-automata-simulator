package de.cas.view.casUI.menu;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import de.cas.controller.IAutomatonController;

public class CASMenuBar extends JMenuBar{

	private static final long serialVersionUID = -7726514404946774468L;
	private CASMenuAutomat menuAutomat;
	private CASMenuPopulation menuPopulation;
	private CASMenuSimulation menuSimulation;
	private CASMenuHelp menuHelp;
	private CASMenuCurrentAutomat currentAutomat;
	
	public CASMenuCurrentAutomat getCurrentAutomat() {
		return currentAutomat;
	}

	public CASMenuAutomat getMenuAutomat() {
		return menuAutomat;
	}

	public CASMenuPopulation getMenuPopulation() {
		return menuPopulation;
	}

	public CASMenuSimulation getMenuSimulation() {
		return menuSimulation;
	}

	public CASMenuHelp getMenuHelp() {
		return menuHelp;
	}

	IAutomatonController controller;
	
	public CASMenuBar(IAutomatonController controller){
		super();
		this.controller = controller;
		initializeUI_Menu();
	}
	
	private void initializeUI_Menu() {
		this.menuAutomat = new CASMenuAutomat("Automat");	
		this.menuPopulation = new CASMenuPopulation("Population", this.controller);	
		this.menuSimulation = new CASMenuSimulation("Simulation", this.controller);
		this.menuHelp = new CASMenuHelp("Help");
		this.currentAutomat = new CASMenuCurrentAutomat("CurrentAutomat");
		this.currentAutomat.setBorder(BorderFactory.createEtchedBorder());
		this.currentAutomat.setBorderPainted(true);

		this.add(this.menuAutomat);
		this.add(this.menuPopulation);
		this.add(this.menuSimulation);
		this.add(this.menuHelp);
		this.add(new JSeparator(JSeparator.VERTICAL));
		//this.add(Box.createHorizontalGlue());
		this.add(currentAutomat);
		
    }
}
