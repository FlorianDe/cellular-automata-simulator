package de.cas.controller.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.loader.AutomatonLoader;
import de.cas.util.loader.CstmClassloader;

public class CASSettings extends Properties {

	private static final long serialVersionUID = 2051639141010268110L;
	private static final String SETTINGS_FILE = "properties/settings.properties";
	private static final CASSettings instance = new CASSettings();
	 
	public enum Property implements IProperty{
		LANGUAGE("language"),
		AUTOMATON_TEMPLATE_PATH("automaton.template.path"),
		AUTOMATON_TEMPLATE_PLACEHOLDER_CLASSNAME("automaton.template.placeholder.classname"),
		AUTOMATON_FILES_ENDING("automaton.files.ending"),
		AUTOMATON_PACKAGE("automaton.package"),
		AUTOMATON_DEFAULT("automaton.default");

				
		
        private final String key;
        public String getKey() {return key;}
        Property(String key) {
            this.key = key;
        }
    }
	
	protected CASSettings() {
		this.loadPropertiesFile(SETTINGS_FILE);
	}
 
	public static CASSettings getInstance() {
		return instance;
	}
	
	protected boolean loadPropertiesFile(String filename){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
        if(is != null){
            try {
                this.load(is);
                return true;
            } catch (IOException e) {
                System.out.println("[" + this.getClass().getSimpleName() + "] Failed to load " + filename + " using default values instead!");
                return false;
            }
        }
        else {
            System.out.println("["+ this.getClass().getSimpleName() +"] Cannot find/load " + filename + " using default values instead!" );
            return false;
        }
    }

	public String getProperty(Property property) {
		return this.getProperty(property.getKey());
	}
}
