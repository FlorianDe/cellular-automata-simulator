package de.cas;

import javax.swing.Timer;

import de.cas.controller.AutomatonController;
import de.cas.model.Automaton;
import de.cas.model.automata.GameOfLifeAutomaton;
import de.cas.view.CASFrame;

public class Main{
	
	Timer timer;
	CASFrame view;
	Automaton automaton;
	AutomatonController controller;
	
	public static void main(String[] args) throws InterruptedException {
		new Main().startGUI();
	}
	
	public void startGUI(){
		automaton = new GameOfLifeAutomaton();
		controller = new AutomatonController(automaton);
		
		((GameOfLifeAutomaton) automaton).createTrafficRLUO();
		view = new CASFrame(controller);
		controller.setView(view);
		controller.getAutomatonModel().addObserver(view.getPopulationPanel());
		//controller.getAutomatonModel().addObserver(view.getStateContainer());
		controller.getAutomatonModel().getStates().addObserver(view.getStateContainer());
		
		
		controller.getSimulationModel().startThread();
	}
}
