package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.GridOnOffListener;
import de.cas.controller.listener.population.SetSizeListener;
import de.cas.controller.listener.population.TorusListener;
import de.cas.controller.listener.population.ZoomInListener;
import de.cas.controller.listener.population.ZoomOutListener;

public class CASMenuPopulation extends JMenu implements Observer {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	private JMenuItem menuItemChangeSize;
	private JMenuItem menuItemDelete;
	private JMenuItem menuItemCreate;
	private JMenuItem menuItemTorus;
	private JMenuItem menuItemZoomIn;
	private JMenuItem menuItemZoomOut;
	private JMenuItem menuItemGridOnOff;
	private JMenuItem menuItemSaveXML;
	private JMenuItem menuItemSaveSerialized;
	private JMenuItem menuItemLoadXML;
	private JMenuItem menuItemLoadSerialized;
	private JMenuItem menuItemPrint;
	private JMenuItem menuItemExportGIF;
	private JMenuItem menuItemExportPNG;
	
	private JMenu submenuSave;
	private JMenu submenuLoad;
	private JMenu submenuExport;
	
	IAutomatonController controller;
	
	public CASMenuPopulation(String name, IAutomatonController controller){
		super(name);
		this.controller = controller;
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");

    	this.menuItemChangeSize = this.jme.createJMenuItem(new JMenuItem("Größe ändern..."), 'G', "STRING_DESCRIPTION", this);
    	this.menuItemChangeSize.addActionListener(new SetSizeListener(this.controller));
    	this.menuItemDelete = this.jme.createJMenuItem(new JMenuItem("Löschen"), 'C', "STRING_DESCRIPTION", this);
    	this.menuItemCreate = this.jme.createJMenuItem(new JMenuItem("Erzeugen"), 'E', "STRING_DESCRIPTION", this);
    	this.menuItemTorus = this.jme.createJMenuItem(new JCheckBoxMenuItem("Torus An/Aus"),'T', "STRING_DESCRIPTION", this);
    	this.menuItemTorus.setUI(new StayOpenCheckBoxMenuItemUI());
    	this.menuItemTorus.addActionListener(new TorusListener(controller));
    	
    	this.addSeparator();
    	
    	this.menuItemZoomIn = this.jme.createJMenuItem(new JMenuItem("Vergrößern"),'I', "STRING_DESCRIPTION", this);
    	this.menuItemZoomIn.addActionListener(new ZoomInListener(this.controller));
    	this.menuItemZoomOut = this.jme.createJMenuItem(new JMenuItem("Verkleinern"), 'O', "STRING_DESCRIPTION", this);
    	this.menuItemZoomOut.addActionListener(new ZoomOutListener(this.controller));
    	this.menuItemGridOnOff = this.jme.createJMenuItem(new JCheckBoxMenuItem("Grid An/Aus"),'R', "STRING_DESCRIPTION", this);
    	this.menuItemGridOnOff.setUI(new StayOpenCheckBoxMenuItemUI());
    	this.menuItemGridOnOff.addItemListener(new GridOnOffListener(this.controller));
    	
    	this.addSeparator();

    	this.submenuSave = this.jme.createJMenu(new JMenu("Speichern"), "STRING_DESCRIPTION", this);
    		this.menuItemSaveXML = this.jme.createJMenuItem(new JMenuItem("XML"), 'X', "STRING_DESCRIPTION", this.submenuSave);
    		this.menuItemSaveSerialized = this.jme.createJMenuItem(new JMenuItem("Serialisiert"), 'R', "STRING_DESCRIPTION", this.submenuSave);

    	this.submenuLoad = this.jme.createJMenu(new JMenu("Laden"), "STRING_DESCRIPTION", this);
    		this.menuItemLoadXML = this.jme.createJMenuItem(new JMenuItem("XML"), 'X', "STRING_DESCRIPTION", this.submenuLoad);
    		this.menuItemLoadSerialized = this.jme.createJMenuItem(new JMenuItem("Serialisiert"), 'R', "STRING_DESCRIPTION", this.submenuLoad);

    	this.addSeparator();

    	this.menuItemPrint = this.jme.createJMenuItem(new JMenuItem("Drucken..."), 'P', "STRING_DESCRIPTION", this);

    	this.submenuExport = this.jme.createJMenu(new JMenu("Als Bild speichern"),"STRING_DESCRIPTION", this);
    		this.menuItemExportGIF = this.jme.createJMenuItem(new JMenuItem("GIF"), 'G', "STRING_DESCRIPTION", this.submenuExport);
    		this.menuItemExportPNG = this.jme.createJMenuItem(new JMenuItem("PNG"), 'P', "STRING_DESCRIPTION", this.submenuExport);
	
    	update(null, this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		this.menuItemTorus.setSelected(this.controller.getAutomatonModel().isTorus());
		this.menuItemGridOnOff.setSelected(this.controller.getPopulationModel().isDrawCellRect());
		this.revalidate();
		this.repaint();
	}
}
