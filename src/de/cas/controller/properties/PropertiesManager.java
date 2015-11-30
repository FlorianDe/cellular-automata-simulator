package de.cas.controller.properties;

import java.util.Locale;

import de.cas.controller.IAutomatonController;

public class PropertiesManager {
	private CASSettings settings;
	private CASLanguageBundle languageBundle;
	public CASSettings getSettings() {
		return settings;
	}

	public void setSettings(CASSettings settings) {
		this.settings = settings;
	}

	public CASLanguageBundle getLanguageBundle() {
		return languageBundle;
	}

	public void setLanguageBundle(CASLanguageBundle languageBundle) {
		this.languageBundle = languageBundle;
	}

	private IAutomatonController controller;
	
	public PropertiesManager(IAutomatonController controller){
		this.controller = controller;
		this.settings = new CASSettings(controller);
		String localeStr = this.settings.getProperty(CASSettings.Property.SETTINGS_PROPERTY_LANGUAGE);
		if(localeStr!=null){
			this.languageBundle = new CASLanguageBundle(controller, new Locale(localeStr));
		} else {
			this.languageBundle = new CASLanguageBundle(controller);
		}
	}
}
