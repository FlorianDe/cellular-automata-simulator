package de.cas.controller.listener.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.cas.controller.DBManager;
import de.cas.controller.IAutomatonController;
import de.cas.util.Settings;

public class DeleteSettingsListener implements ActionListener {
	protected IAutomatonController controller;

	public DeleteSettingsListener(IAutomatonController controller) {
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
					DBManager.getInstance().deleteSettings(selectedSettings);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(controller.getView(), e1.getMessage(), "Title", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
