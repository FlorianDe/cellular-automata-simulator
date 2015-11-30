package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.cas.controller.IAutomatonController;

public class ExitSimulatorListener implements ActionListener {
	protected IAutomatonController controller;

	public ExitSimulatorListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.exitSimulator();
		this.controller.getSimulationModel().stopThread();
	}
}
