package de.cas.view;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import de.cas.view.casUI.casMenu.CASMenuBar;
import de.cas.view.casUI.casPanel.CASMessagesPanel;
import de.cas.view.casUI.casPanel.CASPopulationPanel;
import de.cas.view.casUI.casPanel.CASStatePanel;
import de.cas.view.casUI.casPanel.CASToolbar;




public class MainFrame extends JFrame {
	private static final long serialVersionUID = -1994192886242251349L;

	public MainFrame() {
        initializeUI();
    }

    private void initializeUI() {
        this.setTitle("CAS");
        this.setMinimumSize(new Dimension(650, 250));
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        this.setJMenuBar(new CASMenuBar());
        this.add(new CASToolbar(), BorderLayout.NORTH);
        this.add(new CASStatePanel(), BorderLayout.WEST);
        
        JScrollPane	scrollPane = new JScrollPane();
     	scrollPane.getViewport().add(new CASPopulationPanel());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(new CASMessagesPanel(), BorderLayout.SOUTH); 
    }
}
