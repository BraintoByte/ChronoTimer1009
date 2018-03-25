package hardware.external;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

import hardware.external.sensor.pad.Pad;
import hardware.external.sensor.pad.Pads;
import hardware.external.sensors.eye.Eye;
import hardware.external.sensors.eye.Eyes;
import hardware.external.sensors.gate.Gate;
import hardware.external.sensors.gate.Gates;

/**
 * @author Andy
 * SensorFactory, apart of the hardware.external package, is responsible for generation of all of the sensors.
 * SensorFactory also keeps track of all of the sensors in the system.
 */
public class SensorFactory {

	private Gates gates;
	private Eyes eyes;
	private Pads pads;
	private int amountGates;
	private int amountEye;
	private int amountPad;

	/**
	 * Constructor for SensorFactory
	 */
	public SensorFactory(){

		amountGates = 0;
		amountEye = 0;
		gates = Gates.getSingletonGates();
		eyes = Eyes.getSingletonEyes();
		pads = Pads.getSingletonPads();
	}

	//	public Gate getGate(int gate){
	//		
	//		return gates.getGate(gate);
	//		
	//	}

	/**
	 * @param gate
	 * @param eye
	 * @param pad
	 * @return a stack of the sensor ID's
	 * 
	 * Creates a stack of sensor ID's, either all gates, eyes, or pads depending on the truth values of the parameters.
	 */
	public Stack<Integer> sensorToStack(boolean gate, boolean eye, boolean pad){

		Stack<Integer> temp = new Stack<>();
		int[] tempIntArr;
		int j = 0;

		if(gate){

			Iterator<Gate> it = gates.getGatesIterator();
			tempIntArr = new int[gates.howManyLeft()];

			while(it.hasNext()){

				tempIntArr[j] = it.next().getId();
				j++;

			}

			for(int i = tempIntArr.length - 1; i >= 0; i--){

				temp.push(tempIntArr[i]);
			}

		}else if(eye){

			Iterator<Eye> it = eyes.getEyeIterator();
			tempIntArr = new int[eyes.howManyLeft()];

			while(it.hasNext()){

				tempIntArr[j] = it.next().getId();
				j++;

			}

			for(int i = tempIntArr.length - 1; i >= 0; i--){

				temp.push(tempIntArr[i]);
			}

		}else if(pad){

			Iterator<Pad> it = pads.getPadIterator();
			tempIntArr = new int[pads.howManyLeft()];

			while(it.hasNext()){

				tempIntArr[j] = it.next().getId();
				j++;

			}

			for(int i = tempIntArr.length - 1; i >= 0; i--){

				temp.push(tempIntArr[i]);
			}
		}

		return temp;
	}

	/**
	 * @param eyeAmount
	 * @param gateAmount
	 * @param padAmount
	 * @param eye
	 * @param gates
	 * @param pad
	 * 
	 * Makes <sensor>Amount of sensors according to the parameters.
	 */
	public void makeSensors(int eyeAmount, int gateAmount, int padAmount, boolean eye, boolean gates, boolean pad){

		int lastIndex = 0;

		if(gates){

			for(int i = lastIndex; i < gateAmount; i++){

				Sensor tempGate = new Gate(i);
				lastIndex = i;
			}
			
			amountGates = gateAmount;
		}
		
		int index = lastIndex;
		
		if(eye){

			for(int i = lastIndex; i < index + eyeAmount; i++){

				Sensor tempEye = new Eye(i);
				lastIndex = i;
			}
			
			amountEye = eyeAmount;
		}
		
		index = lastIndex;

		if(pad){

			for(int i = lastIndex; i < index + padAmount; i++){

				Sensor tempPad = new Pad(i);
				lastIndex = i;
			}
			
			amountPad = padAmount;
		}
	}

	//Temporary for number discrimination//

	//	if(eye && gates){
	//	
	//	for(int i = 0; i < totalAmount; i++){
	//		
	//		if(i % 2 == 0){
	//
	//			Sensor tempeye = new Eye(i);
	//			amountEye++;
	//								
	//		}else{
	//			
	//			Sensor tempGate = new Gate(i);
	//			amountGates++;
	//			
	//		}
	//	}
	//	
	//}else{
	//	
	//	for(int i = 0; i < totalAmount; i++){
	//		
	//		if(eye){
	//			
	//			Sensor tempEye = new Eye(i);
	//			amountEye++;
	//			
	//		}else{
	//			
	//			Sensor tempGate = new Gate(i);
	//			amountGates++;
	//			
	//		}
	//	}
	//}

	//END NUMBER DISCRIMINATION//

	/**
	 * @param sensor
	 * @param pad
	 * @param eye
	 * @param gate
	 * 
	 * Puts the sensor back into its associated container class (e.g. if pad = true then sensor is put back into Pads.pads).
	 */
	public void backToTheSource(Sensor sensor, boolean pad, boolean eye, boolean gate){

		if(pad)
			pads.returnPadToSource((Pad) sensor);

		else if(eye)
			eyes.returnEyeToSource((Eye) sensor);

		else if(gate)
			gates.returnGateToSource((Gate) sensor);
		
	}

	/**
	 * @param pad - the ID of the Pad to find
	 * @return the Pad with ID = pad
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
	 * 
	 * Finds the Pad with ID = pad iteratively and returns it.
	 */
	public Pad findPadIteratively(int pad) throws IllegalArgumentException, ConcurrentModificationException {

		Iterator<Pad> it = pads.getPadIterator();

		while(it.hasNext()){

			Pad temp = it.next();

			if(temp.getId() == pad){

				it.remove();
				return temp;
			}
		}

		throw new IllegalArgumentException();

	}

	/**
	 * @param pad - the ID of the Pad to find
	 * @return the Pad with ID = pad
	 * @throws IllegalArgumentException
	 * 
	 * Finds the Pad with ID = pad and returns it.
	 */
	public Pad findPad(int pad) throws IllegalArgumentException {

		Pad temp = pads.getPad(pad);

		if(temp == null)
			throw new IllegalArgumentException();

		return temp;
	}

	/**
	 * @param eye - the ID of the Eye to find
	 * @return the Eye with ID = eye
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
	 * 
	 * Finds the Eye with ID = eye iteratively and returns it.
	 */
	public Eye findEyeIteratively(int eye) throws IllegalArgumentException, ConcurrentModificationException {

		Iterator<Eye> it = eyes.getEyeIterator();

		while(it.hasNext()){

			Eye temp = it.next();

			if(temp.getId() == eye){

				it.remove();
				return temp;
			}
		}

		throw new IllegalArgumentException();
	}

	/**
	 * @param eye - the ID of the Eye to find
	 * @return the Eye with ID = eye
	 * @throws IllegalArgumentException
	 * 
	 * Finds the Eye with ID = eye and returns it.
	 */
	public Eye findEye(int eye) throws IllegalArgumentException {

		Eye temp = eyes.getEye(eye);

		if(temp == null)
			throw new IllegalArgumentException();
		
		return temp;
	}

	/**
	 * @param gate - the ID of the Gate to find
	 * @return the Gate with ID = gate
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
	 * 
	 * Finds the Gate with ID = gate iteratively and returns it.
	 */
	public Gate findGateIteratively(int gate) throws IllegalArgumentException, ConcurrentModificationException {

		Iterator<Gate> it = gates.getGatesIterator();

		while(it.hasNext()){

			Gate temp = it.next();

			if(temp.getId() == gate){

				it.remove();
				return temp;
			}
		}

		throw new IllegalArgumentException();
	}

	/**
	 * @param gate - the ID of the Gate to find
	 * @return the Gate with ID = gate
	 * @throws IllegalArgumentException
	 * 
	 * Finds the Gate with ID = gate and returns it.
	 */
	public Gate findGate(int gate) throws IllegalArgumentException {

		Gate temp = gates.getGate(gate);

		if(temp == null)
			throw new IllegalArgumentException();
		
		return temp;
	}

	/**
	 * @return number of Eye sensors
	 */
	public int getAmountEye() {
		return amountEye;
	}

	/**
	 * @return number of Gate sensors
	 */
	public int getAmountGates() {
		return amountGates;
	}
	
	/**
	 * @return number of Pad sensors
	 */
	public int getAmountPads() {
		return amountPad;
	}
	
}