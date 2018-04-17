package hardware.external.sensor.pad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andy
 * The Pads class, apart of the the hardware.external.sensor.pad package, is a container class for all of the Pad sensors.
 */
public class Pads {
	
	// container for pad sensors
	private List<Pad> pads;
	
	/**
	 * Constructor for Pads
	 */
	private Pads(){
		
		pads = new ArrayList<>();
	}
	
	/**
	 * @author Andy
	 * Makes the singleton
	 */
	private static class SingletonPad {
		
		private static final Pads padSingleton = new Pads();
	}

	/**
	 * @return singleton of Pads
	 */
	public static Pads getSingletonPads(){
		
		return SingletonPad.padSingleton;
	}

	/**
	 * @param pad - ID of Pad to get
	 * @return Pad with ID = pad
	 */
	public Pad getPad(int pad){
		
		return pads.remove(pad);
	}

	/**
	 * @return iterator for pads (list).
	 */
	public Iterator<Pad> getPadIterator(){
		
		return pads.iterator();
	}
	
	/**
	 * @param pad - the Pad to add to pads (list).
	 */
	protected void addPad(Pad pad){
		
		pads.add(pad);
	}
	
	/**
	 * @param pad - the Pad to add to pads (list).
	 * @throws IllegalArgumentException
	 * Adds pad to the list of pads if pad doesn't equal null or pads doesn't contain it.
	 */
	public void returnPadToSource(Pad pad) throws IllegalArgumentException {
		
		if(pad != null && !pads.contains(pad)){
			
			pads.add(pad);
			return;
		}
		
		throw new IllegalArgumentException();
	}
	
	/**
	 * @return size of pads (list).
	 */
	public int howManyLeft(){
		return pads.size();
	}
	
	/**
	 * @return true if pads is empty.
	 */
	protected boolean isEmpty(){
		return pads.size() == 0;
	}
	
}