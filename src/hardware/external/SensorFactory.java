package hardware.external;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hardware.external.sensors.eye.Eye;
import hardware.external.sensors.eye.Eyes;
import hardware.external.sensors.gate.Gate;
import hardware.external.sensors.gate.Gates;

public class SensorFactory {

	private Gates gates;
	private Eyes eyes;
	private int amountGates;
	private int amountEye;

	public SensorFactory(){

		amountGates = 0;
		amountEye = 0;
		gates = Gates.getSingletonGates();
		eyes = Eyes.getSingletonEyes();

	}


	//	public Gate getGate(int gate){
	//		
	//		return gates.getGate(gate);
	//		
	//	}


	public void makeSensors(int totalAmount, boolean eye, boolean gates){
		
		if(eye && gates){
			
			for(int i = 0; i < totalAmount; i++){
				
				if(i % 2 == 0){
					
					Gate tempGate = new Gate(i);
					amountGates++;
					
				}else{
					
					Eye tempeye = new Eye(i);
					amountEye++;
					
				}
			}
			
		}else{
			
			for(int i = 0; i < totalAmount; i++){
				
				if(eye){
					
					Eye tempEye = new Eye(i);
					amountEye++;
					
				}else{
					
					Gate tempGate = new Gate(i);
					amountGates++;
					
				}
			}
		}
	}
	
	public Eye findEyeIteratively(int eye) throws IllegalArgumentException, ConcurrentModificationException {
		
		Iterator<Eye> it = eyes.getEyeIterator();
		
		while(it.hasNext()){
			
			Eye temp = it.next();
			
			if(temp.getId() == eye){
				
				return temp;
				
			}
		}
		
		throw new IllegalArgumentException();
		
	}
	
	public Eye findEye(int eye) throws IllegalArgumentException, ConcurrentModificationException {
		
		Eye temp = eyes.getEye(eye);

		if(temp == null){

			throw new IllegalArgumentException();

		}

		return temp;

	}
	

	public Gate findGateIteratively(int gate) throws IllegalArgumentException, ConcurrentModificationException {

		Iterator<Gate> it = gates.getGatesIterator();

		while(it.hasNext()){

			Gate temp = it.next();

			if(temp.getId() == gate){

				return temp;

			}
		}

		throw new IllegalArgumentException();

	}

	public Gate findGate(int gate) throws IllegalArgumentException, ConcurrentModificationException {

		Gate temp = gates.getGate(gate);

		if(temp == null){

			throw new IllegalArgumentException();

		}

		return temp;

	}
	
	public int getAmountEye() {
		return amountEye;
	}
	
	public int getAmountGates() {
		return amountGates;
	}
}