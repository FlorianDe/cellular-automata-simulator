package de.cas.view.casUI.casPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CASStatePanel extends JPanel{
	
	private static final long serialVersionUID = -6410240876788548983L;
	
	private JButton btnState;
    private JButton btnColor;

	public CASStatePanel(int count){
		super();
        this.btnState = new JButton();
        this.btnState.setPreferredSize(new Dimension(50,30));
        this.btnColor= new JButton();
        this.btnColor.setPreferredSize(new Dimension(50,30));

        setBackground(Color.decode("0xF7E6F0"));
        setAlignmentY(JPanel.TOP_ALIGNMENT);
        setBorder(new EmptyBorder(0,0,0,0));

        this.btnState.setText(String.valueOf(count));
        this.btnColor.setBackground(new Color((int)(Math.random() * 0x1000000)));
        this.btnColor.setText("");
        //TODO REPLACE WITH MVC
        ActionListener actionListener = new ActionListener() {
  	      public void actionPerformed(ActionEvent actionEvent) {
  	        Color initialBackground = btnColor.getBackground();
  	        Color background = JColorChooser.showDialog(null, "Change Button Background",
  	            initialBackground);
  	        if (background != null) {
  	          btnColor.setBackground(background);
  	        }
  	      }
  	    };
  	    btnColor.addActionListener(actionListener);
  	    
        add(this.btnState);
        add(this.btnColor);
	}
}
