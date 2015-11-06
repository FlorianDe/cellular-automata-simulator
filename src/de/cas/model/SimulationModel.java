package de.cas.model;

import java.util.Observable;

import de.cas.controller.IAutomatonController;
import de.cas.controller.SimulationController;

public class SimulationModel extends Observable {

	private SimulationController simulation;
	private IAutomatonController controller;
	private volatile int delay;
	private volatile boolean isRunning;
	private int steps;

	public static final int DELAY_MIN = 5;
	public static final int DELAY_DEFAULT = 100;

	public void setDelay(int delay) {
		if(delay>=DELAY_MIN)
			this.delay = delay;
		else
			this.delay = DELAY_MIN;
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
		this.setDelay(DELAY_DEFAULT);
		this.isRunning = false;
		this.resetSteps();
	}

	public void startThread() {
		this.simulation = new SimulationController(this.controller);
		this.simulation.start();
		this.isRunning = true;
		this.notify(null);
	}

	public void stopThread() {
		this.simulation = null;
		this.isRunning = false;
		this.notify(null);
	}

	public void incrementSteps() {
		this.steps++;
	}
	
	public void resetSteps() {
		this.steps = 0;
	}
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}

