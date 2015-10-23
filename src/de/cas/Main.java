package de.cas;

import java.awt.EventQueue;

import de.cas.view.MainFrame;

public class Main {

	static MainFrame view;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		    	view = new MainFrame();
		    }
		});
	}
}
