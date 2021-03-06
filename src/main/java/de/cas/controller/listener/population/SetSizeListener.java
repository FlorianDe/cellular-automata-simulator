package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import de.cas.controller.IAutomatonController;
import de.cas.view.casUI.dialog.SetSizeJDialog;

public class SetSizeListener implements ActionListener {
	protected IAutomatonController controller;

	public SetSizeListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final SetSizeJDialog dlg = new SetSizeJDialog(controller, null,"", controller.getAutomatonModel().getNumberOfRows(), controller.getAutomatonModel().getNumberOfColumns());
				dlg.setOnAcceptListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						controller.getAutomatonModel().setSize(dlg.getRows(), dlg.getColumns());
						//controller.getSimulation().resetStepCount();
						dlg.dispose();
					}
				});
				dlg.setVisible(true);
			}
		});
		
		this.controller.getView().getPopulationPanel().revalidate();
		this.controller.getView().getPopulationPanel().repaint();
	}
}
