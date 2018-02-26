package hardware.external.sensors.eye;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hardware.external.sensors.eye.Eye;

public class Eyes {
	
	private List<Eye> eyes;
	
	
	private Eyes(){
		
		eyes = new ArrayList<>();
		
	}
	
	private static class SingletonEye {
		
		private static final Eyes eyeSingleton = new Eyes();
		
	}
	
	public static Eyes getSingletonEyes(){
		
		return SingletonEye.eyeSingleton;
		
	}
	
	public Eye getEye(int eye){
		return eyes.remove(eye);
	}
	
	public Iterator<Eye> getEyeIterator(){
		return eyes.iterator();
	}
	
	protected void addEye(Eye eye){
		eyes.add(eye);
	}
	
	
	
	protected int howManyLeft(){
		return eyes.size();
	}
	
	
	protected boolean isEmpty(){
		return eyes.size() == 0;
	}
}