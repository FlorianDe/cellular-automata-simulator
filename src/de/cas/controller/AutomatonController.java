package de.cas.controller;

import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.PropertiesManager;
import de.cas.model.Automaton;
import de.cas.model.SimulationModel;
import de.cas.model.PopulationModel;
import de.cas.view.CASFrame;

public class AutomatonController implements IAutomatonController{
	private Automaton automaton;
	private CASFrame view;
	private PopulationModel zoomFactor;
	private SimulationModel simulationModel;
	private PropertiesManager propertiesManager;

	@Override
	public Automaton getAutomatonModel() {
		return automaton;
	}
	public void setModel(Automaton automaton) {
		this.automaton = automaton;
	}
	
	@Override
	public CASFrame getView() {
		return view;
	}
	public void setView(CASFrame view){
		this.view = view;
	}
	
	@Override
	public PopulationModel getPopulationModel() {
		return zoomFactor;
	}
	public void setZoomFactor(PopulationModel zoomFactor) {
		this.zoomFactor = zoomFactor;
	}
	
	
	@Override
	public SimulationModel getSimulationModel() {
		return simulationModel;
	}
	public void setSimulationModel(SimulationModel simulationModel) {
		this.simulationModel = simulationModel;
	}
	
	public PropertiesManager getPropertiesManager() {
		return propertiesManager;
	}
	public void setPropertiesManager(PropertiesManager propertiesManager) {
		this.propertiesManager = propertiesManager;
	}


	public AutomatonController(Automaton automaton){
		this.automaton = automaton;
		this.zoomFactor = new PopulationModel();
		this.simulationModel = new SimulationModel(this);
		this.propertiesManager = new PropertiesManager(this);
	}
	
	@Override
	public void exitSimulator(){
		System.exit(0);
	}
}
