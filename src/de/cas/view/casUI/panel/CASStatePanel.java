package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.states.ChooseColorListener;
import de.cas.controller.listener.states.SelectStateListener;
import de.cas.view.casUI.component.CASJButton;
import de.cas.view.casUI.component.CASJToggleButton;

public class CASStatePanel extends JPanel{
	
	private static final long serialVersionUID = -6410240876788548983L;
	
	private final static int BTN_HEIGHT = 30;
	private final static int BTN_WIDTH = 55;
	
	private final static int SPACING_LEFT_RIGHT = 5;
	public final static int PANEL_WIDTH = 2*BTN_WIDTH+5*SPACING_LEFT_RIGHT; //Add 15 if Slider not shown permanently!
	
	private JToggleButton btnState;
    private JButton btnColor;
    private IAutomatonController controller;

	public CASStatePanel(IAutomatonController controller, int state, Color color){
		super();
		this.controller = controller;
        this.setOpaque(false);
        this.setAlignmentY(JPanel.TOP_ALIGNMENT);
        //Only use this
        //setBorder(new EmptyBorder(0,0,0,15));
        
        this.btnState = new CASJToggleButton(String.valueOf(state),BTN_HEIGHT,BTN_WIDTH);
        this.btnState.addActionListener(new SelectStateListener(this.controller, state));

        this.btnColor= new CASJButton("",BTN_HEIGHT,BTN_WIDTH);
        this.btnColor.setBackground(color);
        this.btnColor.setText("");
  	    btnColor.addActionListener(new ChooseColorListener(this.controller, state));
  	    
        add(this.btnState);
        add(this.btnColor);
	}
	
	
	public JToggleButton getBtnState() {
		return btnState;
	}

	public JButton getBtnColor() {
		return btnColor;
	}

	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PANEL_WIDTH, BTN_HEIGHT+5);
	}
	
}
