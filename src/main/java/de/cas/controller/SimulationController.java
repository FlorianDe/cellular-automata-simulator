package de.cas.controller;

import javax.swing.SwingUtilities;

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
		
		if(!simulationModel.isOneSimulationStep()){
			while(simulationModel.isRunning()){	
				simulationStep.run();
				try {
					Thread.sleep(simulationModel.getDelay());
				} catch (InterruptedException e) {
					//this.interrupt();
				}
			}
		}
		else{
			SwingUtilities.invokeLater(simulationStep);
			simulationModel.setOneSimulationStep(false);
			simulationModel.setRunning(false);
		}
	}
}

