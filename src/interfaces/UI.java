package interfaces;

import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import main.Simulator;

public class UI {
	
	private Simulator sim;
	private ButtonHandler btnHandler;
	private RaceEventsManager raceManager;
	
	
	/**
	 * @param sim
	 */
	public UI(Simulator sim){
		
		this.sim = sim;
		
	}
	
	
	/**
	 * @return
	 */
	public Simulator getSimulator(){
		return sim;
	}
	
	/**
	 * @param btnHandler
	 */
	public void setBtnHandler(ButtonHandler btnHandler) {
		this.btnHandler = btnHandler;
	}
	
	/**
	 * @return
	 */
	public ButtonHandler getBtnHandler() {
		return btnHandler;
	}
	
	/**
	 * @return
	 */
	public RaceEventsManager getRaceManager() {
		return raceManager;
	}
	/**
	 * @param raceManager
	 */
	public void setRaceManager(RaceEventsManager raceManager){
		this.raceManager = raceManager;
	}
}