package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.cas.controller.IAutomatonController;
import de.cas.model.Cell;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.CASFileChooser;
import de.cas.view.casUI.util.CASFileFilter;

public class DeserializePopulationListener implements ActionListener {
	protected IAutomatonController controller;
	protected FileNameExtensionFilter fileFilter;

	public DeserializePopulationListener(IAutomatonController controller, FileNameExtensionFilter fileFilter) {
		this.controller = controller;
		this.fileFilter = fileFilter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CASFileChooser cfc = new CASFileChooser(this.controller, CstmClassloader.getPopulationFolder(), false, null, fileFilter);

		int result = cfc.showOpenDialog(this.controller.getView());
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			File file = cfc.getSelectedFile();
			FileInputStream fs = null;
			ObjectInputStream is = null;
			try {
				fs = new FileInputStream(file);
				is = new ObjectInputStream(fs);
			if(CASFileFilter.serializedSuffixFilter.equals(this.fileFilter)){
				String loadedName = is.readUTF();
				int loadedNumberOfStates = is.readInt();
				if(this.controller.getAutomatonModel().getStates().getNumberOfStates()>=loadedNumberOfStates){
					this.controller.getAutomatonModel().setPopulation((Cell[][])is.readObject());
				} else {
					JOptionPane.showMessageDialog(cfc, "Failed to deserialize population of "+loadedName,"Title", JOptionPane.ERROR_MESSAGE);
				}
			} else if(CASFileFilter.xmlSuffixFilter.equals(this.fileFilter)) {
				//TODO IMPLEMENT XML
			} else{
				JOptionPane.showMessageDialog(cfc, "This file extension is not supported yet!");
			}
			
			} catch (IOException | ClassNotFoundException exc) {
				exc.printStackTrace();
			} finally {
				try {
					if (fs != null)
						fs.close();
					if (is != null)
						is.close();
				} catch (IOException exc) {
					//ignore
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
}
