package de.cas.controller.listener.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.cas.controller.IAutomatonController;

public class PlayListener implements ActionListener {
	protected IAutomatonController controller;

	public PlayListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.getSimulationModel().startThread();
	}
}
