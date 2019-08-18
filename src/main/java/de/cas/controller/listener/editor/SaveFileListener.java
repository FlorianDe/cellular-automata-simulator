package de.cas.controller.listener.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.Lang;
import de.cas.util.loader.CstmClassloader;

public class SaveFileListener implements ActionListener {
	protected IAutomatonController controller;

	public SaveFileListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String curAutomatonPath = CstmClassloader.getAutomataFolder()+File.separator+this.controller.getAutomatonModel().getClass().getSimpleName()+CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);
		Lang.saveFile(curAutomatonPath, this.controller.getView().getAutomatonEditor().getRsyntaxTextArea().getText());
	}
}
