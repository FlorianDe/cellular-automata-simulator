package de.cas.controller.listener.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import de.cas.controller.DBManager;
import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.Lang;
import de.cas.util.Settings;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.SingleStringInputJDialog;

public class SaveSettingsListener implements ActionListener {
	protected IAutomatonController controller;

	public SaveSettingsListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = JOptionPane.showInputDialog(controller.getView(), "Save");
		if (name != null && !name.isEmpty()) {
			Settings settings = new Settings(name, controller.getView().getX(), controller.getView().getY(), controller.getView().getWidth(),controller.getView().getHeight(),controller.getPopulationModel().getCellSize(), controller.getSimulationModel().getDelay());
			try {
				DBManager.getInstance().saveSettings(settings);
			} catch (SQLException exc) {
				JOptionPane.showMessageDialog(controller.getView(), exc.getMessage(), "Title", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
