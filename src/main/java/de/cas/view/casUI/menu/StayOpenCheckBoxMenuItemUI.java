package de.cas.view.casUI.menu;

import javax.swing.JComponent;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
/*
 * Source Code from:
 * http://stackoverflow.com/questions/3759379/how-to-prevent-jpopupmenu-disappearing-when-checking-checkboxes-in-it
 */
public class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {

	   @Override
	   protected void doClick(MenuSelectionManager msm) {
	      menuItem.doClick(0);
	   }

	   public static ComponentUI createUI(JComponent c) {
	      return new StayOpenCheckBoxMenuItemUI();
	   }
}
