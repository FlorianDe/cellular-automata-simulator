package de.cas;

import java.awt.EventQueue;

import de.cas.view.MainFrame;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
				new MainFrame();
		    }
		});
	}
}
