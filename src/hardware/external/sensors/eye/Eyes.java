package hardware.external.sensors.eye;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hardware.external.sensors.eye.Eye;

/**
 * @author Andy
 * The Eyes class, apart of the the hardware.external.sensor.eye package, is a container class for all of the Eye sensors.
 */
public class Eyes {
	
	// container for pad sensors
	private List<Eye> eyes;
	
	/**
	 * Constructor for Eyes
	 */
	private Eyes(){
		
		eyes = new ArrayList<>();
	}
	
	/**
	 * @author Andy
	 * Makes the singleton
	 */
	private static class SingletonEye {
		
		private static final Eyes eyeSingleton = new Eyes();
	}
	
	/**
	 * @return singleton of Eyes
	 */
	public static Eyes getSingletonEyes(){
		
		return SingletonEye.eyeSingleton;
	}
	
	/**
	 * @param eye - ID of Eye to get
	 * @return Eye with ID = eye 
	 */
	public Eye getEye(int eye){
		return eyes.remove(eye);
	}
	
	/**
	 * @return iterator for eyes (list).
	 */
	public Iterator<Eye> getEyeIterator(){
		return eyes.iterator();
	}
	
	/**
	 * @param eye - the Eye to add to pads (list).
	 */
	protected void addEye(Eye eye){
		eyes.add(eye);
	}
	
	/**
	 * @param eye - the Eye to add to eyes (list).
	 * @throws IllegalArgumentException
	 * Adds eye to the list of Eyes if eye doesn't equal null or eyes doesn't contain it.
	 */
	public void returnEyeToSource(Eye eye) throws IllegalArgumentException {
		
		if(eye != null && !eyes.contains(eye)){
			
			eyes.add(eye);
			return;
		}
		
		throw new IllegalArgumentException();
	}
	
	/**
	 * @return size of eyes (list).
	 */
	public int howManyLeft(){
		return eyes.size();
	}
	
	/**
	 * @return true if eyes is empty.
	 */
	protected boolean isEmpty(){
		return eyes.size() == 0;
	}
	
}