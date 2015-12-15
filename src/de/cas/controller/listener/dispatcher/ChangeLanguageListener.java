package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import de.cas.controller.IAutomatonController;
import de.cas.util.Lang;

public class ChangeLanguageListener implements ActionListener {
	protected IAutomatonController controller;
	private Locale locale;

	public ChangeLanguageListener(IAutomatonController controller, String localeStr) {
		this(controller, new Locale(localeStr));
	}
	public ChangeLanguageListener(IAutomatonController controller, Locale locale) {
		this.controller = controller;
		this.locale = locale;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.controller.getLanguageBundle().setLanguageBundle(locale)){
			Lang.println(this.controller.getAutomatonModel(), "Changed language to %s", this.locale.getDisplayName());
		}
		
	}
}
