package de.cas.controller;

import de.cas.controller.properties.CASLanguageBundle;
import de.cas.controller.properties.CASSettings;
import de.cas.model.Automaton;
import de.cas.model.SimulationModel;
import de.cas.util.Lang;
import de.cas.model.PopulationModel;
import de.cas.view.CASFrame;

public class AutomatonController implements IAutomatonController{
	private Automaton automaton;
	private CASFrame view;
	private PopulationModel populationModel;
	private SimulationModel simulationModel;
	private CASLanguageBundle languageBundle;

	@Override
	public Automaton getAutomatonModel() {
		return automaton;
	}
	@Override
	public void setAutomatonModel(Automaton automaton) {
		if(automaton!=null){
			this.automaton = automaton;
		}
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
		return populationModel;
	}
	@Override
	public void setPopulationModel(PopulationModel populationModel) {
		this.populationModel = populationModel;
	}
	
	
	@Override
	public SimulationModel getSimulationModel() {
		return simulationModel;
	}
	public void setSimulationModel(SimulationModel simulationModel) {
		this.simulationModel = simulationModel;
	}

	public CASLanguageBundle getLanguageBundle() {
		return languageBundle;
	}
	public void setLanguageBundle(CASLanguageBundle languageBundle) {
		this.languageBundle = languageBundle;
	}
	
	public AutomatonController(Automaton automaton){
		this.automaton = automaton;
		this.populationModel = new PopulationModel();
		this.simulationModel = new SimulationModel(this);
		this.languageBundle = new CASLanguageBundle(CASSettings.getInstance().getProperty(CASSettings.Property.LANGUAGE));
	}
	
	@Override
	public synchronized void exitSimulator(){
		this.getSimulationModel().stopThread();
		Lang.println(this.getAutomatonModel(),"Automaton closed: %s", this.getAutomatonModel().getClass().getSimpleName());
		if(Automaton.getRunningAutomatons().size()<=1){
			System.err.println("\n--> Complete application closed!");
			System.exit(0);
		} else {
			this.getView().dispose();
			Automaton.removeRunningAutomaton(automaton);
		}
	}

}
