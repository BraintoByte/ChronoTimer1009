package hardware.external.sensors.eye;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hardware.external.sensors.eye.Eye;

public class Eyes {
	
	/**
	 * 
	 */
	private List<Eye> eyes;
	
	
	/**
	 * 
	 */
	private Eyes(){
		
		eyes = new ArrayList<>();
		
	}
	
	private static class SingletonEye {
		
		/**
		 * Makes the singleton
		 */
		private static final Eyes eyeSingleton = new Eyes();
		
	}
	
	/**
	 * @return singleton of eyes
	 */
	public static Eyes getSingletonEyes(){
		
		return SingletonEye.eyeSingleton;
		
	}
	
	/**
	 * @param eye
	 * @return
	 */
	public Eye getEye(int eye){
		return eyes.remove(eye);
	}
	
	/**
	 * @return
	 */
	public Iterator<Eye> getEyeIterator(){
		return eyes.iterator();
	}
	
	/**
	 * @param eye
	 */
	protected void addEye(Eye eye){
		eyes.add(eye);
	}
	
	public void returnEyeToSource(Eye eye) throws IllegalArgumentException {
		
		if(eye != null && !eyes.contains(eye)){
			
			eyes.add(eye);
			return;
			
		}
		
		throw new IllegalArgumentException();
		
	}
	
	/**
	 * @return
	 */
	public int howManyLeft(){
		return eyes.size();
	}
	
	
	/**
	 * @return
	 */
	protected boolean isEmpty(){
		return eyes.size() == 0;
	}
}