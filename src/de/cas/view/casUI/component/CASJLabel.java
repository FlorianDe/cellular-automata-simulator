package de.cas.view.casUI.component;

import javax.swing.JLabel;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.IPolyGlot;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class CASJLabel extends JLabel implements IPolyGlot, CstmObserver {

	private static final long serialVersionUID = 6223498433605610355L;
	private CASLanguageBundle.Property property;
	private IAutomatonController controller;
	
	public CASJLabel(IAutomatonController controller, CASLanguageBundle.Property property){
		this.controller = controller;
		this.property = property;
		this.controller.getPropertiesManager().getLanguageBundle().addObserver(this);
		this.updateText();
	}

	@Override
	public void updateText() {
		String text = this.controller.getPropertiesManager().getLanguageBundle().getValue(this.property);
		this.setText(text);
	}

	@Override
	public void update(CstmObservable o, Object arg) {
		this.updateText();
	}
}
