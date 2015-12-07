package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import de.cas.Main;
import de.cas.controller.IAutomatonController;
import de.cas.model.Automaton;
import de.cas.model.internalautomata.GameOfLifeAutomaton;
import de.cas.util.CstmClassloader;
import de.cas.util.FileLoader;
import de.cas.util.Lang;
import de.cas.view.casUI.dialog.CASFileChooser;

public class OpenSimulatorListener implements ActionListener {
	protected IAutomatonController controller;

	public OpenSimulatorListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CASFileChooser cfc = new CASFileChooser(this.controller);

		int result = cfc.showDialog(null, "Load");
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			for (File f0 : cfc.getSelectedFiles()) {
				try {
					Files.walk(Paths.get(f0.getPath()))
					.filter(Files::isRegularFile)
					.filter(FileLoader::classFileSuffixFilter)
					.forEach(OpenSimulatorListener::instantiateNewSimulator);

				} catch (NullPointerException err) {
					System.out.println("Wrong path for: " + f0.getPath());
				} catch (IOException err) {
					System.out.println("Cannot load: " + f0.getPath());
				}
			}
			break;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("Cancel or the close-dialog icon was clicked");
			break;
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
			break;
		}
	}

	public static void instantiateNewSimulator(Path path) {
		Class<?> cls = CstmClassloader.getClassLoader().loadClass(new File(path.toUri()));
		if(cls!=null){
			Object obj;
			try {
				obj = cls.newInstance();
				if (obj instanceof Automaton) {
					Automaton automaton = (Automaton)obj;
					new Main().startGUI(automaton);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		else{
			//System.out.println("Cannot load: " + path + " because class is null");
		}
	}

}
