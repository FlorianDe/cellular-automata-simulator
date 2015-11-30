package de.cas.view.casUI.component;

import java.awt.Dimension;

import javax.swing.JButton;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.IPolyGlot;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class CASJButton extends JButton implements IPolyGlot, CstmObserver {

	private static final long serialVersionUID = -1425912522903891255L;
	
	int prefHeight;
	int prefWidth;

	private CASLanguageBundle.Property property;
	private IAutomatonController controller;
	
	public CASJButton(IAutomatonController controller, CASLanguageBundle.Property property){
		this.controller = controller;
		this.property = property;
		this.controller.getLanguageBundle().addObserver(this);
		this.updateProperties();
	}
	
	public CASJButton(IAutomatonController controller, CASLanguageBundle.Property property, int prefHeight, int prefWidth){
		this(controller, property);
		this.prefHeight = prefHeight;
		this.prefWidth = prefWidth;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.prefWidth, this.prefHeight);
	}
	
	@Override
	public void updateProperties() {
		String text = controller.getLanguageBundle().getValue(this.property);
		this.setText(text);
	}

	@Override
	public void update(CstmObservable o, Object arg) {
		this.updateProperties();
	}
}
