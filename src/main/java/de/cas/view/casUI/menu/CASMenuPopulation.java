package de.cas.view.casUI.menu;

import java.awt.event.ActionEvent;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.DeserializePopulationListener;
import de.cas.controller.listener.population.GridOnOffListener;
import de.cas.controller.listener.population.SavePopulationAsImageListener;
import de.cas.controller.listener.population.SerializePopulationListener;
import de.cas.controller.listener.population.SetSizeListener;
import de.cas.controller.listener.population.TorusListener;
import de.cas.controller.listener.population.ZoomInListener;
import de.cas.controller.listener.population.ZoomOutListener;
import de.cas.controller.properties.CASLanguageBundle.Property;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.component.CASJCheckBox;
import de.cas.view.casUI.component.CASJMenu;
import de.cas.view.casUI.component.CASJMenuItem;
import de.cas.view.casUI.util.CASFileFilter;

public class CASMenuPopulation extends CASJMenu implements CstmObserver {

	private static final long serialVersionUID = 1857407324411535407L;
	private CASJMenuItem menuItemChangeSize;
	private CASJMenuItem menuItemDelete;
	private CASJMenuItem menuItemCreate;
	private CASJCheckBox menuItemTorus;
	private CASJMenuItem menuItemZoomIn;
	private CASJMenuItem menuItemZoomOut;
	private CASJCheckBox menuItemGridOnOff;
	private CASJMenu submenuSave;
	private CASJMenuItem menuItemSaveXML;
	private CASJMenuItem menuItemSaveSerialized;
	private CASJMenu submenuLoad;
	private CASJMenuItem menuItemLoadXML;
	private CASJMenuItem menuItemLoadSerialized;
	private CASJMenuItem menuItemPrint;
	private CASJMenuItem menuItemExportImage;
	
	
	IAutomatonController controller;
	
	public CASMenuPopulation(IAutomatonController controller, Property propertyText, Property propertyDescription) {
		super(controller, propertyText, propertyDescription);
		this.controller = controller;
		this.acceleratorModifiers = ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK;

    	this.menuItemChangeSize = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_CHANGESIZE_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_CHANGESIZE_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_CHANGESIZE_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemChangeSize.addActionListener(new SetSizeListener(this.controller));
    	
    	this.menuItemDelete = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_DELETE_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_DELETE_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_DELETE_DESCRIPTION,
    			this.acceleratorModifiers);
    	
    	this.menuItemCreate = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_CREATE_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_CREATE_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_CREATE_DESCRIPTION,
    			this.acceleratorModifiers);
    	
    	this.menuItemTorus = new CASJCheckBox(controller,
    			Property.CASMENUPOPULATION_MENUITEM_TORUS_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_TORUS_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_TORUS_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemTorus.addActionListener(new TorusListener(controller));

    	this.menuItemZoomIn = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_ZOOMIN_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_ZOOMIN_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_ZOOMIN_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemZoomIn.addActionListener(new ZoomInListener(this.controller));

    	this.menuItemZoomOut = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_ZOOMOUT_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_ZOOMOUT_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_ZOOMOUT_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemZoomOut.addActionListener(new ZoomOutListener(this.controller));
    	
    	this.menuItemGridOnOff = new CASJCheckBox(controller,
    			Property.CASMENUPOPULATION_MENUITEM_GRIDONOFF_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_GRIDONOFF_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_GRIDONOFF_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemGridOnOff.addItemListener(new GridOnOffListener(this.controller));

    	this.submenuSave = new CASJMenu(controller, 
    			Property.CASMENUPOPULATION_SUBMENU_SAVE_TEXT, 
    			Property.CASMENUPOPULATION_SUBMENU_SAVE_DESCRIPTION);
    	
    	this.menuItemSaveXML = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_SAVEXML_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_SAVEXML_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_SAVEXML_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemSaveXML.addActionListener(new SerializePopulationListener(controller, CASFileFilter.xmlSuffixFilter));
    	
    	this.menuItemSaveSerialized = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_SAVESERIALIZED_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_SAVESERIALIZED_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_SAVESERIALIZED_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemSaveSerialized.addActionListener(new SerializePopulationListener(controller, CASFileFilter.serializedSuffixFilter));
    	
    	this.submenuLoad = new CASJMenu(controller, 
    			Property.CASMENUPOPULATION_SUBMENU_LOAD_TEXT, 
    			Property.CASMENUPOPULATION_SUBMENU_LOAD_DESCRIPTION);
    	
    	this.menuItemLoadXML = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_LOADXML_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_LOADXML_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_LOADXML_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemLoadXML.addActionListener(new DeserializePopulationListener(controller, CASFileFilter.xmlSuffixFilter));

    	this.menuItemLoadSerialized = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_LOADSERIALIZED_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_LOADSERIALIZED_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_LOADSERIALIZED_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemLoadSerialized.addActionListener(new DeserializePopulationListener(controller, CASFileFilter.serializedSuffixFilter));

    	this.menuItemPrint = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_PRINT_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_PRINT_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_PRINT_DESCRIPTION,
    			this.acceleratorModifiers);
    	
    	this.menuItemExportImage = new CASJMenuItem(controller,
    			Property.CASMENUPOPULATION_MENUITEM_EXPORTIMAGE_TEXT,
    			Property.CASMENUPOPULATION_MENUITEM_EXPORTIMAGE_ACCELERATOR_KEY,
    			Property.CASMENUPOPULATION_MENUITEM_EXPORTIMAGE_DESCRIPTION,
    			this.acceleratorModifiers);
    	this.menuItemExportImage.addActionListener(new SavePopulationAsImageListener(controller));
    	

    	this.add(menuItemChangeSize);
    	this.add(menuItemDelete);
    	this.add(menuItemCreate);
    	this.add(menuItemTorus);
    	this.addSeparator();
    	this.add(menuItemZoomIn);
    	this.add(menuItemZoomOut);
    	this.add(menuItemGridOnOff);
    	this.addSeparator();
    	this.add(submenuSave);
    	this.submenuSave.add(menuItemSaveXML);
    	this.submenuSave.add(menuItemSaveSerialized);
    	this.add(submenuLoad);
    	this.submenuLoad.add(menuItemLoadXML);
    	this.submenuLoad.add(menuItemLoadSerialized);
    	this.addSeparator();
    	this.add(menuItemPrint);
    	this.add(menuItemExportImage);
    	
    	update(null, this);
	}
	
	@Override
	public void update(CstmObservable arg0, Object arg1) {
		super.update(arg0, arg1);
		this.menuItemTorus.setSelected(this.controller.getAutomatonModel().isTorus());
		this.menuItemGridOnOff.setSelected(this.controller.getPopulationModel().isDrawCellRect());
		this.menuItemZoomOut.setEnabled(!this.controller.getPopulationModel().isMinimum());
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void removeFromObserverable(){
		super.removeFromObserverable();
		super.controller.getAutomatonModel().deleteObserver(this);
    }
	
	@Override
	public void addToObserverable(){
		super.addToObserverable();
		super.controller.getView().getObservers().add(this);
		super.controller.getAutomatonModel().addObserver(this);
    }
}
