package de.cas.view.casUI.casMenu;

import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class CASMenuPopulation extends JMenu {

	private static final long serialVersionUID = 1857407324411535407L;
	JMenuExtension jme;
	private JMenuItem menuItemChangeSize;
	private JMenuItem menuItemDelete;
	private JMenuItem menuItemCreate;
	private JMenuItem menuItemTorus;
	private JMenuItem menuItemZoomIn;
	private JMenuItem menuItemZoomOut;
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
	
	public CASMenuPopulation(String name){
		super(name);
		this.jme = new JMenuExtension(ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK);
		this.jme.setInformationJM(this, "STRING_DESCRIPTION");

    	this.menuItemChangeSize = this.jme.createJMenuItem(new JMenuItem("Größe ändern..."), 'G', "STRING_DESCRIPTION", this);
    	this.menuItemDelete = this.jme.createJMenuItem(new JMenuItem("Löschen"), 'C', "STRING_DESCRIPTION", this);
    	this.menuItemCreate = this.jme.createJMenuItem(new JMenuItem("Erzeugen"), 'E', "STRING_DESCRIPTION", this);
    	this.menuItemTorus = this.jme.createJMenuItem(new JCheckBoxMenuItem("Torus"),'T', "STRING_DESCRIPTION", this);
    	this.menuItemTorus.setUI(new StayOpenCheckBoxMenuItemUI());
    	
    	this.addSeparator();
    	
    	this.menuItemZoomIn = this.jme.createJMenuItem(new JMenuItem("Vergrößern"),'I', "STRING_DESCRIPTION", this);
    	this.menuItemZoomOut = this.jme.createJMenuItem(new JMenuItem("Verkleinern"), 'O', "STRING_DESCRIPTION", this);

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
	}
}
