package de.cas.view.casUI.component;

import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.simulation.SimulationSpeedListener;
import de.cas.model.SimulationModel;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class CASJSliderPanel extends JPanel implements CstmObserver {

	private static final long serialVersionUID = -8302774182641615679L;
	
	IAutomatonController controller;
	
	JSlider speedSlider;
	JLabel speedLabel;
		
	
	public CASJSliderPanel(IAutomatonController controller){
		super();
		this.controller = controller;
		this.controller.getSimulationModel().addObserver(this);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setOpaque(false);
		this.speedSlider = new JSlider(JSlider.HORIZONTAL, SimulationModel.DELAY_MIN, SimulationModel.DELAY_MAX, controller.getSimulationModel().getDelay());
		this.speedSlider.setMinorTickSpacing((SimulationModel.DELAY_MAX-SimulationModel.DELAY_MIN)/10);
		this.speedSlider.setMajorTickSpacing((SimulationModel.DELAY_MAX-SimulationModel.DELAY_MIN)/2);
		this.speedSlider.setPaintTicks(true);
		this.speedSlider.setPaintLabels(true);
		Dictionary<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(SimulationModel.DELAY_MIN, new JLabel(((double)SimulationModel.DELAY_MIN)+"ms"));
		labelTable.put(SimulationModel.DELAY_MAX, new JLabel(((double)SimulationModel.DELAY_MAX)+"ms"));
		this.speedSlider.setLabelTable(labelTable);
		this.speedSlider.setOpaque(false);
		this.speedSlider.addChangeListener(new SimulationSpeedListener(controller));
		
		this.speedLabel = new JLabel();
		this.speedLabel.setText(controller.getSimulationModel().getDelay()+"ms");
		
		
		this.add(this.speedSlider);
		this.add(this.speedLabel);
	}


	@Override
	public void update(CstmObservable arg0, Object arg1) {
		this.speedLabel.setText(controller.getSimulationModel().getDelay()+"ms [~" + new DecimalFormat("0.00").format((1000.0/controller.getSimulationModel().getDelay())) + " fps]");
	}
}
