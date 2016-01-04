package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.cas.controller.IAutomatonController;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.CASFileChooser;
import de.cas.view.casUI.util.CASFileFilter;

public class SerializePopulationListener implements ActionListener {
	protected IAutomatonController controller;
	protected FileNameExtensionFilter fileFilter;

	public SerializePopulationListener(IAutomatonController controller, FileNameExtensionFilter fileFilter) {
		this.controller = controller;
		this.fileFilter = fileFilter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CASFileChooser cfc = new CASFileChooser(this.controller, CstmClassloader.getPopulationFolder(), false, null, fileFilter);

		int result = cfc.showSaveDialog(this.controller.getView());
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			ObjectOutputStream os = null;
			FileOutputStream fs = null;
			try {
			File file = cfc.getSelectedFile();
			String fDir = file.getParent();
			String fName = file.getName();
			if (!fName.endsWith(this.fileFilter.getExtensions()[0])) {
				fName += ("."+this.fileFilter.getExtensions()[0]);
				file = new File(fDir + File.separator + fName);
			}
			if(CASFileFilter.serializedSuffixFilter.equals(this.fileFilter)){
				fs = new FileOutputStream(file);
				os = new ObjectOutputStream(fs);
				os.writeUTF(this.controller.getAutomatonModel().getClass().getClass().getSimpleName());
				os.writeInt(this.controller.getAutomatonModel().getStates().getNumberOfStates());
				os.writeObject(this.controller.getAutomatonModel().getPopulation());
				os.flush();
			} else if(CASFileFilter.xmlSuffixFilter.equals(this.fileFilter)) {
				//TODO IMPLEMENT XML
			} else{
				JOptionPane.showMessageDialog(cfc, "This file extension is not supported yet!", "Title", JOptionPane.ERROR_MESSAGE);
			}
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(cfc, "Failed to save serialized!", "Title", JOptionPane.ERROR_MESSAGE);
				exc.printStackTrace();
			} finally {
				try {
					if (fs != null)
						fs.close();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
				try {
					if (os != null)
						os.close();
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
