package states.hardware;

import java.util.Scanner;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import states.State;

/**
 * @author Andy & Matt
 * The Idle State, apart of states.hardware, is the State in which the system idles, or waits for the user to input a command.
 */
public class Idle extends State {

	public enum Run_Types {

		IND,
		PARIND,
		GRP,
		PARGRP;

	}

	private boolean isIdle;
	private Scanner input;

	/**
	 * @param ui
	 * @param input
	 * Constructor for Idle that takes UI and Scanner as parameters.
	 */
	public Idle(UI ui, Scanner input) {
		super(ui, input);
		this.input = input;
		this.isIdle = true;
	}

	/* (non-Javadoc)
	 * @see states.State#update()
	 */
	@Override
	public void update() {

		idleWait();
	}

	/* (non-Javadoc)
	 * @see states.State#display()
	 */
	@Override
	public void display() {

		idleWait();

	}

	/**
	 * The idle method for this State which runs continuesly, waiting for user input.
	 */
	protected void idleWait(){

		String str;

		while(true){

			str = input.nextLine();

			InterfaceHandler.inputCommand(str);

			if(!isIdle){

				isIdle = true;
				break;

			}
		}
	}

}