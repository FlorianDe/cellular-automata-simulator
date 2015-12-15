package de.cas.view.casUI.dialog;

import java.awt.Component;
import java.awt.Container;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.loader.AutomatonLoader;
import de.cas.util.loader.CstmClassloader;

/**
 * Created by Florian on 13.11.2015.
 */
public class CASFileChooser extends JFileChooser {

	private static final long serialVersionUID = -527713167703868987L;
	IAutomatonController controller;
	
    public CASFileChooser(IAutomatonController controller){
        super("Choose a file/s");
        this.controller = controller;
        CASFileChooser.disableNewFolderButton(this);
        //FileFilter classSuffixFilter = new FileNameExtensionFilter("Class files (*.class)", "class");
        //FileFilter javaSuffixFilter = new FileNameExtensionFilter("Java files (*.java)", "java");
        String automatonEnding = CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);
        FileFilter automatonSuffixFilter = new FileNameExtensionFilter("Automaton files (*"+automatonEnding+")", automatonEnding.replace(".", ""));
        this.setAcceptAllFileFilterUsed(false);
        //this.setFileFilter(classSuffixFilter);
        this.setFileFilter(automatonSuffixFilter);
        this.setMultiSelectionEnabled(true);
        this.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.setCurrentDirectory(CstmClassloader.getAutomataFolder());
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
