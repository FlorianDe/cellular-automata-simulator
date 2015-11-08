package de.cas.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import de.cas.controller.IAutomatonController;
import de.cas.view.casUI.menu.CASMenuBar;
import de.cas.view.casUI.panel.CASMessagesPanel;
import de.cas.view.casUI.panel.CASPopulationPanel;
import de.cas.view.casUI.panel.CASStateContainerPanel;
import de.cas.view.casUI.toolBar.CASJToolBar;

public class CASFrame extends JFrame {
	private static final long serialVersionUID = -1994192886242251349L;
	
	public static BufferStrategy bufferStrategy;
	private CASMenuBar menuBar;
	private CASJToolBar toolbar;
	private CASStateContainerPanel stateContainer;
	private JScrollPane stateScrollPane;
	private JScrollPane	populationScrollPane;
	private CASPopulationPanel populationPanel;
	private CASMessagesPanel messages;

	public CASMenuBar getCASMenuBar() {
		return menuBar;
	}
	
	public CASJToolBar getToolbar() {
		return toolbar;
	}

	public CASStateContainerPanel getStateContainer() {
		return stateContainer;
	}
	
	public CASPopulationPanel getPopulationPanel() {
		return populationPanel;
	}

	public CASMessagesPanel getMessages() {
		return messages;
	}	
	
	IAutomatonController controller;
	
	public CASFrame(IAutomatonController controller) {
		this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        this.setTitle("CAS");
        this.setMinimumSize(new Dimension());
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        
    	this.menuBar = new CASMenuBar(this.controller);
    	this.toolbar = new CASJToolBar(this.controller);
        this.stateContainer = new CASStateContainerPanel(this.controller);
        this.stateScrollPane = new JScrollPane(this.stateContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.stateScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.populationPanel = new CASPopulationPanel(this.controller);
        this.populationScrollPane = new JScrollPane(this.populationPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        this.messages = new CASMessagesPanel(this.controller);


        this.setJMenuBar(menuBar);
        this.add(this.toolbar, BorderLayout.NORTH);
        this.add(this.stateScrollPane, BorderLayout.WEST);
        this.add(this.messages, BorderLayout.SOUTH); 
        this.add(this.populationScrollPane,  BorderLayout.CENTER);
        
        
        
        this.pack();
        this.setVisible(true);
    }
    
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(750,500);
    }
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(500,220);
	}
}
