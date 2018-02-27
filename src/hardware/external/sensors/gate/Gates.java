package hardware.external.sensors.gate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Gates {
	
	/**
	 * 
	 */
	private List<Gate> gates;
	
	
	/**
	 * 
	 */
	private Gates(){
		
		gates = new ArrayList<>();
		
	}
	
	private static class SingletonGates {
		
		/**
		 * Makes the singleton
		 */
		private static final Gates gatesSingleton = new Gates();
		
	}
	
	/**
	 * @return
	 */
	public static Gates getSingletonGates(){
		
		return SingletonGates.gatesSingleton;
		
	}
	
	/**
	 * @param gate
	 * @return
	 */
	public Gate getGate(int gate){
		return gates.remove(gate);
	}
	
	/**
	 * @return
	 */
	public Iterator<Gate> getGatesIterator(){
		return gates.iterator();
	}
	
	/**
	 * @param gate
	 */
	protected void addGate(Gate gate){
		gates.add(gate);
	}
	
	public void returnGateToSource(Gate gate) throws IllegalArgumentException {
		
		if(gate != null && !gates.contains(gate)){
			
			gates.add(gate);
			return;
			
		}
		
		throw new IllegalArgumentException();
		
	}
	
	/**
	 * @return
	 */
	public int howManyLeft(){
		return gates.size();
	}
	
	/**
	 * @return
	 */
	protected boolean isEmpty(){
		return gates.size() == 0;
	}
}