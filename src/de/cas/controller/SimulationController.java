package de.cas.controller;

import javax.swing.SwingUtilities;

import de.cas.controller.listener.simulation.SimulationStep;
import de.cas.model.SimulationModel;

public class SimulationController extends Thread {

	private IAutomatonController controller;
	
	public SimulationController(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		SimulationModel simulationModel = this.controller.getSimulationModel();
		SimulationStep simulationStep = new SimulationStep(controller);
		
		while (simulationModel.isRunning()) {
			SwingUtilities.invokeLater(simulationStep);
		}
	}
}

