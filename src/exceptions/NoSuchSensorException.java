package exceptions;

import hardware.external.SensorFactory;

public class NoSuchSensorException extends Exception {
	
	/**
	 * No such sensor exception
	 * @param factory
	 */
	public NoSuchSensorException(SensorFactory factory){
		
		System.out.println("No sensors!");
		System.out.println("Gates sensors " + factory.getAmountGates());
		System.out.println("Eye sensors " + factory.getAmountEye());
	}
}
