package environment.races;

import environment.Race;

public class RaceEvent extends Race {
	
	
	public RaceEvent(int...channels) {
		
		super(channels[0], channels[1]);
	
	}
	
	
	
	@Override
	public boolean changeChannels(int... channels) {
		return super.changeChannels(channels);
	}
	
	@Override
	public int racersActive() {
		return super.racersActive();
	}
	
	@Override
	public void stopLastRace() {
		super.stopLastRace();
	}
	
	
	@Override
	public int[] getChannelsActive() {
		return super.getChannelsActive();
	}
	
	
//	@Override
//	public void startNRacers(int n) {
//		super.startNRacers(n);
//	}
//	
	
	@Override
	public void finishRacer() {
		super.finishRacer();
	}
}