package de.cas.controller.listener.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.cas.controller.DBManager;
import de.cas.controller.IAutomatonController;
import de.cas.util.Settings;

public class RecoverSettingsListener implements ActionListener {
	protected IAutomatonController controller;

	public RecoverSettingsListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> settings = null;
		try {
			settings = DBManager.getInstance().getSettingsNames();
			if (settings == null || settings.size() <= 0) {
				JOptionPane.showMessageDialog(controller.getView(),"No settings saved yet!", "Title", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String selectedSettings = (String)JOptionPane.showInputDialog(controller.getView(), "Choose", "Title",JOptionPane.INFORMATION_MESSAGE, null, settings.toArray(new String[settings.size()]), settings.get(0));
				if (selectedSettings != null) {
					Settings recSettings = DBManager.getInstance().recoverSettings(selectedSettings);
					controller.getView().setLocation(recSettings.getX(), recSettings.getY());
					controller.getView().setSize(recSettings.getWidth(), recSettings.getHeight());
					controller.getPopulationModel().setCellSize(recSettings.getCellSize());
					controller.getSimulationModel().setDelay(recSettings.getDelay());
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(controller.getView(), e1.getMessage(), "Title", JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
