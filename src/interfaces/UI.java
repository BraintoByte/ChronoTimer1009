package interfaces;

import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import main.Simulator;

public class UI {
	
	private Simulator sim;
	private ButtonHandler btnHandler;
	private RaceEventsManager raceManager;
	
	
	public UI(Simulator sim){
		
		this.sim = sim;
		
	}
	
	
	public Simulator getSimulator(){
		return sim;
	}
	
	public void setBtnHandler(ButtonHandler btnHandler) {
		this.btnHandler = btnHandler;
	}
	
	public ButtonHandler getBtnHandler() {
		return btnHandler;
	}
	
	public RaceEventsManager getRaceManager() {
		return raceManager;
	}
	public void setRaceManager(RaceEventsManager raceManager){
		this.raceManager = raceManager;
	}
}