package states.hardware;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import Utils.Util;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Channels;
import environment.Run.Race;
import exceptions.NoSuchRacerException;
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
	private int channelSelected;
	private boolean displayingTime;

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