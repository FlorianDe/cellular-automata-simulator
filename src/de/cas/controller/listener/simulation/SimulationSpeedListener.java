package de.cas.controller.listener.simulation;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cas.controller.IAutomatonController;

public class SimulationSpeedListener  implements ChangeListener {
	protected IAutomatonController controller;

	public SimulationSpeedListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int delay = this.controller.getSimulationModel().getDelay();
		
		if(e.getSource() instanceof JSlider){
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting())
				delay = (int)source.getValue();
		}
		else if(e.getSource() instanceof JTextField){
			JTextField source = (JTextField)e.getSource();
			delay = Integer.parseInt(source.getText());
		}
		
		this.controller.getSimulationModel().setDelay(delay);
	}
}
