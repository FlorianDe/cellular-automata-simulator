package de.cas.controller.properties;

import java.util.HashSet;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import de.cas.util.CstmObservable;

//Controller & Model!

public class CASLanguageBundle extends CstmObservable{
	private static final String languageFilePath = "properties/language/CASLang"; 
	
	public enum SupportedLocale{
		GERMAN(Locale.GERMAN), 
		ENGLISH(Locale.ENGLISH),
		FRENCH(Locale.FRENCH),
		POLISH(new Locale("pl"));
		
        private final Locale locale;
        public Locale getLocale() {return locale;}
        private static HashSet<Locale> locales = new HashSet<>();
        public static HashSet<Locale> getLocales(){
        	return locales;
        }
		public static Locale getDefault() {
			if(getLocales().contains(Locale.getDefault()))
				return Locale.getDefault();
			return ENGLISH.getLocale();
		}
		
        SupportedLocale(Locale locale) {
            this.locale = locale;
        }
        
        static {
            for (SupportedLocale sl : SupportedLocale.values()) {
            	locales.add(sl.getLocale());
            }
        }
	}
	
	public enum Property implements IProperty{
		//Special
		EMPTY("Empty"),
		LANGUAGE("Language"),
		
		//view.casUI.dialog.SetSizeJDialog
        SETSIZEJDIALOG_LBL_ROWS_TEXT("SetSizeJDialog.lblRows.text"),
        SETSIZEJDIALOG_LBL_COLUMNS_TEXT("SetSizeJDialog.lblColumns.text"),
        SETSIZEJDIALOG_BTN_ACCEPT_TEXT("SetSizeJDialog.btnAccept.text"),
        SETSIZEJDIALOG_BTN_CANCEL_TEXT("SetSizeJDialog.btnCancel.text"),
        
		//view.casUI.menu.CASMenuAutomat
        CASMENUAUTOMAT_MENU_TEXT("CASMenuAutomat.menu.text"),
        CASMENUAUTOMAT_MENU_DESCRIPTION("CASMenuAutomat.menu.description"),
		CASMENUAUTOMAT_MENUITEM_NEW_TEXT("CASMenuAutomat.menuItemNew.text"),
		CASMENUAUTOMAT_MENUITEM_NEW_DESCRIPTION("CASMenuAutomat.menuItemNew.description"),
		CASMENUAUTOMAT_MENUITEM_NEW_ACCELERATOR_KEY("CASMenuAutomat.menuItemNew.acceleratorkey"),
		
		CASMENUAUTOMAT_MENUITEM_LOAD_TEXT("CASMenuAutomat.menuItemLoad.text"),
		CASMENUAUTOMAT_MENUITEM_LOAD_DESCRIPTION("CASMenuAutomat.menuItemLoad.description"),
		CASMENUAUTOMAT_MENUITEM_LOAD_ACCELERATOR_KEY("CASMenuAutomat.menuItemLoad.acceleratorkey"),
		
		CASMENUAUTOMAT_MENUITEM_EDITOR_TEXT("CASMenuAutomat.menuItemEditor.text"),
		CASMENUAUTOMAT_MENUITEM_EDITOR_DESCRIPTION("CASMenuAutomat.menuItemEditor.description"),
		CASMENUAUTOMAT_MENUITEM_EDITOR_ACCELERATOR_KEY("CASMenuAutomat.menuItemEditor.acceleratorkey"),
		
		CASMENUAUTOMAT_MENUITEM_EXIT_TEXT("CASMenuAutomat.menuItemExit.text"),
		CASMENUAUTOMAT_MENUITEM_EXIT_DESCRIPTION("CASMenuAutomat.menuItemExit.description"),
		CASMENUAUTOMAT_MENUITEM_EXIT_ACCELERATOR_KEY("CASMenuAutomat.menuItemExit.acceleratorkey"),
		
		//view.casUI.menu.CASMenuCurrentAutomat
		CASMENUCURRENTAUTOMAT_MENU_TEXT("CASMenuCurrentAutomat.menu.text"),
		
		//view.casUI.menu.CASMenuHelp
		CASMENUHELP_MENU_TEXT("CASMenuHelp.menu.text"),
		CASMENUHELP_MENU_DESCRIPTION("CASMenuHelp.menu.description"),
		
		CASMENUHELP_MENUITEM_HELP_TEXT("CASMenuHelp.menuItemHelp.text"),
		CASMENUHELP_MENUITEM_HELP_DESCRIPTION("CASMenuHelp.menuItemHelp.description"),
		CASMENUHELP_MENUITEM_HELP_ACCELERATOR_KEY("CASMenuHelp.menuItemHelp.acceleratorkey"),
		
		CASMENUHELP_MENUITEM_ABOUT_TEXT("CASMenuHelp.menuItemAbout.text"),
		CASMENUHELP_MENUITEM_ABOUT_DESCRIPTION("CASMenuHelp.menuItemAbout.description"),
		CASMENUHELP_MENUITEM_ABOUT_ACCELERATOR_KEY("CASMenuHelp.menuItemAbout.acceleratorkey"),
		
		CASMENUHELP_SUBMENU_LANGUAGE_TEXT("CASMenuHelp.submenuLanguage.text"),
		CASMENUHELP_SUBMENU_LANGUAGE_DESCRIPTION("CASMenuHelp.submenuLanguage.description"),
		
		
		//view.casUI.menu.CASMenuPopulation
		CASMENUPOPULATION_MENU_TEXT("CASMenuPopulation.menu.text"),
		CASMENUPOPULATION_MENU_DESCRIPTION("CASMenuPopulation.menu.description"),
		CASMENUPOPULATION_MENUITEM_CHANGESIZE_TEXT("CASMenuPopulation.menuItemChangeSize.text"),
		CASMENUPOPULATION_MENUITEM_CHANGESIZE_DESCRIPTION("CASMenuPopulation.menuItemChangeSize.description"),
		CASMENUPOPULATION_MENUITEM_CHANGESIZE_ACCELERATOR_KEY("CASMenuPopulation.menuItemChangeSize.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_DELETE_TEXT("CASMenuPopulation.menuItemDelete.text"),
		CASMENUPOPULATION_MENUITEM_DELETE_DESCRIPTION("CASMenuPopulation.menuItemDelete.description"),
		CASMENUPOPULATION_MENUITEM_DELETE_ACCELERATOR_KEY("CASMenuPopulation.menuItemDelete.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_CREATE_TEXT("CASMenuPopulation.menuItemCreate.text"),
		CASMENUPOPULATION_MENUITEM_CREATE_DESCRIPTION("CASMenuPopulation.menuItemCreate.description"),
		CASMENUPOPULATION_MENUITEM_CREATE_ACCELERATOR_KEY("CASMenuPopulation.menuItemCreate.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_TORUS_TEXT("CASMenuPopulation.menuItemTorus.text"),
		CASMENUPOPULATION_MENUITEM_TORUS_DESCRIPTION("CASMenuPopulation.menuItemTorus.description"),
		CASMENUPOPULATION_MENUITEM_TORUS_ACCELERATOR_KEY("CASMenuPopulation.menuItemTorus.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_ZOOMIN_TEXT("CASMenuPopulation.menuItemZoomIn.text"),
		CASMENUPOPULATION_MENUITEM_ZOOMIN_DESCRIPTION("CASMenuPopulation.menuItemZoomIn.description"),
		CASMENUPOPULATION_MENUITEM_ZOOMIN_ACCELERATOR_KEY("CASMenuPopulation.menuItemZoomIn.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_ZOOMOUT_TEXT("CASMenuPopulation.menuItemZoomOut.text"),
		CASMENUPOPULATION_MENUITEM_ZOOMOUT_DESCRIPTION("CASMenuPopulation.menuItemZoomOut.description"),
		CASMENUPOPULATION_MENUITEM_ZOOMOUT_ACCELERATOR_KEY("CASMenuPopulation.menuItemZoomOut.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_GRIDONOFF_TEXT("CASMenuPopulation.menuItemGridOnOff.text"),
		CASMENUPOPULATION_MENUITEM_GRIDONOFF_DESCRIPTION("CASMenuPopulation.menuItemGridOnOff.description"),
		CASMENUPOPULATION_MENUITEM_GRIDONOFF_ACCELERATOR_KEY("CASMenuPopulation.menuItemGridOnOff.acceleratorkey"),

		CASMENUPOPULATION_SUBMENU_SAVE_TEXT("CASMenuPopulation.submenuSave.text"),
		CASMENUPOPULATION_SUBMENU_SAVE_DESCRIPTION("CASMenuPopulation.submenuSave.description"),

		CASMENUPOPULATION_MENUITEM_SAVEXML_TEXT("CASMenuPopulation.menuItemSaveXML.text"),
		CASMENUPOPULATION_MENUITEM_SAVEXML_DESCRIPTION("CASMenuPopulation.menuItemSaveXML.description"),
		CASMENUPOPULATION_MENUITEM_SAVEXML_ACCELERATOR_KEY("CASMenuPopulation.menuItemSaveXML.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_SAVESERIALIZED_TEXT("CASMenuPopulation.menuItemSaveSerialized.text"),
		CASMENUPOPULATION_MENUITEM_SAVESERIALIZED_DESCRIPTION("CASMenuPopulation.menuItemSaveSerialized.description"),
		CASMENUPOPULATION_MENUITEM_SAVESERIALIZED_ACCELERATOR_KEY("CASMenuPopulation.menuItemSaveSerialized.acceleratorkey"),

		CASMENUPOPULATION_SUBMENU_LOAD_TEXT("CASMenuPopulation.submenuLoad.text"),
		CASMENUPOPULATION_SUBMENU_LOAD_DESCRIPTION("CASMenuPopulation.submenuLoad.description"),

		CASMENUPOPULATION_MENUITEM_LOADXML_TEXT("CASMenuPopulation.menuItemLoadXML.text"),
		CASMENUPOPULATION_MENUITEM_LOADXML_DESCRIPTION("CASMenuPopulation.menuItemLoadXML.description"),
		CASMENUPOPULATION_MENUITEM_LOADXML_ACCELERATOR_KEY("CASMenuPopulation.menuItemLoadXML.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_LOADSERIALIZED_TEXT("CASMenuPopulation.menuItemLoadSerialized.text"),
		CASMENUPOPULATION_MENUITEM_LOADSERIALIZED_DESCRIPTION("CASMenuPopulation.menuItemLoadSerialized.description"),
		CASMENUPOPULATION_MENUITEM_LOADSERIALIZED_ACCELERATOR_KEY("CASMenuPopulation.menuItemLoadSerialized.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_PRINT_TEXT("CASMenuPopulation.menuItemPrint.text"),
		CASMENUPOPULATION_MENUITEM_PRINT_DESCRIPTION("CASMenuPopulation.menuItemPrint.description"),
		CASMENUPOPULATION_MENUITEM_PRINT_ACCELERATOR_KEY("CASMenuPopulation.menuItemPrint.acceleratorkey"),

		CASMENUPOPULATION_SUBMENU_EXPORT_TEXT("CASMenuPopulation.submenuExport.text"),
		CASMENUPOPULATION_SUBMENU_EXPORT_DESCRIPTION("CASMenuPopulation.submenuExport.description"),

		CASMENUPOPULATION_MENUITEM_EXPORTGIF_TEXT("CASMenuPopulation.menuItemExportGIF.text"),
		CASMENUPOPULATION_MENUITEM_EXPORTGIF_DESCRIPTION("CASMenuPopulation.menuItemExportGIF.description"),
		CASMENUPOPULATION_MENUITEM_EXPORTGIF_ACCELERATOR_KEY("CASMenuPopulation.menuItemExportGIF.acceleratorkey"),

		CASMENUPOPULATION_MENUITEM_EXPORTPNG_TEXT("CASMenuPopulation.menuItemExportPNG.text"),
		CASMENUPOPULATION_MENUITEM_EXPORTPNG_DESCRIPTION("CASMenuPopulation.menuItemExportPNG.description"),
		CASMENUPOPULATION_MENUITEM_EXPORTPNG_ACCELERATOR_KEY("CASMenuPopulation.menuItemExportPNG.acceleratorkey"),

		
		//view.casUI.menu.CASMenuSimulation
		CASMENUSIMULATION_MENU_TEXT("CASMenuSimulation.menu.text"),
		CASMENUSIMULATION_MENU_DESCRIPTION("CASMenuSimulation.menu.description"),
		
		CASMENUSIMULATION_MENUITEM_STEP_TEXT("CASMenuSimulation.menuItemStep.text"),
		CASMENUSIMULATION_MENUITEM_STEP_DESCRIPTION("CASMenuSimulation.menuItemStep.description"),
		CASMENUSIMULATION_MENUITEM_STEP_ACCELERATOR_KEY("CASMenuSimulation.menuItemStep.acceleratorkey"),

		CASMENUSIMULATION_MENUITEM_START_TEXT("CASMenuSimulation.menuItemStart.text"),
		CASMENUSIMULATION_MENUITEM_START_DESCRIPTION("CASMenuSimulation.menuItemStart.description"),
		CASMENUSIMULATION_MENUITEM_START_ACCELERATOR_KEY("CASMenuSimulation.menuItemStart.acceleratorkey"),

		CASMENUSIMULATION_MENUITEM_STOP_TEXT("CASMenuSimulation.menuItemStop.text"),
		CASMENUSIMULATION_MENUITEM_STOP_DESCRIPTION("CASMenuSimulation.menuItemStop.description"),
		CASMENUSIMULATION_MENUITEM_STOP_ACCELERATOR_KEY("CASMenuSimulation.menuItemStop.acceleratorkey");
		
        private final String key;
        public String getKey() {return key;}
        Property(String key) {
            this.key = key;
        }
    }

	private PropertyResourceBundle languageBundle;
	private Locale locale;

	public CASLanguageBundle(){
		this(SupportedLocale.GERMAN.getLocale().getLanguage());
	}
	
	public CASLanguageBundle(String localeStr){
		this(new Locale(localeStr));
	}
	public CASLanguageBundle(Locale locale){
		if(locale!=null && SupportedLocale.getLocales().contains(locale)){
			this.locale = locale;
		} else {
			this.locale = SupportedLocale.getDefault();
		}
		this.languageBundle = (PropertyResourceBundle) ResourceBundle.getBundle(languageFilePath, this.locale);
		notify(null);
	}
	
	public void setLanguageBundle(String localeStr){
		setLanguageBundle(new Locale(localeStr));
	}
	public void setLanguageBundle(Locale locale){
		if(!this.locale.equals(locale) && SupportedLocale.getLocales().contains(locale)){
			this.locale = locale;
			this.languageBundle = (PropertyResourceBundle) ResourceBundle.getBundle(languageFilePath, this.locale);
		}	
		notify(null);
	}
	
	public String getValue(Property property) {
		return this.languageBundle.getString(property.getKey());
	}

	public static HashSet<Locale> getSupportedLocales() {
		return SupportedLocale.getLocales();
	}
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}