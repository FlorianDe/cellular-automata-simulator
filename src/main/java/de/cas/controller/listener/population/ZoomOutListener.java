package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.cas.controller.IAutomatonController;

public class ZoomOutListener implements ActionListener {
	protected IAutomatonController controller;

	public ZoomOutListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.getPopulationModel().zoomOut();
	}
}
