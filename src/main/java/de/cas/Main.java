package de.cas;

import javax.swing.SwingUtilities;

import de.cas.controller.AutomatonController;
import de.cas.model.Automaton;
import de.cas.util.Lang;
import de.cas.util.loader.AutomatonLoader;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.CASFrame;

import java.nio.file.Path;

public class Main{
	Automaton automaton;
	AutomatonController controller;
	CASFrame view;
	
	public static void main(String[] args) throws InterruptedException {
		Lang.setPrintAutomatonCount(true);
		Path path = CstmClassloader.getDefaultAutomaton().toPath();
		System.out.println("path = " + path);
		Automaton automaton = (new CstmClassloader()).getAutomatonInstance(path);
		new Main().startCAS(automaton);
		AutomatonLoader.getInstance();
	}
	
	public void startCAS(Automaton automaton){
		if(automaton!=null){
			this.automaton = automaton;
			this.controller = new AutomatonController(automaton);
			SwingUtilities.invokeLater(() -> view = new CASFrame(controller));
			
			Lang.println(automaton, "Automaton started: %s", automaton.getClass().getSimpleName());
		}else{
			System.err.println("Passed automaton object was null! Cannot work with null");
		}
	}
}
