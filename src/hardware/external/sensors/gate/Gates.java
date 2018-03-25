package hardware.external.sensors.gate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andy
 * The Gates class, apart of the the hardware.external.sensor.gate package, is a container class for all of the Gate sensors.
 */
public class Gates {
	
	// container for pad sensors
	private List<Gate> gates;
	
	/**
	 * Constructor for Gates
	 */
	private Gates(){
		
		gates = new ArrayList<>();
	}
	
	/**
	 * @author Andy
	 * Makes the singleton
	 */
	private static class SingletonGates {
		
		private static final Gates gatesSingleton = new Gates();
	}
	
	/**
	 * @return singleton of Gates
	 */
	public static Gates getSingletonGates(){
		
		return SingletonGates.gatesSingleton;
	}
	
	/**
	 * @param gate - ID of Gate to get
	 * @return Gate with ID = gate
	 */
	public Gate getGate(int gate){
		return gates.remove(gate);
	}
	
	/**
	 * @return iterator for gates (list).
	 */
	public Iterator<Gate> getGatesIterator(){
		return gates.iterator();
	}
	
	/**
	 * @param gate - the Gate to add to gates (list).
	 */
	protected void addGate(Gate gate){
		gates.add(gate);
	}
	
	/**
	 * @param gate - the Gate to add to gates (list).
	 * @throws IllegalArgumentException
	 * Adds gate to the list of Gates if gate doesn't equal null or gates doesn't contain it.
	 */
	public void returnGateToSource(Gate gate) throws IllegalArgumentException {
		
		if(gate != null && !gates.contains(gate)){
			
			gates.add(gate);
			return;
		}
		
		throw new IllegalArgumentException();
	}
	
	/**
	 * @return size of gates (list).
	 */
	public int howManyLeft(){
		return gates.size();
	}
	
	/**
	 * @return true if gates is empty.
	 */
	protected boolean isEmpty(){
		return gates.size() == 0;
	}
}