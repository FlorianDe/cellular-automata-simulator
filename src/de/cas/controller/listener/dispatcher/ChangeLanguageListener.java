package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import de.cas.controller.IAutomatonController;

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
		this.controller.getLanguageBundle().setLanguageBundle(locale);
	}
}
