package de.cas.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import de.cas.controller.IAutomatonController;
import de.cas.model.Automaton;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.dialog.AutomatonEditor;
import de.cas.view.casUI.menu.CASMenuBar;
import de.cas.view.casUI.panel.CASMessagesPanel;
import de.cas.view.casUI.panel.CASPopulationPanel;
import de.cas.view.casUI.panel.CASStateContainerPanel;
import de.cas.view.casUI.toolBar.CASJToolBar;

public class CASFrame extends JFrame implements CstmObserver{
	private static final long serialVersionUID = -1994192886242251349L;
	
	private CASMenuBar menuBar;
	private CASJToolBar toolbar;
	private CASStateContainerPanel stateContainer;
	private JScrollPane stateScrollPane;
	private JScrollPane	populationScrollPane;
	private CASPopulationPanel populationPanel;
	private CASMessagesPanel messages;
	
	private AutomatonEditor automatonEditor;
	private Set<CstmObserver> observers;

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
		this.controller.setView(this);
		this.observers = Collections.synchronizedSet(new HashSet<CstmObserver>());
        this.initializeUI();
    }
	
	
	
    public AutomatonEditor getAutomatonEditor() {
		return automatonEditor;
	}

	private void initializeUI() {
        //this.controller.getAutomatonModel();
		//this.setTitle("CAS["+Automaton.getRunningAutomatons().get(this.controller.getAutomatonModel())+"]");
        this.addToObserverable();
		this.setMinimumSize(new Dimension());
        this.setLayout(new BorderLayout());
        this.addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        		controller.exitSimulator();
        	}
		});
        
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
        this.populationPanel.setScrollPane(populationScrollPane);
       
        this.messages = new CASMessagesPanel(this.controller);


        this.setJMenuBar(menuBar);
        this.add(this.toolbar, BorderLayout.NORTH);
        this.add(this.messages, BorderLayout.SOUTH);

        //Anpassen
        int pspWidth = (int)(this.getPreferredSize().getWidth()-this.stateContainer.getPreferredSize().getWidth())-100;
        this.populationScrollPane.setMinimumSize(new Dimension(pspWidth,0));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        		stateScrollPane, populationScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation((int)this.stateScrollPane.getPreferredSize().getWidth());
        
        //this.add(this.stateScrollPane, BorderLayout.WEST); 
        //this.add(this.populationScrollPane,  BorderLayout.CENTER);
        this.add(splitPane, BorderLayout.CENTER);
        
        
        this.automatonEditor = new AutomatonEditor(controller);
        
        this.update(null, this);
        
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
	
	public Set<CstmObserver> getObservers() {
		return observers;
	}

	public synchronized void removeAllObserverable(IAutomatonController controller){
		for (CstmObserver cstmObserver : this.observers) {
			cstmObserver.removeFromObserverable();
		}
	}
	
	public synchronized void setObserverables(IAutomatonController controller){
		Set<CstmObserver> observersTemp = new HashSet<>(this.observers);
		for (CstmObserver observers : observersTemp) {
			observers.addToObserverable();
			observers.update(null, null);
		}
	}

	@Override
	public void update(CstmObservable o, Object arg) {
		this.setTitle("CAS["+Automaton.getRunningAutomatons().get(this.controller.getAutomatonModel())+"]");
	}

	@Override
	public void removeFromObserverable() {
		this.controller.getAutomatonModel().deleteObserver(this);
	}

	@Override
	public void addToObserverable() {
		this.getObservers().add(this);
		this.controller.getAutomatonModel().addObserver(this);
	}
}
