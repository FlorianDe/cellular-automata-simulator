package de.cas.view.casUI.dialog;

import java.awt.Component;
import java.awt.Container;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.loader.AutomatonLoader;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.util.CASFileFilter;

/**
 * Created by Florian on 13.11.2015.
 */
public class CASFileChooser extends JFileChooser {

	private static final long serialVersionUID = -527713167703868987L;
	IAutomatonController controller;
	
	
	
	public CASFileChooser(IAutomatonController controller, File currentDirectory, boolean multipleSelection){
		this(controller, currentDirectory, multipleSelection, null, (FileFilter)null); 
	}
	
	public CASFileChooser(IAutomatonController controller, File currentDirectory, boolean multipleSelection, JComponent accesory){
		this(controller, currentDirectory, multipleSelection, accesory, (FileFilter)null); 
	}
	
    public CASFileChooser(IAutomatonController controller, File currentDirectory, boolean multipleSelection, JComponent accesory, FileFilter... filerfilters){
        super("Choose a file/s");
        this.controller = controller;
        CASFileChooser.disableNewFolderButton(this);
        if(filerfilters.length>0){
            this.setAcceptAllFileFilterUsed(false);
	        for (FileFilter fileFilter : filerfilters) {
	        	this.setFileFilter(fileFilter);
			}
        }
        this.setMultiSelectionEnabled(multipleSelection);
        if(multipleSelection){
            this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }
        this.setCurrentDirectory(currentDirectory);
        
        if(accesory!=null){
        	this.setAccessory(accesory);
        }
    }


    public static void disableNewFolderButton(Container c) {
        int len = c.getComponentCount();
        for (int i = 0; i < len; i++) {
            Component comp = c.getComponent(i);
            if (comp instanceof JButton) {
                JButton b = (JButton) comp;
                Icon icon = b.getIcon();
                if (icon != null
                        && icon == UIManager.getIcon("FileChooser.newFolderIcon"))
                    b.setEnabled(false);
            } else if (comp instanceof Container) {
                disableNewFolderButton((Container) comp);
            }
        }
    }
}
