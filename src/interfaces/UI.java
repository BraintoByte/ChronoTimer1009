package interfaces;

import Utils.Printer;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import hardware.user.UserGraphical;
import main.Simulator;

/**
 * @author Andy & Matt
 * The UI class, or User Interface, apart of the Interfaces package, is at the top layer of the system.
 * It is responsible for setting the Simulator, RaceEventManager, and ButtonHandler among other things.  
 */
public class UI {
	
	private Simulator sim;
	private ButtonHandler btnHandler;
	private RaceEventsManager raceManager;
	private int[] validChannels = { 1, 2, 3, 4 };
	private UserGraphical userInterface;
	
	/**
	 * @param sim
	 * Constructor for UI that takes a Simulator as a parameter
	 */
	public UI(Simulator sim){
		
		this.sim = sim;

	}
	
	/**
	 * @return the Simulator associated with this UI
	 */
	public Simulator getSimulator(){
		return sim;
	}
	
	public void setUserInterface(UserGraphical userInterface) {
		this.userInterface = userInterface;
	}
	
	public UserGraphical getUserInterface() {
		return userInterface;
	}
	
	/**
	 * @param btnHandler
	 * Sets the Button Handler to btnHandler
	 */
	public void setBtnHandler(ButtonHandler btnHandler) {
		this.btnHandler = btnHandler;
	}
	
	/**
	 * @return the ButtonHandler associated with this UI
	 */
	public ButtonHandler getBtnHandler() {
		return btnHandler;
	}
	
	/**
	 * @return the RaceEventManager associated with this UI
	 */
	public RaceEventsManager getRaceManager() {
		return raceManager;
	}
	/**
	 * @param raceManager
	 * Sets the raceEventManager to raceManager
	 */
	public void setRaceManager(RaceEventsManager raceManager){
		this.raceManager = raceManager;
	}
	
	/**
	 * @return an array of the valid channel's ID numbers (i.e. [1, 8])
	 */
	public int[] getValidChannels() {
		return validChannels;
	}
	
}