package hardware.external.sensors.gate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Gates {
	
	private List<Gate> gates;
	
	
	private Gates(){
		
		gates = new ArrayList<>();
		
	}
	
	private static class SingletonGates {
		
		private static final Gates gatesSingleton = new Gates();
		
	}
	
	public static Gates getSingletonGates(){
		
		return SingletonGates.gatesSingleton;
		
	}
	
	public Gate getGate(int gate){
		
		return gates.get(gate);
		
	}
	
	public Iterator<Gate> getGatesIterator(){
		
		return gates.iterator();
		
	}
	
	protected void addGate(Gate gate){
		
		gates.add(gate);
		
	}
}