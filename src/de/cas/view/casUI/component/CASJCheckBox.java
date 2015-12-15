package de.cas.view.casUI.component;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.IPolyGlot;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.menu.StayOpenCheckBoxMenuItemUI;

public class CASJCheckBox extends JCheckBoxMenuItem implements IPolyGlot, CstmObserver{

	private static final long serialVersionUID = -7564681246297114422L;
	private Property propertyText;
	private Property propertyDescription;
	private Property propertyAcceleratorKey;
	private int acceleratorModifiers;
	private IAutomatonController controller;
	
	public CASJCheckBox(IAutomatonController controller, Property propertyText, Property propertyAcceleratorKey, Property propertyDescription, int acceleratorModifiers){
		this.controller = controller;
		this.propertyText = propertyText;
		this.propertyDescription = propertyDescription;
		this.propertyAcceleratorKey = propertyAcceleratorKey;
		this.acceleratorModifiers = acceleratorModifiers;
		this.addToObserverable();
    	this.setUI(new StayOpenCheckBoxMenuItemUI());
		this.updateProperties();
	}
	
	@Override
	public void updateProperties() {
		String textStr = this.controller.getLanguageBundle().getValue(this.propertyText);
		String descriptionStr = this.controller.getLanguageBundle().getValue(this.propertyDescription);
		char acceleratorKeyChar = this.controller.getLanguageBundle().getValue(this.propertyAcceleratorKey).charAt(0);
		this.setText(textStr);
		this.setInformation(descriptionStr, acceleratorKeyChar, this.acceleratorModifiers);
	}
	
	private void setInformation(String description, char acceleratorKey, int acceleratorModifiers){
		char mnemonicKey = (this.getText().length()>0)?mnemonicKey = this.getText().charAt(0):'?';
		this.setMnemonic(mnemonicKey);
		this.getAccessibleContext().setAccessibleDescription(description);
		this.setToolTipText(description);
		this.setAccelerator(KeyStroke.getKeyStroke(acceleratorKey,acceleratorModifiers));
	}

	@Override
	public void update(CstmObservable o, Object arg) {
		this.updateProperties();
		this.revalidate();
		this.repaint();
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
