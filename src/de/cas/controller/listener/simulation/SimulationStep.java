package de.cas.controller.listener.simulation;

import de.cas.controller.IAutomatonController;

public class SimulationStep implements Runnable {
	IAutomatonController controller;
	
	public SimulationStep(IAutomatonController controller){
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.getAutomatonModel().calcNextGeneration();
		controller.getSimulationModel().incrementSteps();
	}
}
