package de.cas.controller.listener.population;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import de.cas.controller.IAutomatonController;

public class GridOnOffListener implements ItemListener{
	protected IAutomatonController controller;

	public GridOnOffListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		this.controller.getPopulationModel().setDrawCellRect(e.getStateChange()==ItemEvent.SELECTED);
	}
}
