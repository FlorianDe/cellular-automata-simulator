package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;

import de.cas.controller.IAutomatonController;

public class TorusListener implements ActionListener {
	protected IAutomatonController controller;

	public TorusListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean isTorus = this.controller.getAutomatonModel().isTorus();

		if(e.getSource() instanceof JCheckBoxMenuItem){
			JCheckBoxMenuItem source = (JCheckBoxMenuItem)e.getSource();
			isTorus = source.isSelected();
		}
		else if(e.getSource() instanceof AbstractButton){
			isTorus = !isTorus;
		}
		
		this.controller.getAutomatonModel().setTorus(isTorus);
	}
}
