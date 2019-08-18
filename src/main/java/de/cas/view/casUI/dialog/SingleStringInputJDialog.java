package de.cas.view.casUI.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.view.casUI.component.CASJButton;
import de.cas.view.casUI.component.CASJLabel;

public class SingleStringInputJDialog  extends JDialog {

	private static final long serialVersionUID = -4873273203993880731L;
	
	private CASJLabel lblDescription;
	private JTextField tfDescription;
	private CASJButton btnAccept;
	private CASJButton btnCancel;
	
	IAutomatonController controller;
	
	public SingleStringInputJDialog(IAutomatonController controller, JFrame parent, CASLanguageBundle.Property property, String title) {
		super(parent, title, true);
		this.controller = controller;
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		} else {
			this.setLocationRelativeTo(null);
		}
		GridLayout gl = new GridLayout(0, 2);
		this.setLayout(gl);
		
		this.lblDescription = new CASJLabel(controller, property);
		this.tfDescription = new JTextField(4);
		this.tfDescription.setText("");
		this.btnAccept = new CASJButton(controller, CASLanguageBundle.Property.ACCEPT);
		this.btnCancel = new CASJButton(controller, CASLanguageBundle.Property.CANCEL);

		add(this.lblDescription);
		add(this.tfDescription);
		add(this.btnAccept);
		add(this.btnCancel);

		pack();
		//setVisible(true);
	}
	
	public void setOnAcceptListener(ActionListener list) {
		this.btnAccept.addActionListener(list);
	}

	public void setOnCancelListener() {
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}

	public void closeAction() {
		setVisible(false);
		dispose();
	}
	
	public String getValue() {
		return this.tfDescription.getText();
	}
}
