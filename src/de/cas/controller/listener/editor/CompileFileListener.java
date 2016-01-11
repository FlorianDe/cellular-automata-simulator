package de.cas.controller.listener.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.model.Automaton;
import de.cas.util.loader.CstmClassloader;

public class CompileFileListener implements ActionListener {
	protected IAutomatonController controller;

	public CompileFileListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String curAutomatonPath = CstmClassloader.getAutomataFolder()+File.separator+this.controller.getAutomatonModel().getClass().getSimpleName()+CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);
		CstmClassloader ccl = new CstmClassloader();
		Automaton automaton = ccl.getAutomatonInstance((new File(curAutomatonPath)).toPath());
		if(automaton!=null){
			Automaton automatonOld = this.controller.getAutomatonModel();
			this.controller.getView().removeAllObserverable(controller);
			automaton.setSizeAndStates(automatonOld);
			this.controller.setAutomatonModel(automaton);
			Automaton.removeRunningAutomaton(automatonOld);
			this.controller.getView().setObserverables(controller);
		}else{
			JOptionPane.showMessageDialog(this.controller.getView(),
					ccl.getLastCompilationErrors(),
				    "Couldn't compile",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}
