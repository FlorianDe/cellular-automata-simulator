package de.cas.controller;

import de.cas.model.Automaton;
import de.cas.model.SimulationModel;
import de.cas.model.PopulationModel;
import de.cas.view.CASFrame;

public interface IAutomatonController {
	public CASFrame getView();
	public Automaton getAutomatonModel();
	public PopulationModel getPopulationModel();
	public SimulationModel getSimulationModel();
}
