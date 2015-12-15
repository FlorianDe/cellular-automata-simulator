package de.cas.controller;

import de.cas.controller.properties.CASLanguageBundle;
import de.cas.model.Automaton;
import de.cas.model.SimulationModel;
import de.cas.model.PopulationModel;
import de.cas.view.CASFrame;

public interface IAutomatonController {
	public CASFrame getView();
	public void setView(CASFrame view);
	public Automaton getAutomatonModel();
	public void setAutomatonModel(Automaton automaton);
	public PopulationModel getPopulationModel();
	public void setPopulationModel(PopulationModel populationModel);
	public SimulationModel getSimulationModel();
	public CASLanguageBundle getLanguageBundle();

	
	public void exitSimulator();
}
