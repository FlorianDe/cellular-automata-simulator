package de.cas.view.casUI.menu;

import javax.swing.JMenuBar;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle.Property;

public class EditorMenuBar  extends JMenuBar{

	private static final long serialVersionUID = 5002682373114999322L;
	private EditorMenuTools menuTools;
	IAutomatonController controller;
	
	public EditorMenuBar(IAutomatonController controller){
		this.controller = controller;
		
		this.menuTools = new EditorMenuTools(this.controller, 
				Property.EDITORTOOLS_MENU_TEXT,
				Property.EDITORTOOLS_MENU_DESCRIPTION);
		
		this.add(menuTools);
	}

}
