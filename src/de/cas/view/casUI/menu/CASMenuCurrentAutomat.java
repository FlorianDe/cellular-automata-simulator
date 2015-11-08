package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.cas.model.CurrentAutomatonModel;


public class CASMenuCurrentAutomat extends JMenu {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	
	public CASMenuCurrentAutomat(String name){
		super(name);
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");
	}
	
	public void setDynamicMethodButtons(CurrentAutomatonModel cam){
		this.setText(cam.getAutomatonSimpleName());
		for (Method method : cam.getMethods()) {
			JMenuItem tmi = this.jme.createJMenuItem(new JMenuItem(method.getName()), method.getName().charAt(0), "STRING_DESCRIPTION", this);
			tmi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cam.invokeMethodParameterless(method);
				}
			});
		}
	}
}
