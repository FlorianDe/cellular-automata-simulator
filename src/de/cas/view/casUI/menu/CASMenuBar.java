package de.cas.view.casUI.menu;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle.Property;

public class CASMenuBar extends JMenuBar{

	private static final long serialVersionUID = -7726514404946774468L;
	private CASMenuAutomat menuAutomat;
	private CASMenuPopulation menuPopulation;
	private CASMenuSimulation menuSimulation;
	private CASMenuSettings menuSettings;
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

	public CASMenuSettings getMenuSettings() {
		return menuSettings;
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
		this.menuAutomat = new CASMenuAutomat(this.controller, 
				Property.CASMENUAUTOMAT_MENU_TEXT,
				Property.CASMENUAUTOMAT_MENU_DESCRIPTION);	
		
		this.menuPopulation = new CASMenuPopulation(this.controller, 
				Property.CASMENUPOPULATION_MENU_TEXT,
				Property.CASMENUPOPULATION_MENU_DESCRIPTION);	
		
		this.menuSimulation = new CASMenuSimulation(this.controller, 
				Property.CASMENUSIMULATION_MENU_TEXT,
				Property.CASMENUSIMULATION_MENU_DESCRIPTION);
		
		this.menuSettings = new CASMenuSettings(this.controller, 
				Property.CASMENUSETTINGS_MENU_TEXT,
				Property.CASMENUSETTINGS_MENU_DESCRIPTION);
		
		this.menuHelp = new CASMenuHelp(this.controller, 
				Property.CASMENUHELP_MENU_TEXT,
				Property.CASMENUHELP_MENU_DESCRIPTION);
		
		this.currentAutomat = new CASMenuCurrentAutomat(this.controller, Property.CASMENUCURRENTAUTOMAT_MENU_TEXT);
		this.currentAutomat.setBorder(BorderFactory.createEtchedBorder());
		this.currentAutomat.setBorderPainted(true);

		this.add(this.menuAutomat);
		this.add(this.menuPopulation);
		this.add(this.menuSimulation);
		this.add(this.menuSettings);
		this.add(this.menuHelp);
		this.add(new JSeparator(JSeparator.VERTICAL));
		//this.add(Box.createHorizontalGlue());
		this.add(currentAutomat);
		
    }
}
