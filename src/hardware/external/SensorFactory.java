package hardware.external;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

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


	/**
	 * @param totalAmount
	 * @param eye
	 * @param gates
	 * @param pad
	 */
	public void makeSensors(int totalAmount, boolean eye, boolean gates, boolean pad){
		
		
		for(int i = 0; i < totalAmount; i++){
			
			if(gates){
				
				Sensor tempGate = new Gate(i);
				
			}else if(eye){
				
				Sensor tempEye = new Eye(i);
				
			}else if(pad){
				
				Sensor tempPad = new Pad(i);
				
			}else{
				
				break;
				
			}
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
		
		Iterator<Pad> it = pads.getPadIteratively();
		
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