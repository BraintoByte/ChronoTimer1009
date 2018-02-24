package hardware;

import java.util.Arrays;

import states.State;
import states.StateManager;

public abstract class Button {

	public static Button[] buttons = new Button[8];
	protected String name;
	protected final int btnId;
	protected boolean isOn;
	private int DEFAULT_MIN = 0;
	private int DEFAULT_MAX = 2;
	private int[] validNubers;


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
