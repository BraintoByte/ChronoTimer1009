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
	
	
	public static Pads getSingletonPads(){
		
		return SingletonPad.padSingleton;
		
	}
	
	
	public Pad getPad(int pad){
		
		return pads.remove(pad);
		
	}
	
	
	public Iterator<Pad> getPadIteratively(){
		
		return pads.iterator();
		
	}
	
	
	protected void addPad(Pad pad){
		
		pads.add(pad);
	
	}
	
	
	
	public void returnPadToSource(Pad pad) throws IllegalArgumentException {
		
		if(pad != null && !pads.contains(pad)){
			
			pads.add(pad);
			return;
			
		}
		
		throw new IllegalArgumentException();
		
	}
	
	
	protected int howManyLeft(){
		return pads.size();
	}
	
	protected boolean isEmpty(){
		return pads.size() == 0;
	}
}