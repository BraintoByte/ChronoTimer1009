package hardware.external;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

import hardware.external.pad.Pad;
import hardware.external.pad.Pads;
import hardware.external.sensors.eye.Eye;
import hardware.external.sensors.eye.Eyes;
import hardware.external.sensors.gate.Gate;
import hardware.external.sensors.gate.Gates;

public class SensorFactory {

	private Gates gates;
	private Eyes eyes;
	private Pads pads;
	private int amountGates;
	private int amountEye;
	private int amountPad;

	/**
	 * 
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
	 * @param totalAmount
	 * @param eye
	 * @param gates
	 * @param pad
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

	public void backToTheSource(Sensor sensor, boolean pad, boolean eye, boolean gate){

		if(pad){

			pads.returnPadToSource((Pad) sensor);

		}else if(eye){

			eyes.returnEyeToSource((Eye) sensor);

		}else if(gate){

			gates.returnGateToSource((Gate) sensor);

		}
	}



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


	public Pad findPad(int pad) throws IllegalArgumentException {

		Pad temp = pads.getPad(pad);

		if(temp == null){

			throw new IllegalArgumentException();

		}

		return temp;

	}

	/**
	 * @param eye
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
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
	 * @param eye
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
	 */
	public Eye findEye(int eye) throws IllegalArgumentException {

		Eye temp = eyes.getEye(eye);

		if(temp == null){

			throw new IllegalArgumentException();

		}

		return temp;

	}


	/**
	 * @param gate
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
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
	 * @param gate
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ConcurrentModificationException
	 */
	public Gate findGate(int gate) throws IllegalArgumentException {

		Gate temp = gates.getGate(gate);

		if(temp == null){

			throw new IllegalArgumentException();

		}

		return temp;

	}

	/**
	 * @return
	 */
	public int getAmountEye() {
		return amountEye;
	}

	/**
	 * @return
	 */
	public int getAmountGates() {
		return amountGates;
	}
}