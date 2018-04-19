package states;

import java.util.Scanner;
import hardware.user.ButtonHandler;
import interfaces.UI;

/**
 * @author Andy
 * The abstract class State, apart of the states package, is the super class for all of the States of the ChronoTimer system.
 */
public abstract class State {   //State

	private static State current = null;
	protected UI ui;
	private static ButtonHandler btnHandler;
	private static Scanner input;

	/**
	 * @param ui
	 * @param input
	 * Constructor for States that takes UI and Scanner as parameters.
	 */
	public State(UI ui, Scanner input){

		this.ui = ui;
		this.input = input;
	}

	/**
	 * @param state
	 * Sets the current state to the State current.
	 */
	public static void setState(State state){

		current = state;
	}

	/**
	 * @return the current State
	 */
	public static State getState(){

		return current;
	}
	
	/**
	 * Updates the State
	 */
	public abstract void update();
	
	/**
	 * Displays the State
	 */
	public abstract void display();

}