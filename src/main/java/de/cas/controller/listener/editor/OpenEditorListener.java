package de.cas.controller.listener.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SwingUtilities;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.Lang;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.AutomatonEditor;

public class OpenEditorListener implements ActionListener {
	protected IAutomatonController controller;

	public OpenEditorListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String curAutomatonPath = CstmClassloader.getAutomataFolder()+File.separator+this.controller.getAutomatonModel().getClass().getSimpleName()+CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);
		AutomatonEditor automatonEditor = controller.getView().getAutomatonEditor();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				automatonEditor.getRsyntaxTextArea().setText(Lang.readFileToString(curAutomatonPath));
				automatonEditor.setVisible(true);
				automatonEditor.getRsyntaxTextArea().setCaretPosition(0);
			}
		});
	}
}
