package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.cas.controller.IAutomatonController;

public class TorusListener implements ActionListener {
	protected IAutomatonController controller;

	public TorusListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.getAutomatonModel().setTorus(!this.controller.getAutomatonModel().isTorus());
	}
}
