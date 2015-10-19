package de.cas.view;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import de.cas.view.casUI.casMenu.CASMenuBar;
import de.cas.view.casUI.casPanel.CASMessagesPanel;
import de.cas.view.casUI.casPanel.CASPopulationPanel;
import de.cas.view.casUI.casPanel.CASStateContainerPanel;
import de.cas.view.casUI.casToolBar.CASToolbar;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = -1994192886242251349L;
	
	private CASMenuBar menuBar;
	private CASToolbar toolbar;
	private CASStateContainerPanel stateContainer;
	private JScrollPane stateScrollPane;
	private JScrollPane	populationScrollPane;
	private CASPopulationPanel populationPanel;
	private CASMessagesPanel messages;

	public MainFrame() {
        initializeUI();
    }

    private void initializeUI() {
        this.setTitle("CAS");
        this.setMinimumSize(new Dimension());
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        
    	this.menuBar = new CASMenuBar();
    	this.toolbar = new CASToolbar();
        this.stateContainer = new CASStateContainerPanel();
        this.stateScrollPane = new JScrollPane(this.stateContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.populationPanel = new CASPopulationPanel();
        this.populationScrollPane = new JScrollPane(this.populationPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.messages = new CASMessagesPanel();


        this.setJMenuBar(menuBar);
        this.add(this.toolbar, BorderLayout.NORTH);
        this.add(this.stateScrollPane, BorderLayout.WEST);
        this.add(this.messages, BorderLayout.SOUTH); 
        this.add(this.populationScrollPane,  BorderLayout.CENTER);
        
        
        this.stateContainer.addState();
        this.stateContainer.addState();
        
        this.pack();
        this.setVisible(true);
    }
    
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(600,450);
    }
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(500,200);
	}

}
