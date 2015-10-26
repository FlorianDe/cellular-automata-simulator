package de.cas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import de.cas.model.Automaton;
import de.cas.model.automata.GameOfLifeAutomaton;
import de.cas.view.MainFrame;

public class Main implements ActionListener {
	
	Timer timer;
	MainFrame view;
	Automaton automaton;
	
	public static void main(String[] args) throws InterruptedException {
		new Main().startGUI();
	}
	
	public void startGUI(){
		automaton = new GameOfLifeAutomaton(300, 605,2,true, true);
		automaton.randomPopulation();

		EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		    	view = new MainFrame(automaton);
		    }
		});
		
		timer = new Timer(1, this);
		timer.start(); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		automaton.calcNextGeneration();
		view.getPopulationPanel().repaint();
	}
}
