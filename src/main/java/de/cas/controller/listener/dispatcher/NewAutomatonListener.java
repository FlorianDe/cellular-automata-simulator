package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.Lang;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.SingleStringInputJDialog;

public class NewAutomatonListener implements ActionListener {
	protected IAutomatonController controller;

	public NewAutomatonListener(IAutomatonController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final SingleStringInputJDialog nad = new SingleStringInputJDialog(controller, null, CASLanguageBundle.Property.NEWAUTOMATONDIALOG_LBL_AUTOMATONNAME,"");
				nad.setOnAcceptListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String automatonName = nad.getValue();
						String automatonPath = CstmClassloader.getAutomataFolder()+File.separator+automatonName+CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);
						File file = new File(automatonPath);
						
						if(!file.exists()){
							String automatonTemplate = Lang.readResourceFileToString(CASSettings.getInstance().getProperty(Property.AUTOMATON_TEMPLATE_PATH));
							automatonTemplate = automatonTemplate.replace(CASSettings.getInstance().getProperty(Property.AUTOMATON_TEMPLATE_PLACEHOLDER_CLASSNAME), automatonName);
							Lang.saveFile(automatonPath, automatonTemplate);
						
							nad.dispose();
						}else{
							JOptionPane.showMessageDialog(controller.getView(),
								    "Automaton with this name already exists!",
								    "Error",
								    JOptionPane.ERROR_MESSAGE);
						}
						//controller.getAutomatonModel().setSize(dlg.getRows(), dlg.getColumns());
						//controller.getSimulation().resetStepCount();
					}
				});
				nad.setVisible(true);
			}
		});
	}
}
