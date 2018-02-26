package hardware.user;

import java.util.Arrays;

import entitiesStatic.Clock;
import hardware.buttons.Cancel;
import hardware.buttons.Exit;
import hardware.buttons.Finish;
import hardware.buttons.Power;
import hardware.buttons.Reset;
import hardware.buttons.Start;
import hardware.buttons.Time;
import hardware.buttons.Tog;
import hardware.buttons.Trig;

public abstract class Button {     //Abstract Factory

	public static Button[] buttons = new Button[9];
	protected String name;
	protected final int btnId;
	protected boolean isOn;
	private int DEFAULT_MIN = 0;
	private int DEFAULT_MAX = 2;
	private int[] validNubers;


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


	public Button(String name, int id){

		this.name = name;
		this.btnId = id;
		buttons[id] = this;

	}

	public boolean isOn(){

		return true;

	}

	public void setOn(boolean on){

		isOn = on;

	}

	public String getCurrentTime(){

		return Clock.getFormattedTime();

	}

	public void EXIT(){

		System.exit(1);

	}

	private boolean rangeNumber(int min, int max){

		if(min > DEFAULT_MAX || max > DEFAULT_MAX || min > max || min <= DEFAULT_MIN || max <= DEFAULT_MIN){

			return false;

		}

		return true;

	}

	public void setNumber(int[] nbrs){

		nbrs = selectionSort(nbrs);

		if(rangeNumber(nbrs[0], nbrs[nbrs.length - 1])){

			this.validNubers = nbrs;

		}
	}

	public boolean isValidNumber(int number){

		int index = Arrays.binarySearch(this.validNubers, number);

		return index == -1 ? false : true;

	}


	private static int[] selectionSort(int[] arr){

		for (int i = 0; i < arr.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < arr.length; j++){
				if (arr[j] < arr[index]){
					index = j;
				}
			}
			int smallerNumber = arr[index];  
			arr[index] = arr[i];
			arr[i] = smallerNumber;
		}

		return arr;
	}

	public int getBtnId() {
		return btnId;
	}
}
