package de.cas;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import de.cas.controller.AutomatonController;
import de.cas.model.Automaton;
import de.cas.model.CurrentAutomatonModel;
import de.cas.model.internalautomata.GameOfLifeAutomaton;
import de.cas.util.Lang;
import de.cas.view.CASFrame;

public class Main{
	Automaton automaton;
	AutomatonController controller;
	CASFrame view;
	
	public static void main(String[] args) throws InterruptedException {
		Lang.setPrintAutomatonCount(true);
		new Main().startGUI(new GameOfLifeAutomaton());
	}
	
	public void startGUI(Automaton automaton){
		this.automaton = automaton;

		CurrentAutomatonModel cam = new CurrentAutomatonModel(this.automaton);
		this.controller = new AutomatonController(automaton);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				view = new CASFrame(controller);
				view.getCASMenuBar().getCurrentAutomat().setDynamicMethodButtons(cam);
				controller.setView(view);
			}
		});
		
		Lang.println(automaton, "Automaton started: %s", automaton.getClass().getSimpleName());
	}
}
