package de.cas.view.casUI.menu;

import java.lang.reflect.Method;

import javax.swing.JMenuItem;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.InvokeParameterlessMethodListener;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.util.ACallable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.component.CASJMenu;


public class CASMenuCurrentAutomat extends CASJMenu implements CstmObserver {

	private static final long serialVersionUID = 1857407324411535407L;
	
	public CASMenuCurrentAutomat(IAutomatonController controller, Property propertyDescription) {
		super(controller, null, propertyDescription);
		//this.setDynamicMethodButtons();
	}
	
	public void setDynamicMethodButtons(){
		this.removeAll();
		this.setText(this.controller.getAutomatonModel().getClass().getSimpleName());
		JMenuExtension jme = new JMenuExtension();
		for (Method method : this.controller.getAutomatonModel().getParameterlessMethods()) {
			//TODO REFLECTION LABELS!
			ACallable ca = method.getAnnotation(ACallable.class);
			JMenuItem tmi = jme.createJMenuItem(new JMenuItem(method.getName()), method.getName().charAt(0), (ca!=null)?ca.description():"", this);
			tmi.addActionListener(new InvokeParameterlessMethodListener(controller, method));
		}
	}
	
	@Override
	public void updateProperties() {
		String descriptionStr = this.controller.getLanguageBundle().getValue(this.propertyDescription);
		this.setInformation(descriptionStr);
	}
	
	@Override
	public void removeFromObserverable() {
		super.removeFromObserverable();
	}

	@Override
	public void addToObserverable() {
		super.addToObserverable();
		this.setDynamicMethodButtons();
	}	
}
