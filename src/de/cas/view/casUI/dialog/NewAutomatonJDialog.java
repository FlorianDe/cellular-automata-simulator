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

public class NewAutomatonJDialog  extends JDialog {

	private static final long serialVersionUID = -4873273203993880731L;
	
	private CASJLabel lblAutomatonName;
	private JTextField tfAutomatonName;
	private CASJButton btnAccept;
	private CASJButton btnCancel;
	
	IAutomatonController controller;
	
	public NewAutomatonJDialog(IAutomatonController controller, JFrame parent, String title) {
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
		
		this.lblAutomatonName = new CASJLabel(controller, CASLanguageBundle.Property.NEWAUTOMATONDIALOG_LBL_AUTOMATONNAME);
		this.tfAutomatonName = new JTextField(4);
		this.tfAutomatonName.setText("");
		this.btnAccept = new CASJButton(controller, CASLanguageBundle.Property.ACCEPT);
		this.btnCancel = new CASJButton(controller, CASLanguageBundle.Property.CANCEL);

		add(this.lblAutomatonName);
		add(this.tfAutomatonName);
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
	
	public String getAutomatonName() {
		return this.tfAutomatonName.getText();
	}
}
