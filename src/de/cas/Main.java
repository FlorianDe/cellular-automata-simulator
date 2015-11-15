package de.cas;

import javax.swing.Timer;

import de.cas.controller.AutomatonController;
import de.cas.model.Automaton;
import de.cas.model.CurrentAutomatonModel;
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
		//automaton  = new KrumelmonsterAutomaton();
		automaton = new GameOfLifeAutomaton();
		automaton.setSize(10, 10);
		//((GameOfLifeAutomaton) automaton).createTrafficRLUO(1);
		
		CurrentAutomatonModel cam = new CurrentAutomatonModel(automaton);
		controller = new AutomatonController(automaton);
		
		view = new CASFrame(controller);
		view.getCASMenuBar().getCurrentAutomat().setDynamicMethodButtons(cam);
		
		controller.setView(view);
		//controller.getSimulationModel().startThread();
	}
}
