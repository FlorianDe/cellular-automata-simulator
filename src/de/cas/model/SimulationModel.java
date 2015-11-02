package de.cas.model;

import java.util.Observable;

import de.cas.controller.IAutomatonController;
import de.cas.controller.SimulationController;

public class SimulationModel extends Observable {

	private volatile int delay;
	private volatile boolean isRunning;
	private SimulationController simulation;
	private IAutomatonController controller;
	private int steps;

	public static final int DELAY_MIN = 1;
	public static final int DELAY_DEFAULT = 2500;
	

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return this.delay;
	}

	public void setRunning(boolean running) {
		isRunning = running;
	}

	public boolean isRunning() {
		return isRunning;
	}
	
	public int getStepCount() {
		return steps;
	}

	public SimulationModel(IAutomatonController controller) {
		this.controller = controller;
		this.delay = DELAY_DEFAULT;
		this.isRunning = false;
		resetSteps();
	}

	public void startThread() {
		this.simulation = new SimulationController(this.controller);
		this.simulation.start();
		this.isRunning = true;
		notify(null);
	}

	public void stopThread() {
		this.simulation = null;
		this.isRunning = false;
		notify(null);
	}

	public void incrementSteps() {
		steps++;
	}
	
	public void resetSteps() {
		steps = 0;
	}
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}

