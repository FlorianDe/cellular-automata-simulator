package de.cas.view;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import de.cas.model.Automaton;
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

	public CASToolbar getToolbar() {
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
	
	public MainFrame(Automaton automaton) {
        initializeUI(automaton);
    }

    private void initializeUI(Automaton automaton) {
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
        this.stateScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.populationPanel = new CASPopulationPanel(automaton);
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
		return new Dimension(500,220);
	}

}
