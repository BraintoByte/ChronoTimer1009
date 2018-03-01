package hardware.external;

public interface Sensor {
	
	public String getCurrentFormattedTime();
	public long getCurrentTimeInLong();
	public long computeTime();
	public int getId();
	public boolean hasBeam();
	public void trigger();
	public boolean isTriggered();

}
