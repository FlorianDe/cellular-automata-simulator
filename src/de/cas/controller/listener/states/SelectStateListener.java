package de.cas.controller.listener.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

import de.cas.controller.IAutomatonController;
import de.cas.model.StateModel;

public class SelectStateListener implements ActionListener {
	protected IAutomatonController controller;
	private int state;

	public SelectStateListener(IAutomatonController controller, int state) {
		this.controller = controller;
		this.state = state;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        this.controller.getAutomatonModel().getStates().setActualState(((AbstractButton)e.getSource()).getModel().isSelected()?this.state:StateModel.NO_ACTUAL_STATE);
	}
}
