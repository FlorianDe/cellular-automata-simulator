package de.cas.controller.listener.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.cas.controller.IAutomatonController;

public class StopListener implements ActionListener {
	protected IAutomatonController controller;

	public StopListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.getSimulationModel().stopThread();
	}
}
