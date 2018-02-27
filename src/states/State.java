package states;

import java.util.Scanner;

import hardware.user.ButtonHandler;
import interfaces.UI;

public abstract class State {   //State

	private static State current = null;
	/**
	 * 
	 */
	protected UI ui;
	private static ButtonHandler btnHandler;
	private static Scanner input;

	/**
	 * @param ui
	 * @param input
	 */
	public State(UI ui, Scanner input){

		this.ui = ui;
		this.input = input;
		
	}

	/**
	 * @param state
	 */
	public static void setState(State state){

		current = state;

	}

	/**
	 * @return
	 */
	public static State getState(){

		return current;

	}
	
	
	/**
	 * 
	 */
	public abstract void update();
	/**
	 * 
	 */
	public abstract void display();

}