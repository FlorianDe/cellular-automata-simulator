package de.cas.view.casUI.casPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class CASStateContainerPanel extends JPanel {

	private static final long serialVersionUID = -6410240876788548983L;
	
    public JPanel container;
	public ArrayList<CASStatePanel> states;
	
	public CASStateContainerPanel(){
        super();
        this.states = new ArrayList<>();
        this.container = new JPanel();
        this.container.setLayout(new BoxLayout(this.container, BoxLayout.Y_AXIS));
        
        this.add(this.container);
        this.setBackground(Color.decode("0xFACDFA"));
	}
	
	public CASStatePanel addState() {
		CASStatePanel cell = new CASStatePanel(states.size());
        this.states.add(cell);
        this.container.add(cell);
        return cell;
    }

    public void removeCell(CASStatePanel state) {
        this.states.remove(state);
        this.container.remove(state);
    }

    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(CASStatePanel.PANEL_WIDTH ,super.getPreferredSize().height);
    }
}
