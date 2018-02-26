package environment;

import environment.channels.Ch1;
import environment.channels.Ch2;
import hardware.external.Sensor;

public abstract class Channels {
	
	public static Channels[] channels = new Channels[2];
	private String name;
	private final int chId;
	private boolean isEnabled;
	
	private static Channels ch1 = new Ch1(1);
	private static Channels ch2 = new Ch2(2);
	
	
	
	public Channels(String name, int chId) {
		
		this.name = name;
		this.chId = chId;
		channels[chId - 1] = this;
	
	}
	
	public void isEnabled(boolean isEnabled){
		
		this.isEnabled = isEnabled;
		
	}
	
	public void pairToSensor(Sensor sensor){
		
		
		
	}
	
	public void unPairToSensor(Sensor sensor){
		
		
		
	}
	
	public Sensor[] isPairedToSensor(){
		
		return null;
		
	}
	
}
