package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.model.CurrentAutomatonModel;
import de.cas.view.casUI.component.CASJMenu;


public class CASMenuCurrentAutomat extends CASJMenu {

	private static final long serialVersionUID = 1857407324411535407L;
	
	public CASMenuCurrentAutomat(IAutomatonController controller, Property propertyDescription) {
		super(controller, null, propertyDescription);
	}
	
	public void setDynamicMethodButtons(CurrentAutomatonModel cam){
		this.setText(cam.getAutomatonSimpleName());
		JMenuExtension jme = new JMenuExtension();
		for (Method method : cam.getMethods()) {
			//TODO REFLECTION LABELS!
			JMenuItem tmi = jme.createJMenuItem(new JMenuItem(method.getName()), method.getName().charAt(0), "STRING_DESCRIPTION", this);
			tmi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cam.invokeMethodParameterless(method);
				}
			});
		}
	}
	
	@Override
	public void updateText() {
		String descriptionStr = this.controller.getPropertiesManager().getLanguageBundle().getValue(this.propertyDescription);
		this.setInformation(descriptionStr);
	}
}
