package de.cas.view.casUI.util;

import javax.swing.filechooser.FileNameExtensionFilter;

import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;

public class CASFileFilter{
	public static final FileNameExtensionFilter automatonSuffixFilter;
	public static final FileNameExtensionFilter classSuffixFilter;
	public static final FileNameExtensionFilter javaSuffixFilter;
	public static final FileNameExtensionFilter xmlSuffixFilter;
	public static final FileNameExtensionFilter serializedSuffixFilter;
	public static final FileNameExtensionFilter pngSuffixFilter;
	public static final FileNameExtensionFilter gifSuffixFilter;
    
    
	
	static{
        String automatonEnding = CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);

        automatonSuffixFilter = new FileNameExtensionFilter("Automaton files (*"+automatonEnding+")", automatonEnding.replace(".", ""));
        classSuffixFilter = new FileNameExtensionFilter("Class files (*.class)", "class");
        javaSuffixFilter = new FileNameExtensionFilter("Java files (*.java)", "java");
        xmlSuffixFilter = new FileNameExtensionFilter("XML files (*.xml)", "xml");
        serializedSuffixFilter = new FileNameExtensionFilter("Serialized files (*.ser)", "ser");
        pngSuffixFilter = new FileNameExtensionFilter("PNG files (*.png)", "png");
        gifSuffixFilter = new FileNameExtensionFilter("GIF files (*.gif)", "gif");
	}

}
