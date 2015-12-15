package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import de.cas.Main;
import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.model.Automaton;
import de.cas.util.loader.CstmClassloader;
import de.cas.util.loader.MemJavaFileManager;
import de.cas.util.loader.StringJavaFileObject;
import de.cas.view.casUI.dialog.CASFileChooser;

public class LoadAutomatonListener implements ActionListener {
	protected IAutomatonController controller;

	public LoadAutomatonListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CASFileChooser cfc = new CASFileChooser(this.controller);

		int result = cfc.showDialog(null, "Load");
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			for (File fileTier0 : cfc.getSelectedFiles()) {
				try {
					//Predicate<Path> hasClassSuffix  = (p)-> p.toFile().getName().endsWith(".class");
					//Predicate<Path> hasJavaSuffix  = (p)-> p.toFile().getName().endsWith(".java");
					Predicate<Path> hasAutomatonSuffix  = (p)-> p.toFile().getName().endsWith(CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING));

					Files.walk(Paths.get(fileTier0.getPath()))
					.filter(Files::isRegularFile)
					.filter(hasAutomatonSuffix)
					.forEach(p -> startNewAutomaton(p));

				} catch (IOException err) {
					System.out.println("Cannot load: " + fileTier0.getPath());
				}
			}
			break;
		case JFileChooser.CANCEL_OPTION:
			//System.out.println("Cancel or the close-dialog icon was clicked");
			break;
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
			break;
		}
	}

	public static void instantiateNewSimulatorOLD(Path path) {
		CstmClassloader ccl = new CstmClassloader();
		Class<?> cls = ccl.loadClass(new File(path.toUri()));
		if(cls!=null){
			try {
				Object obj = cls.newInstance();
				if (obj instanceof Automaton) {
					new Main().startCAS((Automaton)obj);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void startNewAutomaton(Path path){
		CstmClassloader ccl = new CstmClassloader();
		Automaton automaton = ccl.getAutomatonInstance(path);
		if(automaton!=null){
			new Main().startCAS(automaton);
		}else{
			JOptionPane.showMessageDialog(this.controller.getView(),
					ccl.getLastCompilationErrors(),
				    "Couldn't compile",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

}
