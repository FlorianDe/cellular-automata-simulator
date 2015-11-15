package de.cas.view.casUI.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.cas.controller.IAutomatonController;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class CASStateContainerPanel extends JPanel implements CstmObserver {

	private static final long serialVersionUID = -6410240876788548983L;
	
    public JPanel container;
	public ArrayList<CASStatePanel> states;
	public IAutomatonController controller;
	
	public CASStateContainerPanel(IAutomatonController controller){
        super();
        this.controller = controller;
        this.controller.getAutomatonModel().getStates().addObserver(this);
        
        this.states = new ArrayList<>();
        this.container = new JPanel();
        this.container.setLayout(new BoxLayout(this.container, BoxLayout.Y_AXIS));
        this.container.setOpaque(false);
        this.setStates();
        
        this.add(this.container);
        this.setBackground(Color.decode("0xFFCDFF"));
        
	    update(null, this);
	}
	
	public void setStates(){
        this.states.clear();
        this.container.removeAll();
		for (int i = 0; i < controller.getAutomatonModel().getStates().getNumberOfStates(); i++) {
			this.addState(i, controller.getAutomatonModel().getStates().getColor(i));
		}
	}
	
	public void refreshColors(){
		for (int i = 0; i < states.size(); i++) {
			if(this.controller.getAutomatonModel().getStates().getColor(i)!=null)
				states.get(i).getBtnColor().setBackground(this.controller.getAutomatonModel().getStates().getColor(i));
		}
	}
	
	public void refreshSelectedState(){
		for (int i = 0; i < states.size(); i++) {
			states.get(i).getBtnState().getModel().setSelected((i==this.controller.getAutomatonModel().getStates().getActualState()));
		}
	}
	
	public CASStatePanel addState(int state, Color color) {
		CASStatePanel cell = new CASStatePanel(this.controller, state, color);
        this.states.add(cell);
        this.container.add(cell);
        return cell;
    }

    public void removeState(CASStatePanel state) {
        this.states.remove(state);
        this.container.remove(state);
    }

    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(CASStatePanel.PANEL_WIDTH ,super.getPreferredSize().height);
    }
    
    @Override
    public Dimension getMaximumSize() {
    	return new Dimension(CASStatePanel.PANEL_WIDTH+100 ,super.getPreferredSize().height);
    }

	@Override
	public void update(CstmObservable o, Object arg) {
		//If ARGUMENT == NEW_AUTOMAT, nicht refreshColors, sondern setStates!
		this.refreshSelectedState();
		this.refreshColors();
		this.revalidate();
		this.repaint();
	}
}
