package hardware.user;

import java.util.Arrays;
import javax.swing.JButton;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import hardware.buttons.Cancel;
import hardware.buttons.Exit;
import hardware.buttons.Finish;
import hardware.buttons.Power;
import hardware.buttons.PrinterPower;
import hardware.buttons.Reset;
import hardware.buttons.Start;
import hardware.buttons.Time;
import hardware.buttons.Tog;
import hardware.buttons.Trig;

/**
 * @author Andy & Matt
 * The Button class, apart of the hardware.user package, is the super class for all buttons in the ChronoTimer.
 * The
 */
public abstract class Button {     //Abstract Factory

	public static Button[] buttons = new Button[9];
	protected String name;
	protected final int btnId;
	protected boolean isOn;
	private int DEFAULT_MIN = 0;
	private int DEFAULT_MAX = 2;
	private int[] validNumbers;

	//BUTTONS!//
	
	//WE MADE IT LIKE THIS SO WHEN WE ARE DOING THE GUI WE ARE BASICALLY SET!

	private static Button power = new Power(0);
	private static Button reset = new Reset(1);
	private static Button start = new Start(2);
	private static Button finish = new Finish(3);
	private static Button tog = new Tog(4);
	private static Button trig = new Trig(5);
	private static Button exit = new Exit(6);
	private static Button cancel = new Cancel(7);
	private static Button time = new Time(8);
	
	

	/**
	 * @param name of the Button
	 * @param id of the Button
	 * Constructor for Button
	 */
	public Button(String name, int id){

		this.name = name;
		this.btnId = id;
		buttons[id] = this;
	}


	/**
	 * @return true if the Button is on
	 */
	public boolean isOn(){
		return true;
	}

	/**
	 * @param state
	 * sets the boolean isOn value to the parameter state.
	 */
	public void setOn(boolean state){
		isOn = state;
	}

	/**
	 * @return the current time of the system as a string in the form "HH:mm:ss"
	 */
	public String getCurrentTime(){

		return ClockInterface.getCurrentTimeFormatted();
	}

	/**
	 * exits the simulator
	 */
	public void EXIT(){
		System.exit(1);
	}

	/**
	 * @param min
	 * @param max
	 * @return true if min and max are in the range (DEFAULT_MIN, DEFAULT_MAX] and min < max
	 * 
	 * Checks that the parameters min and max are in range.
	 */
	private boolean rangeNumber(int min, int max){

		if(min > DEFAULT_MAX || max > DEFAULT_MAX || min > max || min <= DEFAULT_MIN || max <= DEFAULT_MIN)
			return false;

		return true;
	}

	/**
	 * @param nbrs - the array
	 * sorts the nbrs array and checks if the smallest int and biggest int in nbrs[] is in range,
	 * then sets the field validNumbers to the sorted array.
	 */
	public void validateAndSetNumbers(int[] nbrs){

		nbrs = selectionSort(nbrs);

		if(rangeNumber(nbrs[0], nbrs[nbrs.length - 1]))
			this.validNumbers = nbrs;

	}

	/**
	 * @param number - the number to search for
	 * @return true if number is in the validNumbers array
	 */
	public boolean isValidNumber(int number){

		int index = Arrays.binarySearch(this.validNumbers, number);

		return index == -1 ? false : true;

	}

	/**
	 * @param arr - the array to sort
	 * @return sorted array
	 * 
	 * simple selection sort that sorts arr in ascending order.
	 */
	private static int[] selectionSort(int[] arr){

		for (int i = 0; i < arr.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < arr.length; j++){
				if (arr[j] < arr[index])
					index = j;
				
			}
			int smallerNumber = arr[index];  
			arr[index] = arr[i];
			arr[i] = smallerNumber;
		}

		return arr;
	}

	/**
	 * @return ID of this button
	 */
	public int getBtnId() {
		return this.btnId;
	}
	
}
