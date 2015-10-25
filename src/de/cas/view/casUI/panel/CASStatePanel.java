package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.cas.view.casUI.component.CASJButton;
import de.cas.view.casUI.component.CASJToggleButton;

public class CASStatePanel extends JPanel{
	
	private static final long serialVersionUID = -6410240876788548983L;
	
	private final static int BTN_HEIGHT = 30;
	private final static int BTN_WIDTH = 45;
	
	private final static int SPACING_LEFT_RIGHT = 5;
	public final static int PANEL_WIDTH = 2*BTN_WIDTH+5*SPACING_LEFT_RIGHT; //Add 15 if Slider not shown permanently!
	
	private JToggleButton btnState;
    private JButton btnColor;

	public CASStatePanel(int count){
		super();
        this.btnState = new CASJToggleButton(String.valueOf(count),BTN_HEIGHT,BTN_WIDTH);
        this.btnColor= new CASJButton("",BTN_HEIGHT,BTN_WIDTH);
        this.setOpaque(false);
        this.setAlignmentY(JPanel.TOP_ALIGNMENT);
        //Only use this
        //setBorder(new EmptyBorder(0,0,0,15));

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

	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PANEL_WIDTH, BTN_HEIGHT+5);
	}
	
}
