package de.cas.model;

import java.awt.Color;
import java.util.Random;

import de.cas.util.CstmObservable;

public class StateModel  extends CstmObservable{
	public static final int NO_ACTUAL_STATE = -1;
	private volatile int actualState;
	private volatile int numberOfStates;
	private Color[] colors;
	
	public StateModel(int numberOfStates){
		this.numberOfStates = numberOfStates;
		this.actualState = (numberOfStates>0)?0:NO_ACTUAL_STATE;
		this.defineColors();
	}
	
	/**
	 * Liefert die Anzahl an Zuständen des Automaten; gültige Zustände sind
	 * int-Werte zwischen 0 und Anzahl-1
	 *
	 * @return die Anzahl an Zuständen des Automaten
	 */
	public int getNumberOfStates(){
		return numberOfStates;
	}
	
	public int getActualState() {
		return actualState;
	}

	public void setActualState(int actualState) {
		this.actualState = actualState;
		notify(null);
	}

	/**
	 * definiert die Farbrepräsentation der einzelnen Zustände; implementiert
	 * wie folgt: <br>
	 * Anzahl der Zustände = 2: 0 = weiß; 1 = schwarz <br>
	 * Anzahl der Zustände = 3: 0 = weiß; 1 = grau; 2 = schwarz <br>
	 * Ansonsten: Farbzuordnung per Zufall
	 */
	protected void defineColors(){
		if(this.numberOfStates>0){
			colors = new Color[this.numberOfStates];
			colors[0] = Color.WHITE;
			if (this.numberOfStates == 2) {
				colors[1] = Color.BLACK;
			} else if (this.numberOfStates == 3) {
				colors[1] = Color.GRAY;
				colors[2] = Color.BLACK;
			} else {
				Random random = new Random();
				for (int i = 1; i < this.numberOfStates; i++)
					colors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
			}
		}
	}
	
	
	/**
	 * Liefert die Farbrepräsentation eines Zustandes
	 *
	 * @param state der Zustand, dessen Farbrepräsentation geliefert werden soll
	 * @return die Farbrepräsentation des Zustandes state
	 */
	public Color getColor(int state){
		return isValidState(state) ? colors[state] : null;
	}
	
	public int getColorRGBInt(int state){
		return getColor(state).getRGB();
	}
	
	/**
	 * Ändert die Farbrepräsentation eines Zustandes
	 *
	 * @param state der Zustand
	 * @param newColor die neue Farbrepräsentation des Zustandes state
	 */
	public void changeColor(int state, Color newColor){
		synchronized(colors){
			if(isValidState(state)){
				colors[state] = newColor;
				notify(null);
			}
		}
	}
	
	/**
	 * Liefert die Farbrepräsentation alles Zustände des Automaten
	 *
	 * @return die Farbrepräsentation alles Zustände des Automaten
	 */
	public Color[] getColorMapping(){
		return colors;
	}
	
	public boolean isValidState(int count){
		return (0 <= count && count < this.numberOfStates);
	}
	
	public void notify(Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}
}
