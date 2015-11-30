package de.cas;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import de.cas.controller.AutomatonController;
import de.cas.model.Automaton;
import de.cas.model.CurrentAutomatonModel;
import de.cas.model.automata.GameOfLifeAutomaton;
import de.cas.view.CASFrame;

public class Main{
	Automaton automaton;
	AutomatonController controller;
	CASFrame view;
	
	public static void main(String[] args) throws InterruptedException {
		new Main().startGUI();
	}
	
	public void startGUI(){
		automaton = new GameOfLifeAutomaton();
		((GameOfLifeAutomaton) automaton).createTrafficRLUO(1);
		
		CurrentAutomatonModel cam = new CurrentAutomatonModel(automaton);
		controller = new AutomatonController(automaton);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				view = new CASFrame(controller);
				view.getCASMenuBar().getCurrentAutomat().setDynamicMethodButtons(cam);
				controller.setView(view);
			}
		});
	}
}
