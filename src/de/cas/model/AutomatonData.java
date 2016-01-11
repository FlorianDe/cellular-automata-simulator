package de.cas.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AutomatonData {

	private String automatonName;
	private int numberOfStates;
	private Cell[][] population;

	public AutomatonData() {}

	public AutomatonData(Automaton automaton) {
		this(automaton.getClass().getSimpleName(), automaton.getStates().getNumberOfStates(), automaton.getPopulation());
	}
	
	public AutomatonData(String name, int numberOfStates, Cell[][] pop) {
		this.numberOfStates = numberOfStates;
		this.population = pop;
		this.automatonName = name;
	}

	@XmlAttribute
	public void setAutomatonName(String automatonName) {
		this.automatonName = automatonName;
	}
	@XmlElement
	public void setNumberOfStates(int numberOfStates) {
		this.numberOfStates = numberOfStates;
	}
	@XmlElement
	public void setPopulation(Cell[][] population) {
		this.population = population;
	}
	
	public String getAutomatonName() {
		return automatonName;
	}
	public int getNumberOfStates() {
		return numberOfStates;
	}
	public Cell[][] getPopulation() {
		return population;
	}
}

