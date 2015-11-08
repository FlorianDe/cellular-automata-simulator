package de.cas;

import javax.swing.Timer;

import de.cas.controller.AutomatonController;
import de.cas.model.Automaton;
import de.cas.model.CurrentAutomatonModel;
import de.cas.model.automata.GameOfLifeAutomaton;
import de.cas.model.automata.KrumelmonsterAutomaton;
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
		automaton  = new KrumelmonsterAutomaton();
		//((GameOfLifeAutomaton) automaton).createTrafficRLUO();
		
			
		CurrentAutomatonModel cam = new CurrentAutomatonModel(automaton);
		controller = new AutomatonController(automaton);
		view = new CASFrame(controller);
		view.getCASMenuBar().getCurrentAutomat().setDynamicMethodButtons(cam);
		
		//AutomatonModel
		controller.getAutomatonModel().addObserver(view.getMessages());
		controller.getAutomatonModel().addObserver(view.getPopulationPanel());
		controller.getAutomatonModel().addObserver(view.getToolbar());
		controller.getAutomatonModel().addObserver(view.getCASMenuBar().getMenuPopulation());
		
		//SimulationModel
		controller.getSimulationModel().addObserver(view.getToolbar());
		controller.getSimulationModel().addObserver(view.getToolbar().getSpeedSlider());
		
		//PopulationModel
		controller.getPopulationModel().addObserver(view.getPopulationPanel());
		
		//StateModel
		controller.getAutomatonModel().getStates().addObserver(view.getStateContainer());
		controller.getAutomatonModel().getStates().addObserver(view.getPopulationPanel());
		
		
		controller.setView(view);
		//controller.getSimulationModel().startThread();
	}
}
