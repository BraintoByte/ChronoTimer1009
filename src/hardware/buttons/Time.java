package hardware.buttons;
import hardware.user.Button;

public class Time extends Button{

	/**
	 * @param id
	 */
	public Time(int id) {
		super("TIME", id);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#getCurrentTime()
	 */
	@Override
	public String getCurrentTime(){
		
		return super.getCurrentTime();
		
	}
}