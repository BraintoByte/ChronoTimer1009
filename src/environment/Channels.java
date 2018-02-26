package environment;

import java.util.ArrayList;
import java.util.List;

import environment.channels.Ch1;
import environment.channels.Ch2;
import hardware.external.Sensor;
import hardware.external.sensors.eye.Eye;
import hardware.external.sensors.gate.Gate;

public abstract class Channels {

	public static Channels[] channels = new Channels[2];
	private static List<Sensor> pairedSensors = new ArrayList<>();
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

		pairedSensors.add(sensor);

	}

	public Sensor unPairToSensor(Sensor sensor){

		return pairedSensors.remove(sensor.getId());

	}

	public Sensor[] pairedToSensors(){

		return (Sensor[]) pairedSensors.toArray();

	}
	
	public boolean isPairedToSensor(){
		return pairedSensors.size() > 0;
	}
	
	public int sensorsAmount(){
		return pairedSensors.size();
	}
}