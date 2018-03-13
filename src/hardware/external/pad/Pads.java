package hardware.external.pad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pads {
	
	private List<Pad> pads;
	
	
	private Pads(){
		
		pads = new ArrayList<>();
		
	}
	
	
	private static class SingletonPad {
		
		private static final Pads padSingleton = new Pads();
		
	}
	
	
	/**
	 * @return
	 */
	public static Pads getSingletonPads(){
		
		return SingletonPad.padSingleton;
		
	}
	
	
	/**
	 * @param pad
	 * @return
	 */
	public Pad getPad(int pad){
		
		return pads.remove(pad);
		
	}
	
	
	/**
	 * @return
	 */
	public Iterator<Pad> getPadIterator(){
		
		return pads.iterator();
		
	}
	
	
	/**
	 * @param pad
	 */
	protected void addPad(Pad pad){
		
		pads.add(pad);
	
	}
	
	
	
	/**
	 * @param pad
	 * @throws IllegalArgumentException
	 */
	public void returnPadToSource(Pad pad) throws IllegalArgumentException {
		
		if(pad != null && !pads.contains(pad)){
			
			pads.add(pad);
			return;
			
		}
		
		throw new IllegalArgumentException();
		
	}
	
	
	/**
	 * @return
	 */
	public int howManyLeft(){
		return pads.size();
	}
	
	/**
	 * @return
	 */
	protected boolean isEmpty(){
		return pads.size() == 0;
	}
}