package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.cas.controller.IAutomatonController;
import de.cas.model.Automaton;

public class CASMessagesPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 5065284549023365460L;
	private JLabel message;
	IAutomatonController controller;
	
	public void setMessage(String message){
		this.message.setText(message);
	}
	
	public String getMessage(){
		return message.getText();
	}
	
	public CASMessagesPanel(IAutomatonController controller){
		this("Herzlich Willkommen", controller);
	    update(null, this);
	}
	public CASMessagesPanel(String message, IAutomatonController controller){
		this.controller = controller;
		this.message = new JLabel(message);
		this.add(this.message);
		this.setBackground(Color.getHSBColor(187, 33, 100));
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		this.setLayout(fl);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Automaton a = this.controller.getAutomatonModel();
		this.message.setText(String.format("[%s: Size:(W=%s H=%s), Torus: %s, NeighborHood: %s, CellSize: %s, Grid: %s]",
				a.getClass().getSimpleName(), a.getNumberOfColumns(),
				a.getNumberOfRows(), 
				a.isTorus()?"On":"Off", 
				a.isMooreNeighborHood()?"Moore":"Neumann",
				this.controller.getPopulationModel().getCellSize(),
				this.controller.getPopulationModel().isDrawCellRect()?"On":"Off"));
	}
}
