package de.cas.controller.listener.dispatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.ScrollPaneConstants;

import de.cas.controller.IAutomatonController;
import de.cas.util.Lang;

public class FullScreenModeToggleListener implements ActionListener {
	protected IAutomatonController controller;

	public FullScreenModeToggleListener(IAutomatonController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.controller.getView().getExtendedState()!=JFrame.MAXIMIZED_BOTH){
			this.controller.getPopulationModel().setMargin(0);
			this.controller.getView().setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.controller.getView().dispose();
			this.controller.getView().setUndecorated(true);
			this.controller.getView().getJMenuBar().setVisible(false);
			this.controller.getView().getToolbar().setVisible(false);
			this.controller.getView().getMessages().setVisible(false);
			this.controller.getView().getSplitPane().setDividerLocation(0.0);
			this.controller.getView().getSplitPane().setDividerSize(0);
			this.controller.getView().getPopulationScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			this.controller.getView().getPopulationScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			this.controller.getView().getStateScrollPane().setVisible(false);
			this.controller.getView().setVisible(true);
		} else {
			this.controller.getView().setExtendedState(JFrame.NORMAL);
			this.controller.getView().dispose();
			this.controller.getView().setUndecorated(false);
			this.controller.getView().setVisible(true);
		}
	}
}
