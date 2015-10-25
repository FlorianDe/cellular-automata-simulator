package de.cas.view.casUI.component;

import java.awt.Dimension;

import javax.swing.JButton;

public class CASJButton extends JButton{

	private static final long serialVersionUID = -1425912522903891255L;
	
	int prefHeight;
	int prefWidth;

	public CASJButton(String text, int prefHeight, int prefWidth){
		super(text);
		this.prefHeight = prefHeight;
		this.prefWidth = prefWidth;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.prefWidth, this.prefHeight);
	}
}
