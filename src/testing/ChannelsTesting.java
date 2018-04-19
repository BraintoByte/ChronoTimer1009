package testing;

import hardware.external.*;
import hardware.external.sensor.pad.Pad;
import exceptions.*;
import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import org.junit.Test;
import entitiesStatic.Clock;
import environment.RaceEventsManager;
import states.State;

/*
 * @author Nic
 * Tests the Channels class of the environment package.
 */

public class ChannelsTesting {

	private  Clock clock = new Clock();
	private  RaceEventsManager r = new RaceEventsManager();
	
	@Test
	public void testSetInvalidChannel() { 
		
		r.setChannelSelected(1);						//set channel to 1
		assertTrue(r.getChannelSelected() == 1);
		
		assertFalse(r.setChannelSelected(9));			//test invalid channels
		assertFalse(r.setChannelSelected(-2));
		
		assertTrue(r.getChannelSelected() == 1);		//selected channel should remain 1
		
	}
	
	
	
}
