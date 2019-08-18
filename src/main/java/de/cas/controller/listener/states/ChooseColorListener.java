package de.cas.controller.listener.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import de.cas.controller.IAutomatonController;

public class ChooseColorListener implements ActionListener {
	protected IAutomatonController controller;
	private int state;

	public ChooseColorListener(IAutomatonController controller, int state) {
		this.controller = controller;
		this.state = state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	  	Color background = JColorChooser.showDialog(null, "Change Button Background", controller.getAutomatonModel().getStates().getColor(this.state));
	  	if (background != null) {
	  		this.controller.getAutomatonModel().getStates().changeColor(this.state, background);
	  		//TODO this should be done with notify I think!
	  		//this.controller.getView().getStateContainer().refreshColors();
	  	}
	}
}
