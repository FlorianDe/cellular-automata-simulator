package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CASMessagesPanel extends JPanel {

	private static final long serialVersionUID = 5065284549023365460L;
	private JLabel message;
	
	public void setMessage(String message){
		this.message.setText(message);
	}
	
	public String getMessage(){
		return message.getText();
	}
	
	public CASMessagesPanel(){
		this("Herzlich Willkommen");
	}
	public CASMessagesPanel(String message){
		this.message = new JLabel(message);
		this.add(this.message);
		this.setBackground(Color.getHSBColor(187, 33, 100));
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		this.setLayout(fl);
	}
}
