package de.cas.view.casUI.casPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CASStatePanel extends JPanel{

	private static final long serialVersionUID = -6410240876788548983L;

	public CASStatePanel(){
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxLayout);
		
		//TODO ADD DYNAMICALLY FROM MODEL UND SCROLLPANE
		addState(0);
		addState(1);

		this.add(Box.createVerticalBox());
	}
	
	public void addState(int count){
		int prefHeight = 30;
		int prefWidth = 60;
		
		JPanel rowPanel = new JPanel();
		
		JLabel lbl = new JLabel(String.valueOf(count),SwingConstants.CENTER);
		lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		lbl.setPreferredSize(new Dimension(prefWidth, prefHeight));
		rowPanel.add(lbl);
		
		JButton btn = new JButton("");
	    ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
	        Color initialBackground = btn.getBackground();
	        Color background = JColorChooser.showDialog(null, "Change Button Background",
	            initialBackground);
	        if (background != null) {
	          btn.setBackground(background);
	        }
	      }
	    };
	    btn.addActionListener(actionListener);
	    btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
	    btn.setBackground(new Color((int)(Math.random() * 0x1000000)));
	    btn.setPreferredSize(new Dimension(prefWidth, prefHeight));
	    
		rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefHeight+6));
		rowPanel.add(btn);
		this.add(rowPanel);
	}
}
