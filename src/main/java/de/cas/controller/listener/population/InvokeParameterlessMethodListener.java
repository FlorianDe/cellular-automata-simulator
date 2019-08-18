package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;

import de.cas.controller.IAutomatonController;

public class InvokeParameterlessMethodListener implements ActionListener {
	protected IAutomatonController controller;
	private Method method;

	public InvokeParameterlessMethodListener(IAutomatonController controller, Method method) {
		this.controller = controller;
		this.method = method;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			method.invoke(this.controller.getAutomatonModel(), new Object[]{});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exc) {
			String message = "No error Message";
			if(exc!=null && exc.getMessage()!=null && !exc.getMessage().isEmpty())
				message = exc.getMessage();
			JOptionPane.showMessageDialog(this.controller.getView(), "Error: "+message);
		}
	}
}
