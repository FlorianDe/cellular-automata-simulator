package de.cas.view.casUI.component;

import javax.swing.JMenu;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.IPolyGlot;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class CASJMenu extends JMenu implements IPolyGlot, CstmObserver {

	private static final long serialVersionUID = -1026521676115498195L;
	protected Property propertyText;
	protected Property propertyDescription;
	protected int acceleratorModifiers;
	protected IAutomatonController controller;
	
	public CASJMenu(IAutomatonController controller, Property propertyText, Property propertyDescription){
		this.controller = controller;
		this.propertyText = propertyText;
		this.propertyDescription = propertyDescription;
		this.addToObserverable();
		this.updateProperties();
	}
	
	public void setInformation(String description){
		char mnemonicKey = (this.getText().length()>0)?mnemonicKey = this.getText().charAt(0):'?';
		this.setMnemonic(mnemonicKey);
		this.getAccessibleContext().setAccessibleDescription(description);
		this.setToolTipText(description);
	}
	
	@Override
	public void updateProperties() {
		String textStr = this.controller.getLanguageBundle().getValue(this.propertyText);
		String descriptionStr = this.controller.getLanguageBundle().getValue(this.propertyDescription);
		this.setText(textStr);
		this.setInformation(descriptionStr);
	}

	@Override
	public void update(CstmObservable o, Object arg) {
		this.updateProperties();
	}
	
	@Override
	public void removeFromObserverable() {
		this.controller.getLanguageBundle().deleteObserver(this);
	}

	@Override
	public void addToObserverable() {
		this.controller.getView().getObservers().add(this);
		this.controller.getLanguageBundle().addObserver(this);
	}
}
